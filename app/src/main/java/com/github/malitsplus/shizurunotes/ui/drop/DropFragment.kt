package com.github.malitsplus.shizurunotes.ui.drop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.databinding.FragmentDropBinding
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelEquipment
import kotlinx.android.synthetic.main.fragment_drop.*

class DropFragment : Fragment() {

    private lateinit var sharedEquipment: SharedViewModelEquipment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedEquipment = ViewModelProvider(activity!!)[SharedViewModelEquipment::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDropBinding.inflate(
            inflater, container, false
        ).apply {
            val mLayoutManager = GridLayoutManager(context, 6)
            val mAdapter = GridSelectAdapter(sharedEquipment.equipmentMap.values.toList())
            dropRecycler.apply {
                layoutManager = mLayoutManager
                adapter = mAdapter
                setHasFixedSize(true)
            }
        }
        return binding.root
    }
}