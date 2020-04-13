package com.github.malitsplus.shizurunotes.ui.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.databinding.FragmentDayBinding

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
            val calendar = calendarVM.calendarMap[calendarVM.selectedDay]
            dayToolbar.title = calendar?.month.toString() + I18N.getString(R.string.text_month) + calendar?.day + I18N.getString(R.string.text_day)
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