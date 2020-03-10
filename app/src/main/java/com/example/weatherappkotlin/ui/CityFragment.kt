package com.example.weatherappkotlin.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.weatherappkotlin.*
import com.example.weatherappkotlin.model.WeatherModel
import com.example.weatherappkotlin.utils.API
import kotlinx.android.synthetic.main.activity_launcher.*
import kotlinx.android.synthetic.main.fragment_city.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityFragment : Fragment() {
    var city: String = "Bishkek"
    var retrofit: ApiService? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retrofit = App().getApp(context!!).getRetrofitService()
        val view = inflater.inflate(R.layout.fragment_city, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        enter_city_name.setOnClickListener {
            city = et_city_name.text.toString()
            val call = retrofit?.getCityListByName(city, API)
            call?.enqueue(object : Callback<WeatherModel> {
                override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                    Toast.makeText(context, "onFailure", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<WeatherModel>,
                    response: Response<WeatherModel>
                ) {
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
        btn_city_back.setOnClickListener {
            val intent = Intent(getActivity(), LauncherActivity::class.java)
            getActivity()?.startActivity(intent)
        }
    }
}
