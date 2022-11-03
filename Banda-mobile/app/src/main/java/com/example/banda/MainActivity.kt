package com.example.banda

import HappyDayDecorator
import PolaroidFragment
import SaturdayDecorator
import WeekendDecorator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.kakaologin.DogProfileAdapter
import com.android.kakaologin.DogProfileData
import com.example.banda.data.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(){
    var polaroidFragment: Fragment? = null
    var mainPageFragment: Fragment? = null
    var bottomAppBar: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        polaroidFragment = PolaroidFragment()
        mainPageFragment = MainPageFragment()
        bottomAppBar = findViewById<BottomNavigationView>(R.id.bottomAppBar)

        bottomAppBar?.setOnItemSelectedListener { item ->

            when(item.itemId) {

                R.id.navHome -> {

                    Log.d("ASD", item.itemId.toString())
                    supportFragmentManager.beginTransaction().replace(R.id.frame ,
                        mainPageFragment!!
                    ).commit()
                    true
                }
                R.id.navPolaroid -> {

                    Log.d("ASD", item.itemId.toString())
                    supportFragmentManager.beginTransaction().replace(R.id.frame,
                        polaroidFragment!!
                    ).commit()
                    true
                }
                R.id.navWalk -> {
                    Log.d("ASD", item.itemId.toString())
                    supportFragmentManager.beginTransaction().replace(R.id.frame,
                        polaroidFragment!!
                    ).commit()
                    true
                }
                else -> {
                    false
                }
            }

        }
        supportFragmentManager.beginTransaction().replace(R.id.frame, mainPageFragment!!).commit()

    }
    override fun onBackPressed() {
//        super.onBackPressed()
    }
}