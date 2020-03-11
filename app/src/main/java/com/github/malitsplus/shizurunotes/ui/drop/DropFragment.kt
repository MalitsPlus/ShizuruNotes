package com.github.malitsplus.shizurunotes.ui.drop

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.databinding.FragmentDropBinding
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelEquipment
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelEquipmentFactory
import kotlinx.android.synthetic.main.fragment_drop.*

class DropFragment : Fragment() {

    private lateinit var sharedEquipment: SharedViewModelEquipment
    private lateinit var dropVM: DropViewModel
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

        val mAdapter = GridSelectAdapter(activity!!.applicationContext)
        val mLayoutManager = GridLayoutManager(context, maxSpanNum).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when(mAdapter.getItemViewType(position)) {
                        GridSelectAdapter.GRID_TEXT -> maxSpanNum
                        GridSelectAdapter.GRID_EQUIPMENT -> 1
                        else -> 1
                    }
                }
            }
        }

        val binding = FragmentDropBinding.inflate(
            inflater, container, false
        ).apply {
            dropRecycler.apply {
                layoutManager = mLayoutManager
                adapter = mAdapter
                setHasFixedSize(true)
            }
            clickListener = View.OnClickListener {
                if (it.id == R.id.drop_floating_button) {
                    mAdapter
                }
            }
        }

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

        return binding.root
    }
}