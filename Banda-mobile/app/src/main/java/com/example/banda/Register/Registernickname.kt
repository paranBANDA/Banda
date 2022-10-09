package com.example.banda.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.banda.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_registernickname.*

class Registernickname : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registernickname)
        val secondintent = getIntent()
        val email = secondintent.getStringExtra("email")
        val pw = secondintent.getStringExtra("pw")
        Log.e("tag", "메인에서 받아온 id : $email, pw : $pw")
        button_next.setOnClickListener {
            var nickname = textInputEditText.text.toString()

            val intent = Intent(this@Registernickname, Groupexist::class.java)
            intent.putExtra("email",email)
            intent.putExtra("pw",pw)
            intent.putExtra("nickname",nickname)
            startActivity((intent))
        }
    }
}