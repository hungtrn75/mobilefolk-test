package com.mobilefolk.test.workspace.main.data.dtos

import com.mobilefolk.test.workspace.main.domain.models.CatImage

data class CatImageDto(
  val breeds: List<Any>? = null,
  val id: String? = null,
  val url: String? = null,
  val width: Long? = null,
  val height: Long? = null,
  val categories: List<CategoryDto>? = null
)

data class CategoryDto(
  val id: Long? = null,
  val name: String? = null
)

fun CatImageDto.mapper() = CatImage(id = id, url = url, width = width, height = height)