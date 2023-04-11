package com.mobilefolk.test.workspace.main.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatImage(
  val id: String? = null,
  val url: String? = null,
  val width: Long? = null,
  val height: Long? = null,
  val placeholder: ImagePlaceholder? = ImagePlaceholder.Image,
): Parcelable

@Parcelize
data class ImagesHolder(
  val totalResults: Int,
  val images: List<CatImage>?
) : Parcelable

@Parcelize
sealed class ImagePlaceholder : Parcelable {
  object Image : ImagePlaceholder()
  object FistPageLoading : ImagePlaceholder()
  object Loading : ImagePlaceholder()
  class Error(val error: String) : ImagePlaceholder()
}