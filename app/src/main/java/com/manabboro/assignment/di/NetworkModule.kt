package com.manabboro.assignment.di

import com.manabboro.assignment.data.remote.ApiKeyInterceptor
import com.manabboro.assignment.data.remote.MovieApiService
import com.manabboro.assignment.data.remote.UserApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.nerdythings.okhttp.profiler.BuildConfig
import io.nerdythings.okhttp.profiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(OkHttpProfilerInterceptor())
        }
        builder.addInterceptor(ApiKeyInterceptor())

        return builder.build()
    }


    @Singleton
    @Provides
    fun provideUserApiService(okHttpClient: OkHttpClient): UserApiService {
        return Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApiService::class.java)
    }


    @Provides
    fun provideMovieApiService(): MovieApiService {
        return Retrofit.Builder()
            .baseUrl("http://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiService::class.java)
    }

}