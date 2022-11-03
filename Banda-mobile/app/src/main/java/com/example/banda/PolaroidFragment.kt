import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.android.kakaologin.DogProfileData
import com.example.banda.PolariodAdapter
import com.example.banda.R

class PolaroidFragment : Fragment() {

    val datas = mutableListOf<DogProfileData>()

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
            add(DogProfileData(name = "김만두", birth = "2021.12.03", img = "nothing", gender = 0, breed = "래브라도 리트리버"))
            add(DogProfileData(name = "김두부", birth = "2020.01.21", img = "nothing", gender = 1, breed = "골든 리트리버"))
            add(DogProfileData(name = "김두부", birth = "2020.01.21", img = "nothing", gender = 1, breed = "골든 리트리버"))
        }
        val viewPager = getView()?.findViewById<ViewPager2>(R.id.viewPager)

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