package com.example.weatherappkotlin.ui

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.utils.Communicator
import kotlinx.android.synthetic.main.activity_launcher.*
import kotlinx.android.synthetic.main.fragment_city.*

class LauncherActivity : AppCompatActivity(), Communicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        btn_city.setOnClickListener {
            changeFragments(CityFragment())
        }

        btn_coord.setOnClickListener {
            changeFragments(CoordinatesFragment())
        }
    }

    fun changeFragments(fragment: Fragment) {
        val fragment = fragment
        val fm = supportFragmentManager
        val transaction = fm?.beginTransaction()
        input_container.visibility = View.GONE
        transaction?.add(R.id.main_container, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 404) {

        }
    }

    override fun passCoord(lat: Double, lon: Double) {
        val bundle = Bundle()
        bundle.putAll(bundle)
        bundle.putDouble("geo_lat", lat)
        bundle.putDouble("geo_lon", lon)
        val transaction = this.supportFragmentManager.beginTransaction()
        val frag2 = MapFragment()
        frag2.arguments = bundle

        transaction.replace(R.id.main_container, frag2)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

    override fun passCoordFromMap(lat: Double, lon: Double) {
        val bundle = Bundle()
        bundle.putAll(bundle)
        bundle.putDouble("map_lat", lat)
        bundle.putDouble("map_lon", lon)
        val transaction = this.supportFragmentManager.beginTransaction()
        val frag2 = WeatherInfoCoordFragment()
        frag2.arguments = bundle

        transaction.replace(R.id.main_container, frag2)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }
}
