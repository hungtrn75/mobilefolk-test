package com.mobilefolk.test.workspace.details.presentation

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mobilefolk.test.R
import com.mobilefolk.test.core.utils.getShimmerDrawable
import com.mobilefolk.test.databinding.ActivityDetailsBinding
import com.mobilefolk.test.workspace.main.domain.models.CatImage


class DetailsActivity : AppCompatActivity() {
  private lateinit var binding: ActivityDetailsBinding
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDetailsBinding.inflate(layoutInflater)
    setContentView(binding.root)
    settingViews()
    settingListeners()
  }
  
  private fun settingViews() {
    window.setFlags(
      WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
      WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
    val imageExtra = intent.getParcelableExtra<CatImage>("image")
    imageExtra?.apply {
      Glide.with(this@DetailsActivity)
        .load(imageExtra.url)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .placeholder(this@DetailsActivity.getShimmerDrawable())
        .error(ColorDrawable(ContextCompat.getColor(this@DetailsActivity, R.color.colorShimmer)))
        .centerInside()
        .into(binding.imageView)
    }
  }
  
  private fun settingListeners() {
    binding.backBtn.setOnClickListener {
      onBackPressed()
    }
  }
}