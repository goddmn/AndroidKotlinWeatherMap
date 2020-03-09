package com.example.weatherappkotlin.ui

import android.content.Intent
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
import kotlinx.android.synthetic.main.fragment_coordinates.*
import kotlinx.android.synthetic.main.fragment_coordinates.btn_city_back
import kotlinx.android.synthetic.main.fragment_coordinates.city_name_txt
import kotlinx.android.synthetic.main.fragment_coordinates.coord_lat_txt
import kotlinx.android.synthetic.main.fragment_coordinates.coord_lon_txt
import kotlinx.android.synthetic.main.fragment_coordinates.country_txt
import kotlinx.android.synthetic.main.fragment_coordinates.temp_max_txt
import kotlinx.android.synthetic.main.fragment_coordinates.temp_min_txt
import kotlinx.android.synthetic.main.fragment_coordinates.visibility_txt
import kotlinx.android.synthetic.main.fragment_coordinates.weather_txt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class CoordinatesFragment : Fragment() {
    var retrofit: ApiService? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        retrofit = App().getApp(context!!).getRetrofitService()
        val view = inflater.inflate(R.layout.fragment_coordinates, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_enter_coord.setOnClickListener {
            val call =  retrofit?.getCityListByCoord(42.87, 74.59, API)
            call?.enqueue(object : Callback<WeatherModel>{
                override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                    Toast.makeText(context, "onFailure", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
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
        btn_city_back.setOnClickListener {
            val intent = Intent (getActivity(), LauncherActivity::class.java)
            getActivity()?.startActivity(intent)
        }

        btn_map.setOnClickListener {
            val fragment = MapFragment()
            val fm = activity?.supportFragmentManager
            val transaction = fm?.beginTransaction()
            input_layout_coord.visibility = View.GONE
            info_layout_coord.visibility = View.GONE
            transaction?.replace(R.id.main_coord_container, fragment)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
    }

}
