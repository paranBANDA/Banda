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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import java.text.SimpleDateFormat

class MainPageFragment : Fragment()  {
    private var service: RetrofitService? = null
    var calendarView: MaterialCalendarView? = null
    lateinit var profileAdapter: DogProfileAdapter
    val datas = mutableListOf<DogProfileData>()
    var showdatas = mutableListOf<DogProfileData>()
    var viewPager: ViewPager2? = null
    var email = ""

    private fun loadUserEmail(){
        val pref = activity?.getSharedPreferences("pref",0)
        email = pref?.getString("email","").toString()
    }
    private fun saveUserDog(pet : String){
        val pref = activity?.getSharedPreferences("pref",0)
        val edit = pref?.edit()
        edit?.putString("pet",pet)
        edit?.apply()
        println("preferences success")
    }

    val temp: (DogProfileData) -> Int = { data ->

        Log.d("DOG CHANGE", data.toString());

        1;
    }
    private fun initRecycler() {
        profileAdapter = DogProfileAdapter(datas, temp, null)
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
        datas.clear()
        loadUserEmail()
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
                                saveUserDog(petData[0].petname)
                                for (i in petData) {
                                    datas.apply {
                                        add(
                                            DogProfileData(
                                                name = i.petname,
                                                birth = SimpleDateFormat("yyyy-MM-dd").format(i.birthday),
                                                img = i.image,
                                                gender = i.gender,
                                                breed = i.breed,
                                                meetday = SimpleDateFormat("yyyy-MM-dd").format(i.meetday),
                                                petId = 1
                                            )
                                        )
                                        println(datas)
                                    }
                                }
                            profileAdapter.notifyDataSetChanged()
                            }
                        } else {
                            println("??????")
                        }
                    }

                    override fun onFailure(call: Call<UserPet>, t: Throwable) {
                        // ?????? ?????? (????????? ??????, ?????? ?????? ??? ??????????????? ??????)
                        println("??????: " + t.message.toString());
                    }
                })
            }
        }
        viewPager = view.findViewById<ViewPager2>(R.id.recyclerView)
        calendarView = view.findViewById<MaterialCalendarView>(R.id.calendarView)
        initRecycler()

        Log.d("ASD", datas.toString());

        calendarView?.setWeekDayFormatter( ArrayWeekDayFormatter(
            arrayOf("???", "???", "???", "???", "???", "???", "???")
        )
        )
        calendarView?.setTitleFormatter(TitleFormatter { day -> // CalendarDay?????? ???????????? LocalDate ???????????? ???????????? ???????????? ????????????
            // ????????? MaterialCalendarView?????? ???/??? ??????????????? ?????????????????? CalendarDay ????????? getDate()??? ???/?????? ?????? ?????? LocalDate ????????? ?????????
            // LocalDate??? ???????????? ????????? ????????????

            val calendarHeaderBuilder = StringBuilder()
            calendarHeaderBuilder.append(day.year)
                .append(".")
                .append(day.month + 1)
            calendarHeaderBuilder.toString()
        })

        val calList = ArrayList<CalendarDay>()
        calList.add(CalendarDay.from(2022, Calendar.DECEMBER, 7))
        calList.add(CalendarDay.from(2022, Calendar.DECEMBER, 8))
        calList.add(CalendarDay.from(2022, Calendar.DECEMBER, 9))
        calList.add(CalendarDay.from(2022, Calendar.DECEMBER, 10))

        calendarView?.addDecorator(WeekendDecorator())
        calendarView?.addDecorator(SaturdayDecorator())
        calendarView?.addDecorator(BoldDecorator())
        calendarView?.addDecorator(HappyDayDecorator(activity, calList[0]))
        calendarView?.addDecorator(BadDayDecorator(activity, calList[1]))
        calendarView?.addDecorator(NormalDayDecorator(activity, calList[2]))
        calendarView?.addDecorator(HappyDayDecorator(activity, calList[3]))


    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false)
    }

}