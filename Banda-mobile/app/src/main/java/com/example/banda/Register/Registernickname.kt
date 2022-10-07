package com.example.banda.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.banda.R
import kotlinx.android.synthetic.main.activity_registernickname.*

class Registernickname : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registernickname)
        button.setOnClickListener {
            intent = Intent(this@Registernickname, Groupexist::class.java)
            startActivity((intent))
        }
    }
}