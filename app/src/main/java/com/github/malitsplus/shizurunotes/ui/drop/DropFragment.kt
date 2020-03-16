package com.github.malitsplus.shizurunotes.ui.drop

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.JsonUtils
import com.github.malitsplus.shizurunotes.common.UserData
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.databinding.FragmentDropBinding
import com.github.malitsplus.shizurunotes.ui.ViewPagerFragmentDirections
import com.github.malitsplus.shizurunotes.ui.base.BaseHintAdapter
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelEquipment
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelEquipmentFactory
import kotlinx.android.synthetic.main.fragment_drop.*

class DropFragment : Fragment() {

    private lateinit var sharedEquipment: SharedViewModelEquipment
    private lateinit var dropVM: DropViewModel
    private lateinit var binding: FragmentDropBinding
    private lateinit var mAdapter: GridSelectAdapter
    private val maxSpanNum = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedEquipment = ViewModelProvider(activity!!)[SharedViewModelEquipment::class.java]
        dropVM = ViewModelProvider(this)[DropViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mAdapter = GridSelectAdapter(activity!!.applicationContext, sharedEquipment)
        val mLayoutManager = GridLayoutManager(context, maxSpanNum).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when(mAdapter.getItemViewType(position)) {
                        BaseHintAdapter.HINT_TEXT -> maxSpanNum
                        else -> 1
                    }
                }
            }
        }

        binding = FragmentDropBinding.inflate(
            inflater, container, false
        ).apply {
            setOptionItemClickListener(dropToolbar)
            dropRecycler.apply {
                layoutManager = mLayoutManager
                adapter = mAdapter
                setHasFixedSize(true)
            }
        }

        setFloatingBarClickListener()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        sharedEquipment.equipmentMap.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                dropVM.refreshList(it.values.toList())
                mAdapter.update(dropVM.itemList)
            }
        })
        sharedEquipment.loadingFlag.observe(viewLifecycleOwner, Observer {
            binding.dropProgressBar.visibility = if (it) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })
    }

    private fun setFloatingBarClickListener() {
        binding.clickListener = View.OnClickListener { view ->
            if (view.id == R.id.drop_floating_button) {
                val idList = mutableListOf<Int>()
                sharedEquipment.selectedDrops.value?.forEach {
                    idList.add(it.equipmentId)
                }
                UserData.setLastEquipmentIds(idList)
                view.findNavController().navigate(ViewPagerFragmentDirections.actionNavViewPagerToNavDropQuest())
            }
        }
    }

    private fun setOptionItemClickListener(toolbar: Toolbar) {
        toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_drop_before -> {

                    true
                }
                R.id.menu_drop_cancel -> {
                    sharedEquipment.selectedDrops.value?.forEach { equipment ->
                        mAdapter.itemList.forEach { item ->
                            if (item == equipment) {
                                binding.dropRecycler.getChildAt(mAdapter.itemList.indexOf(item)).background = context!!.getDrawable(R.drawable.color_unselected_background)
                            }
                        }
                    }
                    sharedEquipment.selectedDrops.value?.clear()
                    true
                }
                else -> true
            }
        }
    }
}