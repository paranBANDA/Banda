package com.example.banda
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.banda.data.*
import com.example.banda.polaroid.ChangeDogDialog
import com.example.banda.polaroid.PolaroidData
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PolaroidFragment : Fragment() {

    private var dogName: TextView? = null
    var addTextButton: Button? = null
    private var polaroidAdapter: PolariodAdapter? = null
    val datas = mutableListOf<PolaroidData>()
    var firstDogName = ""
    var email = ""
    var diarytext = ""
    var dtext = ""
    var diarydate = "1999-03-04"
    var currentPosition = 0;
//    lateinit var PolariodAdapter: PolariodAdapter

    private var service: RetrofitService? = null
    private fun loadFirstDog(){
        val pref = activity?.getSharedPreferences("pref",0)
        firstDogName = pref?.getString("pet","").toString()
    }
    private fun loadUserEmail(){
        val pref = activity?.getSharedPreferences("pref",0)
        email = pref?.getString("email","").toString()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_polaroid, container, false)
    }
    private fun uploadimage(){
        val imageRequest = DiaryPost(picture = "이부분 사진 url 추가필요", userId = email, petId = firstDogName,date = "오늘날짜 넣을 필요")
        service?.uploadDiaryImage(imageRequest)?.enqueue(object : Callback<DiaryTextGet> {
            override fun onResponse(call: Call<DiaryTextGet>, response: Response<DiaryTextGet>) {
                if (response.isSuccessful) {
                    //successful 했을 때의 액션
                } else {
                    println("실패")
                }
            }

            override fun onFailure(call: Call<DiaryTextGet>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                println("에러: " + t.message.toString());
            }
        })
    }

    val changeDog: (data: DogProfileData ) -> Int = { data ->
        datas.clear()
        dogName?.text = data.name
        firstDogName = data.name
        val Diaryrequest = FindDiary(email = email, petname = data.name)
        service?.getDiaryByPet(Diaryrequest)?.enqueue(object : Callback<DiaryGet> {
            override fun onResponse(call: Call<DiaryGet>, response: Response<DiaryGet>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        var diarydata = body.data
                        for (i in diarydata) {
                            datas.apply {
                                add(
                                    PolaroidData(
                                        dogDiaryImageUrl = i.picture,
                                        dogDiaryText = i.text,
                                        masterDiaryText = i.text,
                                        Diarydate = i.date.substring(0,10)
                                    )
                                )
                            }
                        }
                        polaroidAdapter?.notifyDataSetChanged()
                    }
                } else {
                    println("실패")
                }
            }

            override fun onFailure(call: Call<DiaryGet>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                println("에러: " + t.message.toString());
            }
        })
        1;
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFirstDog()
        loadUserEmail()
        datas.clear()
        val viewPager = getView()?.findViewById<ViewPager2>(R.id.viewPager)
        dogName = getView()?.findViewById<TextView>(R.id.polaroidDogName)
        val yearPicker = getView()?.findViewById<NumberPicker>(R.id.numberPickerYear)
        val monthPicker = getView()?.findViewById<NumberPicker>(R.id.numberPickerMonth)
        val dayPicker = getView()?.findViewById<NumberPicker>(R.id.numberPickerDay)
        val btn_submit = getView()?.findViewById<Button>(R.id.btn_submit)
        yearPicker?.setOnValueChangedListener { numberPicker, i, i2 ->
            Log.d("jun", i.toString() + " " + i2.toString())
        }
        monthPicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            Log.d("jun", oldVal.toString() + " " + newVal.toString());
        }
        dayPicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            Log.d("jun", oldVal.toString() + " " + newVal.toString());
        }


        addTextButton = getView()?.findViewById<Button>(R.id.addTextButton)

        addTextButton?.setOnClickListener {
            dtext = polaroidAdapter?.getBackDiaryText(currentPosition) ?: ""
            println(diarytext)
            println(diarydate)
            println(email)
            println(firstDogName)
            println(dtext)
            val Diarytextrequest = DiaryTextPost(email = email, petname = firstDogName, text = dtext, date = diarydate)
            service?.addDiaryText(Diarytextrequest)?.enqueue(object : Callback<DiaryTextGet> {
                override fun onResponse(call: Call<DiaryTextGet>, response: Response<DiaryTextGet>) {
                    if (response.isSuccessful) {
                        val body = response.body()

                    } else {
                        println("실패")
                    }
                }

                override fun onFailure(call: Call<DiaryTextGet>, t: Throwable) {
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    println("에러: " + t.message.toString());
                }
            })
        }
        val retrofit = Retrofit.Builder().baseUrl("http://13.124.202.212:3000/")
            .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(RetrofitService::class.java)
        dogName?.text = firstDogName
        val Diaryrequest = FindDiary(email = email, petname = firstDogName)
        runBlocking {
            launch {
                service?.getDiaryByPet(Diaryrequest)?.enqueue(object : Callback<DiaryGet> {
                    override fun onResponse(call: Call<DiaryGet>, response: Response<DiaryGet>) {
                        if (response.isSuccessful) {
                            val body = response.body()
                            if (body != null) {
                                var diarydata = body.data
                                for (i in diarydata) {
                                    datas.apply {
                                        add(
                                            PolaroidData(
                                                dogDiaryImageUrl = i.picture,
                                                dogDiaryText = i.text,
                                                masterDiaryText = i.text,
                                                Diarydate = i.date.substring(0,10)
                                            )
                                        )
                                    }
                                }
                                polaroidAdapter?.notifyDataSetChanged()
                            }
                        } else {
                            println("실패")
                        }
                    }

                    override fun onFailure(call: Call<DiaryGet>, t: Throwable) {
                        // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                        println("에러: " + t.message.toString());
                    }
                })
            }
        }
        yearPicker?.minValue = 1900
        yearPicker?.maxValue = 2300


        monthPicker?.minValue = 1
        monthPicker?.maxValue = 12

        dayPicker?.minValue = 1
        dayPicker?.maxValue = 31

        dogName?.setOnClickListener {
            ChangeDogDialog(requireContext(), changeDog).show()
        }
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        polaroidAdapter = PolariodAdapter(datas)
        viewPager?.adapter = polaroidAdapter
        viewPager?.offscreenPageLimit = 4
        // item_view 간의 양 옆 여백을 상쇄할 값
        val offsetBetweenPages = resources.getDimensionPixelOffset(R.dimen.offsetBetweenPages).toFloat()
        var transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(8))

        transform.addTransformer(ViewPager2.PageTransformer{ view: View, fl: Float ->
            var v = 1-Math.abs(fl)
            view.scaleY = 0.8f + v * 0.2f
        })
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pagerWidth)
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        viewPager?.setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
        }
        viewPager?.setPageTransformer(transform)

        viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            // 관리하는 페이지 수. default = 1

            // Paging 완료되면 호출
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPosition = position
                diarydate = datas[position].Diarydate
                diarytext = datas[position].dogDiaryText
                dtext = datas[position].masterDiaryText
                yearPicker?.value = diarydate.substring(0,4).toInt()
                monthPicker?.value = diarydate.substring(5,7).toInt()
                dayPicker?.value = diarydate.substring(8,10).toInt()
            }
        })


    }

}