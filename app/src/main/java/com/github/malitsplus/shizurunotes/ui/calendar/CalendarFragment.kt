package com.github.malitsplus.shizurunotes.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.databinding.FragmentCalendarBinding
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import java.time.LocalDate


class CalendarFragment : Fragment(),
    CalendarView.OnCalendarSelectListener,
    CalendarView.OnMonthChangeListener {

    private lateinit var binding: FragmentCalendarBinding
    private lateinit var calendarVM: CalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(layoutInflater, container, false)
        calendarVM = ViewModelProvider(requireActivity())[CalendarViewModel::class.java]
        calendarVM.initData()
        binding.calendarToolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }

        setOptionItemClickListener(binding.calendarToolbar)

        binding.calendarView.apply {
            setSchemeDate(calendarVM.calendarMap)
            setOnMonthChangeListener(this@CalendarFragment)
            setOnCalendarSelectListener(this@CalendarFragment)
            onMonthChange(LocalDate.now().year, LocalDate.now().monthValue)
        }

        return binding.root
    }

    private fun setOptionItemClickListener(toolbar: Toolbar) {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_notification -> {
                    Navigation.findNavController(binding.root).navigate(CalendarFragmentDirections.actionNavCalendarToNavEventNotification())
                }
                else -> {
                }
            }
            true
        }
    }

    override fun onMonthChange(year: Int, month: Int) {
        binding.calendarToolbar.title = year.toString() + I18N.getString(R.string.text_year) + month + I18N.getString(R.string.text_month)
    }

    override fun onCalendarSelect(calendar: Calendar, isClick: Boolean) {
        if (!isClick) return
        calendarVM.selectedDay = calendar.toString()
        Navigation.findNavController(binding.root).navigate(CalendarFragmentDirections.actionNavCalendarToNavDay())
    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {}
}
