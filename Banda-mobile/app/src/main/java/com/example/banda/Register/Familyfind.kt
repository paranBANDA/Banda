package com.example.banda.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.banda.R
import kotlinx.android.synthetic.main.activity_familyfind.*

class Familyfind : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_familyfind)
        button_next.setOnClickListener{
            intent = Intent(this@Familyfind, Finalregister::class.java)
            startActivity((intent))
        }
    }
}