package com.example.mymoviecatalog.di

import com.example.mymoviecatalog.Network.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    private lateinit var client: OkHttpClient.Builder
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createClient().build())
            .build()
    }

    @Singleton
    @Provides
    @Named("provideRetrofitForYoutube")
    fun provideRetrofitForYoutube(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://youtube.googleapis.com/youtube/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createClient().build())
            .build()
    }

    fun createClient(): OkHttpClient.Builder {
        val logger: HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        client = OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request()
                val newUrl = request.url.newBuilder()
                    .addQueryParameter("api_key", "64ce00b71164fc4f02e665d2924b7ca3")
                    .build()
                it.proceed(
                    request.newBuilder()
                        .url(newUrl)
                        .build()
                )

            }
        client.addInterceptor(logger)
        return client

        /*when (apiName) {
            "tmdb" -> {
                 client = OkHttpClient.Builder()
                    .addInterceptor {
                        val request = it.request()
                        val newUrl = request.url.newBuilder()
                            .addQueryParameter("api_key", "64ce00b71164fc4f02e665d2924b7ca3")
                            .build()
                        it.proceed(
                            request.newBuilder()
                                .url(newUrl)
                                .build()
                        )

                    }
            }
            "youtube" ->{
                 client = OkHttpClient.Builder()
                    .addInterceptor {
                        val request = it.request()
                        val newUrl = request.url.newBuilder()
                            .addQueryParameter("key", "AIzaSyBEHb0pKKo-tWVBSDF_3qZ9mTIy_tI36pw")
                            .build()
                        it.proceed(
                            request.newBuilder()
                                .url(newUrl)
                                .build()
                        )
                    }
            }
        }
        client.addInterceptor(logger)
        return client  */
    }

    fun createClientForYoutube(): OkHttpClient.Builder {
        val logger: HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request()
                val newUrl = request.url.newBuilder()
                    .addQueryParameter("key", "AIzaSyBEHb0pKKo-tWVBSDF_3qZ9mTIy_tI36pw")
                    .build()
                it.proceed(
                    request.newBuilder()
                        .url(newUrl)
                        .build()
                )
            }
        client.addInterceptor(logger)
        return client
    }

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}