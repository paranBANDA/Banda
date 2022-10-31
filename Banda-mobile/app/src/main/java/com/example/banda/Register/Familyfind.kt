package com.example.banda.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.banda.R
import com.example.banda.RetrofitService
import com.example.banda.data.FamilyId
import com.example.banda.data.Groupcheck
import com.example.banda.data.Register
import com.example.banda.data.RegisterGet
import kotlinx.android.synthetic.main.activity_familyfind.*
import kotlinx.android.synthetic.main.activity_familyfind.button_next
import kotlinx.android.synthetic.main.activity_registernickname.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Familyfind : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_familyfind)
        val retrofit = Retrofit.Builder().baseUrl("http://13.124.202.212:3000/")
            .addConverterFactory(GsonConverterFactory.create()).build();
        val service = retrofit.create(RetrofitService::class.java)
        button_next.setOnClickListener{
            val email = emailinput.text.toString()
            val emailRequest = Groupcheck(email = email)
            service.GroupCheck(emailRequest).enqueue(object : Callback<RegisterGet> {
                override fun onResponse(call: Call<RegisterGet>, response: Response<RegisterGet>) {
                    if (response.isSuccessful) {
                        var sucessdata = response.body()
                        if (sucessdata?.type == "true") {
                            val familyId = sucessdata.data
                            Log.e("tag", "familyid : $familyId ")
//                            intent = Intent(this@RegisterActivity, Registernickname::class.java)
//                            intent.putExtra("email",id)
//                            intent.putExtra("pw",pw)
//                            startActivity((intent))
                            intent = Intent(this@Familyfind, Finalregister::class.java)
                            startActivity((intent))
                        }
                        else{
                            Toast.makeText(this@Familyfind, "해당 이메일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
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
        emailinput.onFocusChangeListener = View.OnFocusChangeListener{ view, hasFocus ->
            if(hasFocus){
                emailinput.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.banda)
            } else {
                emailinput.backgroundTintList = ContextCompat.getColorStateList(applicationContext, android.R.color.darker_gray)
            }
        }
    }
}