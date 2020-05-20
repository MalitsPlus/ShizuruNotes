package com.github.malitsplus.shizurunotes.ui.calendar

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.malitsplus.shizurunotes.common.App
import com.github.malitsplus.shizurunotes.databinding.FragmentDayBinding
import com.github.malitsplus.shizurunotes.user.UserSettings
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DayFragment : Fragment() {

    private lateinit var calendarVM: CalendarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calendarVM = ViewModelProvider(requireActivity())[CalendarViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentDayBinding.inflate(inflater, container, false).apply {
            dayToolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
            calendarVM.calendarMap[calendarVM.selectedDay]?.let {
                with(Calendar.getInstance()) {
                    set(it.year, it.month - 1, it.day)
                    val locale = Locale(UserSettings.get().getLanguage())
                    val format = DateFormat.getBestDateTimePattern(locale, "d MMM")
                    dayToolbar.title = SimpleDateFormat(format, locale).format(time)
                }
            }

            dayRecycler.apply {
                layoutManager = LinearLayoutManager(this@DayFragment.context)
                val map = calendarVM.scheduleMap[calendarVM.selectedDay]
                if (map != null) {
                    adapter = DayScheduleAdapter().apply {
                        itemList = map
                    }
                }
            }
        }
        return binding.root
    }
}