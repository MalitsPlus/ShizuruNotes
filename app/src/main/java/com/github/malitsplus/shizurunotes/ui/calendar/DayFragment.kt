package com.github.malitsplus.shizurunotes.ui.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
            dayRecycler.apply {
                layoutManager = LinearLayoutManager(this@DayFragment.context)
                adapter = DayScheduleAdapter().apply { itemList = calendarVM.scheduleMap[calendarVM.selectedDay]!! }
            }
        }
        return binding.root
    }
}