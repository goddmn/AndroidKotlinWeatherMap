package com.example.weatherappkotlin.ui

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weatherappkotlin.R
import kotlinx.android.synthetic.main.activity_launcher.*
import kotlinx.android.synthetic.main.fragment_city.*

class LauncherActivity : AppCompatActivity() {

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

    fun changeFragments(fragment: Fragment){
        val fragment = fragment
        val fm = supportFragmentManager
        val transaction = fm?.beginTransaction()
        input_container.visibility = View.GONE
        transaction?.add(R.id.main_container, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}
