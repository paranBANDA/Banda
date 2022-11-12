package com.example.banda

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.banda.MyPage.MyPageFragment
import com.example.banda.store.StoreFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(){
    var polaroidFragment: Fragment? = null
    var storeFragment: Fragment? = null
    var mainPageFragment: Fragment? = null
    var bottomAppBar: BottomNavigationView? = null
    var myPageFragment : MyPageFragment? = null

    var walkButton : FloatingActionButton? = null

    private fun fabColorActivate() {
        walkButton?.imageTintList = ColorStateList.valueOf(Color.rgb(0xff, 0xc1, 0x1a))
    }

    private fun fabColorDeactivate() {
        walkButton?.imageTintList = ColorStateList.valueOf(Color.rgb(0x87, 0x87, 0x87))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        polaroidFragment = PolaroidFragment()
        storeFragment = StoreFragment()
        mainPageFragment = MainPageFragment()
        myPageFragment = MyPageFragment()
        bottomAppBar = findViewById<BottomNavigationView>(R.id.bottomAppBar)
        walkButton = findViewById<FloatingActionButton>(R.id.walkButton)
        walkButton?.setOnClickListener {
            fabColorActivate()
            supportFragmentManager.beginTransaction().replace(R.id.frame,
                polaroidFragment!!
            ).commit()
        }
        bottomAppBar?.setOnItemSelectedListener { item ->

            when(item.itemId) {

                R.id.navHome -> {
                    fabColorDeactivate()
                    Log.d("ASD", item.itemId.toString())
                    supportFragmentManager.beginTransaction().replace(R.id.frame ,
                        mainPageFragment!!
                    ).commit()
                    true
                }
                R.id.navPolaroid -> {
                    fabColorDeactivate()

                    Log.d("ASD", item.itemId.toString())
                    supportFragmentManager.beginTransaction().replace(R.id.frame,
                        polaroidFragment!!
                    ).commit()
                    true
                }
                R.id.navWalk -> {
                    fabColorActivate()
                    Log.d("ASD", item.itemId.toString())
                    supportFragmentManager.beginTransaction().replace(R.id.frame,
                        polaroidFragment!!
                    ).commit()
                    true
                }
                R.id.navStore -> {
                    fabColorDeactivate()
                    Log.d("ASD", item.itemId.toString())
//                    supportFragmentManager.beginTransaction().replace(R.id.frame,
//                        storeFragment!!
//                    ).commit()
                    true
                }
                R.id.navMypage -> {
                    fabColorDeactivate()
                    Log.d("ASD", item.itemId.toString())
                    supportFragmentManager.beginTransaction().replace(R.id.frame,
                        myPageFragment!!
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