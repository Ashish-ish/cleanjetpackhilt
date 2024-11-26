package com.ashish.cleanjetpackhilt.di

import android.content.Context
import android.content.SharedPreferences
import com.ashish.cleanjetpackhilt.data.network.ApiService
import com.ashish.cleanjetpackhilt.utils.Constants.prefName
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi
        .Builder()
        .run {
            add(KotlinJsonAdapterFactory())
            build()
        }

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024L // 10 MB cache
        return Cache(context.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(cacheInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(moshi: Moshi, okHttpClient: OkHttpClient): ApiService = Retrofit
        .Builder()
        .run {
            baseUrl(ApiService.BASE_URL)
            addConverterFactory(MoshiConverterFactory.create(moshi))
            client(okHttpClient)
            build()
        }.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    private fun cacheInterceptor() = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
            .maxAge(5, TimeUnit.MINUTES) // Specifically caching response for 5 mins, to enable api caching
            .build()
        response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}