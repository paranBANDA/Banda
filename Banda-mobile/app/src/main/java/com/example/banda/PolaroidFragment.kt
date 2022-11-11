package com.example.banda
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.NumberPicker
import android.widget.TextView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.banda.DogProfileData
import com.example.banda.PolariodAdapter
import com.example.banda.R
import com.example.banda.polaroid.ChangeDogDialog
import com.example.banda.polaroid.PolaroidData
import java.util.*

class PolaroidFragment : Fragment() {

    val datas = mutableListOf<PolaroidData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_polaroid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        datas.apply {
            add(PolaroidData(
                dogDiaryImageUrl = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fk.kakaocdn.net%2Fdn%2Fd9Ei8k%2FbtrQptZh9B4%2FF98lmVqbx8SihvFGCZRF5K%2Fimg.jpg",
                dogDiaryText = "Cute Seagull..",
                masterDiaryImageUrl = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fk.kakaocdn.net%2Fdn%2FbMMpAU%2FbtrQm9mYv3D%2FZspkO07KHvN6X6JQCUkjT0%2Fimg.png",
                masterDiaryText = "Cute Toto..."))
            add(PolaroidData(
                dogDiaryImageUrl = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fk.kakaocdn.net%2Fdn%2FbSI0Di%2FbtrQo9zZDw3%2FzDIY4OjXA3cUk53kH2dCv0%2Fimg.jpg",
                dogDiaryText = "Zoo cha geum ji...",
                masterDiaryImageUrl = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fk.kakaocdn.net%2Fdn%2FVMMHK%2FbtrQpxHnZ4s%2FY5rZheHBkOKx29hoZtUHFk%2Fimg.png",
                masterDiaryText = "Cute Toto..."))
            add(PolaroidData(
                dogDiaryImageUrl = "https://i.ibb.co/brCsKHT/img1.jpg",
                dogDiaryText = "Cute Seagull..",
                masterDiaryImageUrl = "",
                masterDiaryText = "Cute Toto..."))
            add(PolaroidData(
                dogDiaryImageUrl = "https://t1.daumcdn.net/cfile/tistory/99C0D7335A159E890A",
                dogDiaryText = "Cute Seagull..",
                masterDiaryImageUrl = "http://goo.gl/gEgYUd",
                masterDiaryText = "Cute Toto..."))
        }
        val viewPager = getView()?.findViewById<ViewPager2>(R.id.viewPager)
        val dogName = getView()?.findViewById<TextView>(R.id.polaroidDogName)
        val yearPicker = getView()?.findViewById<NumberPicker>(R.id.numberPickerYear)
        val monthPicker = getView()?.findViewById<NumberPicker>(R.id.numberPickerMonth)
        val dayPicker = getView()?.findViewById<NumberPicker>(R.id.numberPickerDay)

        val cal = Calendar.getInstance();

        yearPicker?.minValue = 1900
        yearPicker?.maxValue = 2300
        yearPicker?.value = cal.get(Calendar.YEAR)

        monthPicker?.minValue = 1
        monthPicker?.maxValue = 12
        monthPicker?.value = cal.get(Calendar.MONTH) + 1

        dayPicker?.minValue = 1
        dayPicker?.maxValue = 31
        dayPicker?.value = cal.get(Calendar.DAY_OF_MONTH)

        dogName?.setOnClickListener {
            ChangeDogDialog(requireContext()).show()
        }
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        viewPager?.adapter = PolariodAdapter(datas)
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
                Log.d("ViewPagerFragment", "Page ${position+1}")
            }
        })


    }

}