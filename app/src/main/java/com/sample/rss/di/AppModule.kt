package com.sample.rss.di

import android.app.Application
import android.content.Context
import com.sample.rss.BuildConfig
import com.sample.rss.room.AppDB
import com.sample.rss.rss.BASE_RSS_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun providesDb(context: Context): AppDB {
        return AppDB.getInstance(context)
    }

    @Provides
    fun providesOkHTTPClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.HEADERS
                })
            }
            addInterceptor { chain ->
                chain.proceed(chain.request()).let { response ->
                    response.header("etag", null)?.let { etag ->
                        // Check ETag for skipping loading
                    }
                    response
                }
            }
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_RSS_URL)
            .client(client)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
}