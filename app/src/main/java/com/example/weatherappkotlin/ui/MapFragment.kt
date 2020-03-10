package com.example.weatherappkotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.utils.Communicator
import com.example.weatherappkotlin.utils.UiManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_coordinates.*
import kotlinx.android.synthetic.main.fragment_map.*


/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
    private lateinit var googleMap: GoogleMap
    private lateinit var comm: Communicator
    lateinit var geo: LatLng
    private var geo1: Double? = null
    private var geo2: Double? = null
    private var newMarkerLatPos: Double? = null
    private var newMarkerLonPos: Double? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        comm = activity as Communicator
        geo1 = arguments?.getDouble("geo_lat")
        geo2 = arguments?.getDouble("geo_lon")
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onMapReady(map: GoogleMap?) {
        if (map != null) {
            googleMap = map
            googleMap.setOnMapClickListener {
                googleMap.clear()
                googleMap.addMarker(MarkerOptions().position(it))
                newMarkerLatPos = it.latitude
                newMarkerLonPos = it.longitude
                comm.passCoordFromMap(it.latitude, it.longitude)
            }
        }
        geo = LatLng(geo1!!, geo2!!)
        Toast.makeText(context, "Map is ready, locatios is ${geo}", Toast.LENGTH_SHORT).show()
        googleMap.addMarker(MarkerOptions().position(geo).title("City Marker"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(geo))
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(geo, 17.0f)
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        gmap.onCreate(savedInstanceState)
        gmap.onResume()
        gmap.getMapAsync(this)
    }

    override fun onMapLongClick(p0: LatLng?) {
        UiManager.showShortToast(context!!, p0.toString())
    }

    private fun startInfoWeatherFragment() {
        val fragment = WeatherInfoCoordFragment()
        val fm = activity?.supportFragmentManager
        val transaction = fm?.beginTransaction()
        transaction?.replace(R.id.main_container, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }


}
