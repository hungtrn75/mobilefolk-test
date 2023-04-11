package com.mobilefolk.test.workspace.main.domain.usecases

import arrow.core.Either
import com.mobilefolk.test.core.UseCase
import com.mobilefolk.test.workspace.main.domain.models.CatImage
import com.mobilefolk.test.workspace.main.domain.repositories.CatRepo

class GetCatImagesUseCase constructor(private val catRepo: CatRepo) :
  UseCase<Either<String, List<CatImage>>, Int>() {
  override suspend fun execute(params: Int) =
    catRepo.getCatImages(params)
}
