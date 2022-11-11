package com.example.banda.polaroid

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.kakaologin.DogProfileAdapter
import com.example.banda.DogProfileData
import com.example.banda.PolariodAdapter
import com.example.banda.RetrofitService
import com.example.banda.data.Groupcheck
import com.example.banda.data.UserPet
import com.example.banda.databinding.ChangeDogDialogBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat

class ChangeDogDialog(context: Context): Dialog(context) {

    private lateinit var binding: ChangeDogDialogBinding

    val datas = mutableListOf<DogProfileData>()
    var email = ""
    private var service: RetrofitService? = null
    private fun loadUserEmail(){
        val pref = context.getSharedPreferences("pref",0)
        email = pref?.getString("email","").toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 만들어놓은 dialog_profile.xml 뷰를 띄운다.
        loadUserEmail()
        binding = ChangeDogDialogBinding.inflate(layoutInflater)
        // 뒤로가기 버튼, 빈 화면 터치를 통해 dialog가 사라지지 않도록
        //setCancelable(false)
        binding.changeDogDialogRecyclerView.adapter = DogProfileAdapter(datas)
        val retrofit = Retrofit.Builder().baseUrl("http://13.124.202.212:3000/")
            .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(RetrofitService::class.java)
        val UserRequest = Groupcheck(email = email)
        runBlocking {
            launch {
                service?.ShowUserPet(UserRequest)?.enqueue(object : Callback<UserPet> {
                    override fun onResponse(call: Call<UserPet>, response: Response<UserPet>) {
                        if (response.isSuccessful) {
                            val body = response.body()
                            if (body != null) {
                                var petData = body.data
                                for (i in petData) {
                                    datas.apply {
                                        add(
                                            DogProfileData(
                                                name = i.petname,
                                                birth = SimpleDateFormat("yyyy-MM-dd").format(i.birthday),
                                                img = "",
                                                gender = i.gender,
                                                breed = i.breed,
                                                meetday = SimpleDateFormat("yyyy-MM-dd").format(i.meetday),
                                                petId = 1
                                            )
                                        )
                                        println(datas)
                                    }
                                }
                                binding.changeDogDialogRecyclerView.adapter?.notifyDataSetChanged()
                            }
                        } else {
                            println("실패")
                        }
                    }

                    override fun onFailure(call: Call<UserPet>, t: Throwable) {
                        // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                        println("에러: " + t.message.toString());
                    }
                })
            }
        }


        binding.changeDogDialogRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() = with(binding) {

        // background를 투명하게 만듦
        // (중요) Dialog는 내부적으로 뒤에 흰 사각형 배경이 존재하므로, 배경을 투명하게 만들지 않으면
        // corner radius의 적용이 보이지 않는다.
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // OK Button 클릭에 대한 Callback 처리. 이 부분은 상황에 따라 자유롭게!
    }
}