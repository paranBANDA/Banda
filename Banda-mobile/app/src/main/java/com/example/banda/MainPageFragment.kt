package com.example.banda

import HappyDayDecorator
import SaturdayDecorator
import WeekendDecorator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.kakaologin.DogProfileAdapter
import com.android.kakaologin.DogProfileData
import com.example.banda.data.Groupcheck
import com.example.banda.data.UserPet
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class MainPageFragment : Fragment()  {
    var calendarView: MaterialCalendarView? = null
    lateinit var profileAdapter: DogProfileAdapter
    val datas = mutableListOf<DogProfileData>()
    var recyclerView: RecyclerView? = null
    var email = ""

    private fun loadUserEmail(){
        val pref = activity?.getSharedPreferences("pref",0)
        email = pref?.getString("email","").toString()
    }

    private fun initRecycler() {
        datas.apply {
            add(DogProfileData(name = "김반", birth = "2021.12.03", img = "nothing", gender = 0, breed = "래브라도 리트리버"))
            add(DogProfileData(name = "김두부", birth = "2020.01.21", img = "nothing", gender = 1, breed = "골든 리트리버"))
        }
        profileAdapter = DogProfileAdapter(datas)
        recyclerView?.adapter = profileAdapter
        //profileAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadUserEmail()

        val retrofit = Retrofit.Builder().baseUrl("http://13.124.202.212:3000/")
            .addConverterFactory(GsonConverterFactory.create()).build();
        val service = retrofit.create(RetrofitService::class.java)
        val UserRequest = Groupcheck(email = email)
        service.ShowUserPet(UserRequest).enqueue(object : Callback<UserPet> {
            override fun onResponse(call: Call<UserPet>, response: Response<UserPet>) {
                if (response.isSuccessful) {
                    println(email+response.body())
                } else {
                    println("실패")
                }
            }
            override fun onFailure(call: Call<UserPet>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                println("에러: " + t.message.toString());
            }
        })

        calendarView = view.findViewById<MaterialCalendarView>(R.id.calendarView)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView?.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        recyclerView?.itemAnimator = null

        initRecycler()

        Log.d("ASD", datas.toString());

        calendarView?.setWeekDayFormatter( ArrayWeekDayFormatter(
            arrayOf("일", "월", "화", "수", "목", "금", "토")
        )
        )
        calendarView?.setTitleFormatter(TitleFormatter { day -> // CalendarDay라는 클래스는 LocalDate 클래스를 기반으로 만들어진 클래스다
            // 때문에 MaterialCalendarView에서 연/월 보여주기를 커스텀하려면 CalendarDay 객체의 getDate()로 연/월을 구한 다음 LocalDate 객체에 넣어서
            // LocalDate로 변환하는 처리가 필요하다

            val calendarHeaderBuilder = StringBuilder()
            calendarHeaderBuilder.append(day.year)
                .append(".")
                .append(day.month + 1)
            calendarHeaderBuilder.toString()
        })

        val calList = ArrayList<CalendarDay>()
        calList.add(CalendarDay.from(2022, Calendar.OCTOBER, 20))
        calList.add(CalendarDay.from(2022, Calendar.OCTOBER, 21))
        calList.add(CalendarDay.from(2022, Calendar.OCTOBER, 22))
        calList.add(CalendarDay.from(2022, Calendar.OCTOBER, 23))

        calendarView?.addDecorator(WeekendDecorator())
        calendarView?.addDecorator(SaturdayDecorator())

        for(calDay in calList)
            calendarView?.addDecorator(HappyDayDecorator(activity, calDay))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false)
    }

}