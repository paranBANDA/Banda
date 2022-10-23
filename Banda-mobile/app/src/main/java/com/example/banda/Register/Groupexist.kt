package com.example.banda.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.banda.R
import kotlinx.android.synthetic.main.activity_groupexist.*
import kotlinx.android.synthetic.main.activity_registernickname.*

class Groupexist : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groupexist)
        val thirdintent = getIntent()
        val email = thirdintent.getStringExtra("email")
        val pw = thirdintent.getStringExtra("pw")
        val nickname = thirdintent.getStringExtra("nickname")
        Log.e("tag", "메인에서 받아온 id : $email, pw : $pw nickname: $nickname")
        button_yes.setOnClickListener {
            intent = Intent(this@Groupexist, Familyfind::class.java)
            intent.putExtra("email",email)
            intent.putExtra("pw",pw)
            intent.putExtra("nickname",nickname)
            startActivity((intent))
        }
        button_no.setOnClickListener {
            intent = Intent(this@Groupexist, Dogregister::class.java)
            intent.putExtra("email",email)
            intent.putExtra("pw",pw)
            intent.putExtra("nickname",nickname)
            startActivity((intent))
        }
    }
}