package com.example.weatherappkotlin

import android.app.Application
import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class App : Application() {
    var BASE_URL = "https://api.openweathermap.org/data/2.5/"
    var service: ApiService? = null

    override fun onCreate() {
        super.onCreate()
        service = create()
    }


    fun create() : ApiService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
            .create(ApiService::class.java)
    }

    fun getApp(context: Context): App {
        return context.applicationContext as App
    }

    fun getRetrofitService(): ApiService {
        return service!!
    }
}