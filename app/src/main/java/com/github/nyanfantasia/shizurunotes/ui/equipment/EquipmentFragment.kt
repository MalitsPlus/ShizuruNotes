package com.github.nyanfantasia.shizurunotes.ui.equipment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.data.Equipment
import com.github.nyanfantasia.shizurunotes.data.Item
import com.github.nyanfantasia.shizurunotes.databinding.FragmentEquipmentBinding
import com.github.nyanfantasia.shizurunotes.databinding.ItemEquipmentLevelBinding
import com.github.nyanfantasia.shizurunotes.ui.base.ViewType
import com.github.nyanfantasia.shizurunotes.ui.base.ViewTypeAdapter
import com.github.nyanfantasia.shizurunotes.ui.base.ViewTypeHolder
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelEquipment
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelEquipmentFactory
import com.google.android.material.slider.Slider

class EquipmentFragment : Fragment(), OnEquipmentActionListener<Equipment> {

    lateinit var binding: FragmentEquipmentBinding
    lateinit var sharedEquipment: SharedViewModelEquipment
    lateinit var equipmentVM: EquipmentViewModel

    private val maxSpan = 4
    private val equipmentAdapter by lazy { ViewTypeAdapter<ViewType<*>>(onItemActionListener = this) }

    override fun onResume() {
        super.onResume()
        sharedEquipment.selectedDrops.value?.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedEquipment = ViewModelProvider(requireActivity())[SharedViewModelEquipment::class.java]
        equipmentVM = ViewModelProvider(this, SharedViewModelEquipmentFactory(sharedEquipment))[EquipmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
                // disable the default animation
                itemAnimator = null

                equipmentAdapter.setList(equipmentVM.viewList)
                adapter = equipmentAdapter
                layoutManager = GridLayoutManager(requireContext(), maxSpan).apply {
                    spanSizeLookup = spanSize
                }
            }
            this
        }
        equipmentVM.selectedLevel.observe(viewLifecycleOwner, selectedLevelObserver)

    }

    private val selectedLevelObserver = Observer<Int> { level ->
        for (i in 0 until equipmentAdapter.itemCount) {
            binding.equipmentRecycler.findViewHolderForAdapterPosition(i)?.let {
                when (it) {
                    is ViewTypeHolder.EquipmentLevelViewHolder -> {
                        (it.binding as ItemEquipmentLevelBinding).selectedLevelInteger.text = level.toString()
                    }
                }
            }
        }
        equipmentAdapter.updateElements(equipmentVM.getPropertyViewType(level), 1)
    }

    private val spanSize = object: GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when (equipmentAdapter.getItemViewType(position)) {
                R.layout.item_property -> maxSpan / 2
                R.layout.item_equipment_craft_num -> maxSpan / 4
                else -> maxSpan
            }
        }
    }

    override val onSliderActionListener: Slider.OnChangeListener
        get() = equipmentVM.onSliderChangeListener

    override fun onItemClickedListener(item: Item) {
        if (item.itemId in 101000..139999) {
            sharedEquipment.setDrop(item)
            findNavController().navigate(EquipmentFragmentDirections.actionNavEquipmentToNavDropQuest())
        }
    }

    override fun onItemClicked(position: Int) {
    }
}
