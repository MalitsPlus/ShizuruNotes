package com.github.malitsplus.shizurunotes.ui.equipment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.databinding.FragmentEquipmentBinding
import com.github.malitsplus.shizurunotes.ui.base.ViewType
import com.github.malitsplus.shizurunotes.ui.base.ViewTypeAdapter
import com.github.malitsplus.shizurunotes.ui.charaprofile.OnEquipmentClickListener
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelEquipment

class EquipmentFragment : Fragment(), OnEquipmentClickListener<Equipment> {

    lateinit var binding: FragmentEquipmentBinding
    lateinit var sharedEquipment: SharedViewModelEquipment
    lateinit var equipmentVM: EquipmentViewModel

    private val maxSpan = 6
    private val equipmentAdapter by lazy { ViewTypeAdapter<ViewType<*>>(onItemActionListener = this) }

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
        with(binding) {
            equipmentToolbar.apply {
                title = sharedEquipment.selectedEquipment?.itemName
                setNavigationOnClickListener { view ->
                    view.findNavController().navigateUp()
                }
            }
            equipmentRecycler.apply {

            }
        }
    }

    override fun onEquipmentClicked(item: Equipment) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClicked(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
