package com.example.banda

import com.example.banda.decorator.HappyDayDecorator
import SaturdayDecorator
import WeekendDecorator
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.android.kakaologin.DogProfileAdapter
import com.example.banda.data.Groupcheck
import com.example.banda.data.InfoPetFeel
import com.example.banda.data.UserPet
import com.example.banda.decorator.BadDayDecorator
import com.example.banda.decorator.NormalDayDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainPageFragment : Fragment()  {
    private var service: RetrofitService? = null
    var calendarView: MaterialCalendarView? = null
    lateinit var profileAdapter: DogProfileAdapter
    val datas = mutableListOf<DogProfileData>()
    var viewPager: ViewPager2? = null
    var email = ""

    private fun loadUserEmail(){
        val pref = activity?.getSharedPreferences("pref",0)
        email = pref?.getString("email","").toString()
    }

    private fun initRecycler() {
        profileAdapter = DogProfileAdapter(datas)
        viewPager?.adapter = profileAdapter

        viewPager?.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }


            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                service?.PetInfo(viewPager?.adapter?.getItemId(position)?.toInt()!!)?.enqueue(object : Callback<InfoPetFeel> {

                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onResponse(
                        call: Call<InfoPetFeel>,
                        response: Response<InfoPetFeel>
                    ) {
                        Log.d("Asd", viewPager?.adapter?.getItemId(position)?.toInt()!!.toString())
                        Log.d("GET PET FEEL", response.body()?.feel.toString())

                        Log.d("GET PET FEEL", response.body()?.date.toString())
                        val calList = ArrayList<CalendarDay>()
                        calendarView?.removeDecorators()
                        var i = 0;
                        try {
                            for(d in response.body()?.date!!){
                                val feel = response.body()?.feel!![i++]
                                val date = LocalDate.parse(d, DateTimeFormatter.ISO_DATE_TIME)
                                calendarView?.addDecorator(
                                    when (feel) {
                                        "Bad" -> {
                                            BadDayDecorator(activity,
                                                CalendarDay.from(date.year, date.month.value - 1, date.dayOfMonth))
                                        }
                                        "Normal" -> {
                                            NormalDayDecorator(activity,
                                                CalendarDay.from(date.year, date.month.value - 1, date.dayOfMonth))
                                        }
                                        else -> {
                                            HappyDayDecorator(activity,
                                                CalendarDay.from(date.year, date.month.value - 1, date.dayOfMonth))
                                        }
                                    }
                                )
                            }
                        } catch (e : Exception) {

                        }

/*

                        calList.add(CalendarDay.from(2022, Calendar.OCTOBER, 21))
                        calList.add(CalendarDay.from(2022, Calendar.OCTOBER, 22))
                        calList.add(CalendarDay.from(2022, Calendar.OCTOBER, 23))

                        calendarView?.addDecorator(WeekendDecorator())
                        calendarView?.addDecorator(SaturdayDecorator())

                        for(calDay in calList)
                            calendarView?.addDecorator(com.example.banda.decorator.HappyDayDecorator(activity, CalendarDay.from(2022, Calendar.OCTOBER, 23)))*/

                    }

                    override fun onFailure(call: Call<InfoPetFeel>, t: Throwable) {
                        Log.d("GET PET FEEL fAILED", "ASD")
                    }
                })
                Log.d("ASDASD", position.toString());

            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
        //profileAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadUserEmail()

        val retrofit = Retrofit.Builder().baseUrl("http://13.124.202.212:3000/")
            .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(RetrofitService::class.java)
        val UserRequest = Groupcheck(email = email)
        service?.ShowUserPet(UserRequest)?.enqueue(object : Callback<UserPet> {
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

        viewPager = view.findViewById<ViewPager2>(R.id.recyclerView)
        calendarView = view.findViewById<MaterialCalendarView>(R.id.calendarView)
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
        datas.apply {
            add(DogProfileData(name = "김반", birth = "2021.12.03", img = "", gender = 0, breed = "래브라도 리트리버", petId = 0))
            add(DogProfileData(name = "김두부", birth = "2020.01.21", img = "http://goo.gl/gEgYUd", gender = 1, breed = "골든 리트리버", petId = 1))
            add(DogProfileData(name = "김미모", birth = "2020.01.21", img = "", gender = 1, breed = "골든 리트리버", petId = 100))
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false)
    }

}