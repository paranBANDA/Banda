package com.example.banda.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.banda.R
import kotlinx.android.synthetic.main.activity_dogregister.*

class Dogregister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogregister)
        button2_register.setOnClickListener{
            intent = Intent(this@Dogregister, Finalregister::class.java)
            startActivity((intent))
        }
    }
}