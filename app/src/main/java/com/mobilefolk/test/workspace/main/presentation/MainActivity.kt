package com.mobilefolk.test.workspace.main.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobilefolk.test.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private val viewModel: CatViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    settingViews()
    settingListeners()
  }

  private fun settingViews() {
    viewModel.refreshImages()
  }

  private fun settingListeners() {
    viewModel.catImagesLiveData.observe(this) { images ->
      Timber.e(images.size.toString());
    }
  }
}