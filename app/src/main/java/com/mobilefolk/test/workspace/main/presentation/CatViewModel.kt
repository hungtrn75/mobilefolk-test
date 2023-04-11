package com.mobilefolk.test.workspace.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mobilefolk.test.core.utils.Constants
import com.mobilefolk.test.workspace.main.domain.models.CatImage
import com.mobilefolk.test.workspace.main.domain.models.ImagePlaceholder
import com.mobilefolk.test.workspace.main.domain.usecases.GetCatImagesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class CatViewModel constructor(
  private val getCatImagesUseCase: GetCatImagesUseCase
) : ViewModel() {
  private var _searching = false
  private val _imagesFlow = MutableStateFlow(mutableListOf<CatImage>())
  
  val searchSize: Int get() = _imagesFlow.value.size
  val catImagesLiveData = _imagesFlow.asLiveData()
  
  fun refreshImages() {
    getImages(refresh = true)
  }
  
  fun retry() = viewModelScope.launch {
    if (searchSize == 1) {
      getImages(refresh = true)
    } else {
      val previous = _imagesFlow.value.toMutableList()
      previous.removeLast()
      _imagesFlow.emit(previous)
      getImages()
    }
  }
  
  /* pull to refresh -> refresh: true */
  fun getImages(refresh: Boolean = false) {
    viewModelScope.launch(Dispatchers.IO) {
      if (_searching) return@launch
      _searching = true
      var previous = if (refresh) mutableListOf() else _imagesFlow.value.toMutableList()
      
      withContext(Dispatchers.Main) {
        if (refresh) {
          previous.addAll(
            mutableListOf(
              CatImage(placeholder = ImagePlaceholder.Loading),
              CatImage(placeholder = ImagePlaceholder.Loading),
              CatImage(placeholder = ImagePlaceholder.Loading),
              CatImage(placeholder = ImagePlaceholder.Loading),
            )
          )
          _imagesFlow.emit(
            previous.toMutableList()
          )
        } else {
          previous.add(CatImage(placeholder = ImagePlaceholder.Loading))
          _imagesFlow.emit(previous.toMutableList())
        }
      }
      
      val resp = getCatImagesUseCase.execute(
        if (refresh) 0 else (previous.size / Constants.pageSize)
      )
      
      if (refresh) previous = mutableListOf() else previous.removeLast()
      resp.fold({ error ->
        Timber.e(error);
        previous.add(CatImage(placeholder = ImagePlaceholder.Error(error)))
        withContext(Dispatchers.Main) {
          _imagesFlow.emit(previous.toMutableList())
          _searching = false
        }
      }) { images ->
        previous.addAll(images)
        withContext(Dispatchers.Main) {
          _imagesFlow.emit(previous.toMutableList())
          _searching = false
        }
      }
    }
  }
}