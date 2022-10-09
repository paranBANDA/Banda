package com.example.banda.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.banda.R
import com.example.banda.RetrofitService
import com.example.banda.data.Register
import com.example.banda.data.RegisterGet
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
        val RegisterRequest = Register(email = id, pw = pw)
        service.EmailCheck(RegisterRequest).enqueue(object : Callback<RegisterGet> {
            override fun onResponse(call: Call<RegisterGet>, response: Response<RegisterGet>) {
                if (response.isSuccessful) {
                    var sucessdata = response.body()
                    if (sucessdata?.type == "true") {
                        intent = Intent(this@RegisterActivity, Registernickname::class.java)
                        intent.putExtra("email",id)
                        intent.putExtra("pw",pw)
                        startActivity((intent))
                    }
                    else{
                        Toast.makeText(this@RegisterActivity, "이미 가입된 이메일 입니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                        println("Failed")
                }
            }
            override fun onFailure(call: Call<RegisterGet>, t: Throwable) {
                println("에러: " + t.message.toString());
            }
        })
    }
}
}