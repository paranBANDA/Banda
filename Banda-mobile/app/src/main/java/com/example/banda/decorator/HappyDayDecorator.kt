package com.example.banda.decorator

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.example.banda.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class HappyDayDecorator(context: Activity?, currentDay: CalendarDay) : DayViewDecorator {
    private val drawable: Drawable = ContextCompat.getDrawable(context as Context, R.drawable.ic_happy_icon)!!
    private var myDay = currentDay
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day == myDay
    }

    override fun decorate(view: DayViewFacade) {
        //view.setSelectionDrawable(drawable!!)
        view.setBackgroundDrawable(drawable)
        view.setDaysDisabled(true)
        view.addSpan(ForegroundColorSpan(Color.argb(0,0,0,0)))
    }

    init {
        // You can set background for Decorator via drawable here
    }
}