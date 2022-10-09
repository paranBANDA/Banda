package com.example.banda.Register

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.banda.R
import com.example.banda.RetrofitService
import com.example.banda.data.Register
import com.example.banda.data.RegisterGet
import kotlinx.android.synthetic.main.activity_dogregister.*
import kotlinx.android.synthetic.main.activity_dogregister.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Dogregister : AppCompatActivity() {


    val DOG_GENDER_WOMAN: Int = 0;
    val DOG_GENDER_MAN: Int = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogregister)

        var dogName = editTextDogName.text.toString()
        var dogBreed = editTextDogBreed.text.toString()
        var dogBirthday = dogBirthdayDate.text.toString()
        var dogGender = DOG_GENDER_WOMAN


        val retrofit = Retrofit.Builder().baseUrl("http://13.124.202.212:3000/")
            .addConverterFactory(GsonConverterFactory.create()).build();
        val service = retrofit.create(RetrofitService::class.java)
        radioGroupDogGender.setOnCheckedChangeListener{ r, id ->
            when(id) {
                R.id.woman -> {
                    Log.d("radio", "woman");
                    dogGender = DOG_GENDER_WOMAN
                }
                R.id.man -> {
                    Log.d("radio", "man");
                    dogGender = DOG_GENDER_MAN
                }
                else -> {
                    Log.d("radio", "?");
                }
            }
        }
        button2_register.setOnClickListener {

            Log.d("ASDAS", dogName +" "+  dogBreed + " "+ dogBirthday + " " + dogGender);

            AlertDialog.Builder(this)
                .setTitle("반려견이 더 있으신가요?")
                .setPositiveButton("네!", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        Log.d("MyTag", "강아지 추가 등록")

                        //여기에 강아지등록 리퀘스트 보내기
                        //val RegisterRequest = Register(name = dogName, breed = dogBreed)
                    }
                })
                .setNegativeButton("아니오", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        Log.d("MyTag", "완료")

                        intent = Intent(this@Dogregister, Finalregister::class.java)
                        startActivity((intent))
                        //여기에 강아지등록 리퀘스트 보내기
                        //val RegisterRequest = Register(name = dogName, breed = dogBreed)
                    }
                })
                .create()
                .show()

        }
    }
}