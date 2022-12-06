import android.graphics.Color
import android.text.style.ForegroundColorSpan
import androidx.core.graphics.toColor
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.*

class WeekendDecorator: DayViewDecorator {
    private val calendar = Calendar.getInstance(Locale.getDefault())
    private val sundayColor = Color.parseColor("red")
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        day?.copyTo(calendar)
        val weekDay= calendar.get(Calendar.DAY_OF_WEEK)
        return  weekDay == Calendar.SUNDAY
    }

    override fun decorate(view: DayViewFacade) {
        val weekDay= calendar.get(Calendar.DAY_OF_WEEK)

        view.addSpan(ForegroundColorSpan(sundayColor))
    }
}