package com.example.banda.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.banda.MainActivity
import com.example.banda.R
import kotlinx.android.synthetic.main.activity_finalregister.*

class Finalregister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finalregister)
        button_move.setOnClickListener{
            intent = Intent(this@Finalregister, MainActivity::class.java)
            startActivity((intent))
        }
    }
}