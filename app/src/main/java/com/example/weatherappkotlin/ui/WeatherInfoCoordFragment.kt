package com.example.weatherappkotlin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.weatherappkotlin.ApiService
import com.example.weatherappkotlin.App

import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.model.WeatherModel
import com.example.weatherappkotlin.utils.API
import com.example.weatherappkotlin.utils.Communicator
import com.example.weatherappkotlin.utils.UiManager
import kotlinx.android.synthetic.main.fragment_coordinates.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class WeatherInfoCoordFragment : Fragment() {
    var retrofit: ApiService? = null

    var map_lat: Double? = null
    var map_lon: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retrofit = App().getApp(context!!).getRetrofitService()
        map_lat = arguments?.getDouble("map_lat")
        map_lon = arguments?.getDouble("map_lon")
        UiManager.showShortToast(context!!, map_lat.toString())
        return inflater.inflate(R.layout.fragment_weather_info_coord, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val call = retrofit?.getCityListByCoord(map_lat!!, map_lon!!, API)
        call?.enqueue(object : Callback<WeatherModel> {
            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Toast.makeText(context, "onFailure", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<WeatherModel>,
                response: Response<WeatherModel>
            ) {
                Toast.makeText(context, "onResponse", Toast.LENGTH_LONG).show()
                val list = response.body()
                coord_lat_txt.text = list?.coord?.lat.toString()
                coord_lon_txt.text = list?.coord?.lon.toString()
                weather_txt.text = list?.main?.temp.toString()
                visibility_txt.text = list?.visibility.toString()
                city_name_txt.text = list?.name.toString()
                country_txt.text = list?.sys?.country.toString()
                temp_min_txt.text = list?.main?.tempMin.toString()
                temp_max_txt.text = list?.main?.tempMax.toString()
            }
        })
    }

}
