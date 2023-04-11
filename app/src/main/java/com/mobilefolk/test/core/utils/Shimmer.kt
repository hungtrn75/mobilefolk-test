package com.mobilefolk.test.core.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.mobilefolk.test.R

inline fun Context.getShimmerDrawable(): ShimmerDrawable {
  val shimmer = Shimmer.ColorHighlightBuilder()
    .setDuration(1500)
    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
    .setBaseColor(ContextCompat.getColor(this, R.color.colorShimmer))
    .setBaseAlpha(1f)
    .setAutoStart(true)
    .build()
  return ShimmerDrawable().apply {
    setShimmer(shimmer)
  }
}

