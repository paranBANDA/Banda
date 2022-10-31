package com.example.banda.Register

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import android.app.DatePickerDialog
import com.example.banda.R
import com.example.banda.RetrofitService
import com.example.banda.data.Groupcheck
import com.example.banda.data.PetRegister
import com.example.banda.data.Register
import com.example.banda.data.RegisterGet
import kotlinx.android.synthetic.main.activity_dogregister.*
import kotlinx.android.synthetic.main.activity_dogregister.view.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class Dogregister : AppCompatActivity() {
    val DOG_GENDER_WOMAN: Int = 0;
    val DOG_GENDER_MAN: Int = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogregister)
        val secondintent = getIntent()
        var dogMeetDateString = ""
        var dogBirthdayString = ""
        val email = secondintent.getStringExtra("email")
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
        dogBirthdayDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                dogBirthdayString = "${year}년 ${month+1}월 ${dayOfMonth}일"
                dogBirthdayDate.setText(dogBirthdayString)
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        dogMeetDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                dogMeetDateString = "${year}년 ${month+1}월 ${dayOfMonth}일"
                dogMeetDate.setText(dogMeetDateString)
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        button2_register.setOnClickListener {
            var dogName = editTextDogName.text.toString()
            var dogBreed = editTextDogBreed.text.toString()
            var dogBirthday = dogBirthdayString
            var dogMeetdate = dogMeetDateString
            Log.e("tag", "dogName : $dogName + dogBreed : $dogBreed + dogBirthday : $dogBirthday + dogGender : $dogGender + dogMeetday : $dogMeetdate")

            AlertDialog.Builder(this)
                .setTitle("반려견이 더 있으신가요?")
                .setPositiveButton("네!", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        Log.d("MyTag", "강아지 추가 등록")
                        val petRequest = PetRegister(dogName = dogName,dogBreed = dogBreed, dogGender = dogGender,dogBirthday = dogBirthday,dogMeetdate = dogMeetdate,email = email)
                        service.PetRegister(petRequest).enqueue(object : Callback<RegisterGet> {
                            override fun onResponse(call: Call<RegisterGet>, response: Response<RegisterGet>) {
                                if (response.isSuccessful) {
                                    var sucessdata = response.body()
                                    if (sucessdata?.type == "true") {

                                    }
                                    else{
                                        Toast.makeText(this@Dogregister, "형식을 확인해 주세요!.", Toast.LENGTH_SHORT).show()
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
                })
                .setNegativeButton("아니오", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        Log.d("MyTag", "완료")
                        val petRequest = PetRegister(dogName = dogName,dogBreed = dogBreed, dogGender = dogGender,dogBirthday = dogBirthday,dogMeetdate = dogMeetdate,email = email)
                        service.PetRegister(petRequest).enqueue(object : Callback<RegisterGet> {
                            override fun onResponse(call: Call<RegisterGet>, response: Response<RegisterGet>) {
                                if (response.isSuccessful) {
                                    var sucessdata = response.body()
                                    if (sucessdata?.type == "true") {
                                        intent = Intent(this@Dogregister, Finalregister::class.java)
                                        startActivity((intent))
                                    }
                                    else{
                                        Toast.makeText(this@Dogregister, "형식을 확인해 주세요!.", Toast.LENGTH_SHORT).show()
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
                })
                .create()
                .show()

        }
        editTextDogName.onFocusChangeListener = View.OnFocusChangeListener{ view, hasFocus ->
            if(hasFocus){
                editTextDogName.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.banda)
            } else {
                editTextDogName.backgroundTintList = ContextCompat.getColorStateList(applicationContext, android.R.color.darker_gray)
            }
        }
        editTextDogBreed.onFocusChangeListener = View.OnFocusChangeListener{ view, hasFocus ->
            if(hasFocus){
                editTextDogBreed.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.banda)
            } else {
                editTextDogBreed.backgroundTintList = ContextCompat.getColorStateList(applicationContext, android.R.color.darker_gray)
            }
        }
        dogBirthdayDate.onFocusChangeListener = View.OnFocusChangeListener{ view, hasFocus ->
            if(hasFocus){
                dogBirthdayDate.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.banda)
            } else {
                dogBirthdayDate.backgroundTintList = ContextCompat.getColorStateList(applicationContext, android.R.color.darker_gray)
            }
        }
        dogMeetDate.onFocusChangeListener = View.OnFocusChangeListener{ view, hasFocus ->
            if(hasFocus){
                dogMeetDate.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.banda)
            } else {
                dogMeetDate.backgroundTintList = ContextCompat.getColorStateList(applicationContext, android.R.color.darker_gray)
            }
        }
    }
}