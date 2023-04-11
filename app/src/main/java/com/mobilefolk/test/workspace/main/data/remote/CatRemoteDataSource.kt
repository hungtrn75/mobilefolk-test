package com.mobilefolk.test.workspace.main.data.remote

import com.mobilefolk.test.workspace.main.data.dtos.CatImageDto

interface CatRemoteDataSource {
  suspend fun getCatImages(page: Int): List<CatImageDto>
}

class CatRemoteDataSourceImpl constructor(private val catService: CatService) :
  CatRemoteDataSource {
  override suspend fun getCatImages(page: Int) =
    catService.getCatImages(page = page)
}