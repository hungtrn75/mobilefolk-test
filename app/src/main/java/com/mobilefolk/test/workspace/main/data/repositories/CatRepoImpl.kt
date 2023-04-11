package com.mobilefolk.test.workspace.main.data.repositories

import arrow.core.Either
import com.mobilefolk.test.core.util.NetworkError
import com.mobilefolk.test.workspace.main.data.dto.mapper
import com.mobilefolk.test.workspace.main.data.remote.CatRemoteDataSource
import com.mobilefolk.test.workspace.main.domain.models.CatImage
import com.mobilefolk.test.workspace.main.domain.repositories.CatRepo

class CatRepoImpl constructor(
  private val catRemoteDataSource: CatRemoteDataSource,
) :
  CatRepo {
  override suspend fun getCatImages(page: Int): Either<String, List<CatImage>> = try {
    val resp = catRemoteDataSource.getCatImages(page).map { it.mapper() }
    Either.Right(resp)
  } catch (e: Exception) {
    Either.Left(NetworkError.handleException(e))
  }
}