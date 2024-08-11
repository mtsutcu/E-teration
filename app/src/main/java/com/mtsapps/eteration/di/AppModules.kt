package com.mtsapps.eteration.di

import android.content.Context
import androidx.room.Room
import com.mtsapps.eteration.BuildConfig
import com.mtsapps.eteration.data.local.AppDatabase
import com.mtsapps.eteration.data.local.CartDao
import com.mtsapps.eteration.data.remote.ApiService
import com.mtsapps.eteration.data.repository.CartRepositoryImpl
import com.mtsapps.eteration.data.repository.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideProductRepositoryImpl(apiService: ApiService): ProductRepositoryImpl {
        return ProductRepositoryImpl(apiService = apiService)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCartDao(db: AppDatabase): CartDao {
        return db.cartDao()
    }
    @Provides
    @Singleton
    fun provideCartRepositoryImpl(cartDao: CartDao): CartRepositoryImpl {
        return CartRepositoryImpl(cartDao = cartDao)
    }


}