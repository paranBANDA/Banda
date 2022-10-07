package com.example.banda.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.banda.R
import com.example.banda.RetrofitService
import com.example.banda.data.Register
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

    val retrofit = Retrofit.Builder().baseUrl("http://13.124.202.212:3000/")
        .addConverterFactory(GsonConverterFactory.create()).build();
    val service = retrofit.create(RetrofitService::class.java)

    btn_next.setOnClickListener {
        var id = editTextTextEmailAddress.text.toString()
        var pw = editTextTextPassword.text.toString()
        Log.d(id,pw+"hello")
        val RegisterRequest = Register(email = id, pw = pw)
        service.LocalRegister(RegisterRequest).enqueue(object : Callback<Register> {
            override fun onResponse(call: Call<Register>, response: Response<Register>) {
                if (response.isSuccessful) {
                    println("성공")
                    intent = Intent(this@RegisterActivity, Registernickname::class.java)
                    startActivity((intent))
                } else {
                    println("실패")
                }
            }
            override fun onFailure(call: Call<Register>, t: Throwable) {
                println("에러: " + t.message.toString());
            }
        })
    }
}
}