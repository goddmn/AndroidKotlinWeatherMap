package com.example.weatherappkotlin

import com.example.weatherappkotlin.model.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    fun getCityListByName(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ) : Call<WeatherModel>

    @GET("weather")
    fun getCityListByCoord(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ) : Call<WeatherModel>
}