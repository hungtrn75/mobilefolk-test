package com.mobilefolk.test.workspace.main.data.remote

import com.mobilefolk.test.core.utils.Constants
import com.mobilefolk.test.workspace.main.data.dtos.CatImageDto
import retrofit2.http.*

interface CatService {
    @GET(Constants.catImages)
    suspend fun getCatImages(
        @Query("has_breeds") hasBreeds: Int = Constants.hasBreeds,
        @Query("limit") pageSize: Int = Constants.pageSize,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = Constants.newsApiKey,
    ): List<CatImageDto>

}