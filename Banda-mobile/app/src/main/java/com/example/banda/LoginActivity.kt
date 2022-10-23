package com.example.banda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.banda.data.Login
import com.example.banda.data.LoginGet
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.Toast
import com.example.banda.Register.RegisterActivity


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val retrofit = Retrofit.Builder().baseUrl("http://13.124.202.212:3000/")
            .addConverterFactory(GsonConverterFactory.create()).build();
        val service = retrofit.create(RetrofitService::class.java)
        btn_login.setOnClickListener {
            var email = edit_id.text.toString()
            var pw = edit_pw.text.toString()
            val loginRequest = Login(email = email, pw = pw)
            service.LocalLogin(loginRequest).enqueue(object : Callback<LoginGet>{
                override fun onResponse(call: Call<LoginGet>, response: Response<LoginGet>) {
                    if (response.isSuccessful) {
                        var login = response.body()
                        println("성공" + login?.type + login?.data + login?.token)
                        if(login?.type == "false"){
                            Toast.makeText(this@LoginActivity, "아이디와 비밀번호를 확인해 주세", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            intent = Intent(this@LoginActivity,MainActivity::class.java)
                            startActivity((intent))
                        }
                    } else {
                        println("실패")
                    }
                }
                override fun onFailure(call: Call<LoginGet>, t: Throwable) {
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    println("에러: " + t.message.toString());
                }
            })
        }
        btn_register.setOnClickListener(({
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity((intent))
        }))

    }
}