package com.github.malitsplus.shizurunotes.ui.equipment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.FragmentEquipmentBinding
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelEquipment

class EquipmentFragment : Fragment() {

    lateinit var binding: FragmentEquipmentBinding
    lateinit var sharedEquipment: SharedViewModelEquipment
    lateinit var equipmentVM: EquipmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedEquipment = ViewModelProvider(requireActivity())[SharedViewModelEquipment::class.java]
        equipmentVM = ViewModelProvider(this)[EquipmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEquipmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}
