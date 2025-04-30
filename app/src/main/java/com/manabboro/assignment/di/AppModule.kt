package com.manabboro.assignment.di

import android.content.Context
import androidx.room.Room
import com.manabboro.assignment.data.local.AppDatabase
import com.manabboro.assignment.data.local.UserDao
import com.manabboro.assignment.data.remote.ApiKeyInterceptor
import com.manabboro.assignment.data.remote.UserApiService
import com.manabboro.assignment.repository.UserRepository
import com.manabboro.assignment.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.nerdythings.okhttp.profiler.BuildConfig
import io.nerdythings.okhttp.profiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
//
//    @Provides
//    fun provideMovieApiService(): MovieApiService {
//        return Retrofit.Builder()
//            .baseUrl("https://api.themoviedb.org/3/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(MovieApiService::class.java)
//    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        apiService: UserApiService
    ): UserRepository {
        return UserRepositoryImpl(apiService)
    }

}
