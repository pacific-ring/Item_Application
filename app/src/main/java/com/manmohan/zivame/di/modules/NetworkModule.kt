package com.manmohan.zivame.di.modules

import com.manmohan.zivame.retrofit.ProductApi
import com.manmohan.zivame.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun getInterceptor() : Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }


    @Singleton
    @Provides
    fun getClient(interceptor : Interceptor): OkHttpClient {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return client
    }


    @Singleton
    @Provides
    fun getRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }




    @Singleton
    @Provides
    fun getProductApi(retrofit: Retrofit) : ProductApi {
        return retrofit.create(ProductApi :: class.java)
    }

}