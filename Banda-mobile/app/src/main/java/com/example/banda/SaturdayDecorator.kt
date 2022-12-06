import android.graphics.Color
import android.text.style.ForegroundColorSpan
import androidx.core.graphics.toColor
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.*

class SaturdayDecorator: DayViewDecorator {
    private val calendar = Calendar.getInstance(Locale.getDefault())
    private val saturdayColor = Color.parseColor("blue")
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        day?.copyTo(calendar)
        val weekDay= calendar.get(Calendar.DAY_OF_WEEK)
        return  weekDay == Calendar.SATURDAY
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(ForegroundColorSpan(saturdayColor))
    }
}