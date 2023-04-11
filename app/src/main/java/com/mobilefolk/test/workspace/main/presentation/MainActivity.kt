package com.mobilefolk.test.workspace.main.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobilefolk.test.databinding.ActivityMainBinding
import com.mobilefolk.test.workspace.details.presentation.DetailsActivity
import com.mobilefolk.test.workspace.main.domain.models.CatImage
import com.mobilefolk.test.workspace.main.presentation.adapters.CatImagesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity(), CatImagesAdapter.OnImageListener {
  private lateinit var binding: ActivityMainBinding
  private val viewModel: CatViewModel by viewModel()
  
  private lateinit var imageAdapter: CatImagesAdapter
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    settingViews()
    settingListeners()
    viewModel.refreshImages()
  }
  
  private fun settingViews() {
    imageAdapter = CatImagesAdapter(this)
    binding.recycleView.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = imageAdapter
      setHasFixedSize(false)
    }
  }
  
  private fun settingListeners() {
    viewModel.catImagesLiveData.observe(this) { images ->
      Timber.e(images.size.toString())
      imageAdapter.submitList(images)
    }
    binding.apply {
      recycleView.addOnScrollListener(onScrollChangeListener)
    }
  }
  
  private val onScrollChangeListener = object : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
      super.onScrolled(recyclerView, dx, dy)
      val linearLayoutManager = binding.recycleView.layoutManager as LinearLayoutManager?
      
      if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() > 0 && linearLayoutManager.findLastCompletelyVisibleItemPosition() == viewModel.searchSize - 1) {
        viewModel.getImages()
      }
    }
  }
  
  override fun onTap(item: CatImage) {
    val intent = Intent(this, DetailsActivity::class.java)
    intent.putExtra("image", item)
    startActivity(intent)
  }
  
  override fun onRetry() {
    viewModel.retry()
  }
}