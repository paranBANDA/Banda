package com.example.banda.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.banda.MainActivity
import com.example.banda.R
import com.example.banda.RetrofitService
import com.example.banda.data.Login
import com.example.banda.data.LoginGet
import com.example.banda.data.Register
import com.example.banda.data.RegisterGet
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_registernickname.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Registernickname : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registernickname)
        val secondintent = getIntent()
        val email = secondintent.getStringExtra("email").toString()
        val pw = secondintent.getStringExtra("pw").toString()
        Log.e("tag", "메인에서 받아온 id : $email, pw : $pw")
        val retrofit = Retrofit.Builder().baseUrl("http://13.124.202.212:3000/")
            .addConverterFactory(GsonConverterFactory.create()).build();
        val service = retrofit.create(RetrofitService::class.java)
        button_next.setOnClickListener {
            var nickname = textInputEditText.text.toString()
            val RegisterRequest = Register(email = email, pw = pw,nickname = nickname)
            service.LocalRegister(RegisterRequest).enqueue(object : Callback<RegisterGet> {
                override fun onResponse(call: Call<RegisterGet>, response: Response<RegisterGet>) {
                    if (response.isSuccessful) {
                        var login = response.body()
                        println("성공" + login?.type + login?.message)
                        if(login?.type == "false"){
                        }
                        else{
                            intent = Intent(this@Registernickname, Groupexist::class.java)
                            intent.putExtra("email",email)
                            startActivity((intent))
                        }
                    } else {
                        println("실패")
                    }
                }
                override fun onFailure(call: Call<RegisterGet>, t: Throwable) {
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    println("에러: " + t.message.toString());
                }
            })

        }
        textInputEditText.onFocusChangeListener = View.OnFocusChangeListener{ view, hasFocus ->
            if(hasFocus){
                textInputEditText.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.banda)
            } else {
                textInputEditText.backgroundTintList = ContextCompat.getColorStateList(applicationContext, android.R.color.darker_gray)
            }
        }
    }
}