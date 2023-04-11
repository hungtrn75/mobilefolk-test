package com.mobilefolk.test.workspace.main.domain.repositories

import arrow.core.Either
import com.mobilefolk.test.workspace.main.domain.models.CatImage

interface CatRepo {
  suspend fun getCatImages(page: Int): Either<String, List<CatImage>>

}