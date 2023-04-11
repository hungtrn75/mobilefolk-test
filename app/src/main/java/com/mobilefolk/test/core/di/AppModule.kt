package com.mobilefolk.test.core.di

import android.content.Context
import com.mobilefolk.test.core.utils.AppInterceptor
import com.mobilefolk.test.core.utils.Constants
import com.mobilefolk.test.workspace.main.data.remote.CatRemoteDataSource
import com.mobilefolk.test.workspace.main.data.remote.CatRemoteDataSourceImpl
import com.mobilefolk.test.workspace.main.data.remote.CatService
import com.mobilefolk.test.workspace.main.data.repositories.CatRepoImpl
import com.mobilefolk.test.workspace.main.domain.repositories.CatRepo
import com.mobilefolk.test.workspace.main.domain.usecases.GetCatImagesUseCase
import com.mobilefolk.test.workspace.main.presentation.CatViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

val helperModule = module {
  factory { CoroutineScope(Dispatchers.IO) }
}

val retrofitModule = module {
  fun provideInterceptor(context: Context): AppInterceptor {
    return AppInterceptor(context)
  }

  fun provideHttpClient(interceptor: AppInterceptor): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()
    okHttpClientBuilder.readTimeout(45, TimeUnit.SECONDS)
    okHttpClientBuilder.connectTimeout(45, TimeUnit.SECONDS)
    return okHttpClientBuilder.addInterceptor(interceptor).build()
  }

  fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .baseUrl(Constants.baseUrl)
      .addConverterFactory(JacksonConverterFactory.create())
      .client(client)
      .build()
  }

  single { provideInterceptor(get()) }
  single { provideHttpClient(get()) }
  single { provideRetrofit(get()) }
}

val serviceModule = module {
  fun provideAuthService(retrofit: Retrofit): CatService {
    return retrofit.create(CatService::class.java)
  }
  single {
    provideAuthService(get())
  }
}

val dataSourceModule = module {
  single<CatRemoteDataSource> {
    CatRemoteDataSourceImpl(get())
  }
}

val repositoryModule = module {
  single<CatRepo> {
    CatRepoImpl(get())
  }
}

val useCaseModule = module {
  single {
    GetCatImagesUseCase(get())
  }
}

val viewModelModule = module {
  viewModel {
    CatViewModel(get())
  }
}