package com.example.banda

import HappyDayDecorator
import SaturdayDecorator
import WeekendDecorator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.kakaologin.DogProfileAdapter
import com.android.kakaologin.DogProfileData
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var calendarView: MaterialCalendarView? = null
    lateinit var profileAdapter: DogProfileAdapter
    val datas = mutableListOf<DogProfileData>()
    var recyclerView: RecyclerView? = null

    private fun initRecycler() {


        datas.apply {
            add(DogProfileData(name = "김만두", birth = "2021.12.03", img = "nothing", gender = 0, breed = "래브라도 리트리버"))
            add(DogProfileData(name = "김두부", birth = "2020.01.21", img = "nothing", gender = 1, breed = "골든 리트리버"))



        }
        profileAdapter = DogProfileAdapter(datas)

        recyclerView?.adapter = profileAdapter
        //profileAdapter.notifyDataSetChanged()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        calendarView = findViewById<MaterialCalendarView>(R.id.calendarView)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
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
            calendarView?.addDecorator(HappyDayDecorator(this, calDay))


    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }
}