package com.github.malitsplus.shizurunotes.ui.charalist

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.ui.base.MaterialSpinnerAdapter
import com.github.malitsplus.shizurunotes.data.Chara
import com.github.malitsplus.shizurunotes.databinding.FragmentCharaListBinding
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelCharaFactory
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelEquipment
import com.github.malitsplus.shizurunotes.utils.LogUtils
import com.google.android.material.snackbar.Snackbar


class CharaListFragment : Fragment() {

    private lateinit var charaListViewModel: CharaListViewModel
    private lateinit var sharedChara: SharedViewModelChara
    private lateinit var sharedEquipment: SharedViewModelEquipment
    private lateinit var adapter: CharaListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedChara = ViewModelProvider(requireActivity())[SharedViewModelChara::class.java]
        sharedEquipment = ViewModelProvider(requireActivity())[SharedViewModelEquipment::class.java]
        charaListViewModel = ViewModelProvider(this, SharedViewModelCharaFactory(sharedChara))[CharaListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentCharaListBinding>(inflater, R.layout.fragment_chara_list, container, false).apply {
            viewModel = charaListViewModel
            lifecycleOwner = viewLifecycleOwner

            adapter = CharaListAdapter(sharedChara)

            charaListRecycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@CharaListFragment.adapter
                setHasFixedSize(true)
                parentFragment?.postponeEnterTransition()
                viewTreeObserver.addOnPreDrawListener {
                    parentFragment?.startPostponedEnterTransition()
                    true
                }
            }
        }
        setDropdownText(binding)
        setObserver(binding)
        return binding.root
    }

    private fun setObserver(binding: FragmentCharaListBinding) {
        charaListViewModel.liveCharaList.observe(viewLifecycleOwner,
            Observer<List<Chara>> {
                binding.downloadDbHint.visibility = if (it.isEmpty() && sharedChara.loadingFlag.value == false){
                    View.VISIBLE
                } else {
                    View.GONE
                }
                adapter.update(it)
            }
        )

        sharedChara.loadingFlag.observe(viewLifecycleOwner,
            Observer {
                binding.charaListProgressBar.visibility = if (it) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        )

        sharedEquipment.loadingFlag.observe(viewLifecycleOwner,
            Observer {
                if (it) {
                    binding.charaListProgressBar.visibility = View.VISIBLE
                }
            }
        )

        sharedChara.charaList.observe(viewLifecycleOwner,
            Observer {
                updateList()
            }
        )
    }

    private fun setDropdownText(binding: FragmentCharaListBinding){
        binding.apply {
            dropdownText1.apply {
                onItemClickListener = AdapterView.OnItemClickListener { _, _, position: Int, _ ->
                    charaListViewModel.filter(position.toString(), null, null, null)
                }
                setAdapter(
                    MaterialSpinnerAdapter(
                        this@CharaListFragment.requireContext(),
                        R.layout.dropdown_item_chara_list,
                        charaListViewModel.attackTypeMap.values.toTypedArray<String>()
                    )
                )
            }

            dropdownText2.apply {
                onItemClickListener = AdapterView.OnItemClickListener { _, _, position: Int, _ ->
                    charaListViewModel.filter(null, position.toString(), null, null)
                }
                setAdapter(
                    MaterialSpinnerAdapter(
                        this@CharaListFragment.requireContext(),
                        R.layout.dropdown_item_chara_list,
                        charaListViewModel.positionMap.values.toTypedArray<String>()
                    )
                )
            }

            dropdownText3.apply {
                onItemClickListener = AdapterView.OnItemClickListener { _, _, position: Int, _ ->
                    if (position == 9)
                        Snackbar.make(
                            root,
                            I18N.getString(R.string.snack_bar_cant_sort),
                            Snackbar.LENGTH_LONG
                        ).show()
                    else
                        charaListViewModel.filter(null, null, position.toString(), null)
                }
                setAdapter(
                    MaterialSpinnerAdapter(
                        this@CharaListFragment.requireContext(),
                        R.layout.dropdown_item_chara_list,
                        charaListViewModel.sortMap.values.toTypedArray<String>()
                    )
                )
            }
        }
    }

    private fun updateList() {
        charaListViewModel.filterDefault()
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.fragment_chara_bar, menu)
//    }
}