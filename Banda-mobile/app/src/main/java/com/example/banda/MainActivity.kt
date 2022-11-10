package com.example.banda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.banda.MyPage.MyPageFragment
import com.example.banda.store.StoreFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(){
    var polaroidFragment: Fragment? = null
    var storeFragment: Fragment? = null
    var mainPageFragment: Fragment? = null
    var bottomAppBar: BottomNavigationView? = null
    var myPageFragment : MyPageFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        polaroidFragment = PolaroidFragment()
        storeFragment = StoreFragment()
        mainPageFragment = MainPageFragment()
        myPageFragment = MyPageFragment()
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
                R.id.navStore -> {
                    Log.d("ASD", item.itemId.toString())
//                    supportFragmentManager.beginTransaction().replace(R.id.frame,
//                        storeFragment!!
//                    ).commit()
                    true
                }
                R.id.navMypage -> {
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