package com.github.malitsplus.shizurunotes.ui.comparison

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.FragmentComparisonListBinding
import com.github.malitsplus.shizurunotes.ui.base.MaterialSpinnerAdapter
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelCharaFactory

class ComparisonListFragment : Fragment() {

    private lateinit var binding: FragmentComparisonListBinding
    private lateinit var sharedChara: SharedViewModelChara
    private lateinit var comparisonListVM: ComparisonListViewModel
    private lateinit var adapter: ComparisonListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedChara = ViewModelProvider(requireActivity())[SharedViewModelChara::class.java]
        comparisonListVM = ViewModelProvider(this, SharedViewModelCharaFactory(sharedChara))[ComparisonListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComparisonListBinding.inflate(inflater, container, false)
        comparisonListVM.liveComparisonList.observe(viewLifecycleOwner, Observer {
            binding.comparisonListProgressbar.visibility = if (it.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
            adapter.update(it.toMutableList())
        })
        setDropdownText()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ComparisonListAdapter()
        binding.apply {
            comparisonListToolbar.setNavigationOnClickListener {
                it.findNavController().navigateUp()
            }
            comparisonListRecycler.apply {
                layoutManager = LinearLayoutManager(this@ComparisonListFragment.context)
                adapter = this@ComparisonListFragment.adapter
            }
        }
        comparisonListVM.filterDefault()
    }

    private fun setDropdownText(){
        binding.apply {
            dropdownText1.apply {
                onItemClickListener = AdapterView.OnItemClickListener { _, _, position: Int, _ ->
                    comparisonListVM.filter(position.toString(), null, null, null)
                }
                setAdapter(
                    MaterialSpinnerAdapter(
                        this@ComparisonListFragment.requireContext(),
                        R.layout.dropdown_item_chara_list,
                        comparisonListVM.attackTypeMap.values.toTypedArray<String>()
                    )
                )
                setText(comparisonListVM.attackTypeMap[0].toString())
            }

            dropdownText2.apply {
                onItemClickListener = AdapterView.OnItemClickListener { _, _, position: Int, _ ->
                    comparisonListVM.filter(null, position.toString(), null, null)
                }
                setAdapter(
                    MaterialSpinnerAdapter(
                        this@ComparisonListFragment.requireContext(),
                        R.layout.dropdown_item_chara_list,
                        comparisonListVM.positionMap.values.toTypedArray<String>()
                    )
                )
                setText(comparisonListVM.positionMap[0].toString())
            }

            dropdownText3.apply {
                onItemClickListener = AdapterView.OnItemClickListener { _, _, position: Int, _ ->
                    comparisonListVM.filter(null, null, position.toString(), null)
                }
                setAdapter(
                    MaterialSpinnerAdapter(
                        this@ComparisonListFragment.requireContext(),
                        R.layout.dropdown_item_chara_list,
                        comparisonListVM.sortMap.values.toTypedArray<String>()
                    )
                )
                setText(comparisonListVM.sortMap[0].toString())
            }
        }
    }

}
