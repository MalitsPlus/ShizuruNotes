package com.github.malitsplus.shizurunotes.ui.charalist

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.FragmentCharaListBinding
import com.github.malitsplus.shizurunotes.ui.base.MaterialSpinnerAdapter
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelCharaFactory
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelEquipment
import org.w3c.dom.Text


class CharaListFragment : Fragment()
{
    private lateinit var binding: FragmentCharaListBinding
    private lateinit var charaListViewModel: CharaListViewModel
    private lateinit var sharedChara: SharedViewModelChara
    private lateinit var sharedEquipment: SharedViewModelEquipment
    private lateinit var adapter: CharaListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        (requireActivity() as MainActivity).callBack = this
    }

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

        binding = FragmentCharaListBinding.inflate(inflater, container, false).apply {
            viewModel = charaListViewModel
            lifecycleOwner = viewLifecycleOwner

            charaSearchBar.addTextChangeListener(charaListViewModel.textWatcher)

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
        setDropdownText()
        setObserver()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            dropdownText1.dismissDropDown()
            dropdownText2.dismissDropDown()
            dropdownText3.dismissDropDown()
        }
    }

    private fun setObserver() {
        charaListViewModel.liveCharaList.observe(viewLifecycleOwner, Observer {
            binding.downloadDbHint.visibility = if (it.isEmpty() && sharedChara.loadingFlag.value == false && sharedEquipment.loadingFlag.value == false){
                View.VISIBLE
            } else {
                View.GONE
            }
            adapter.update(it)
        })

        sharedChara.loadingFlag.observe(viewLifecycleOwner, Observer {
            binding.charaListProgressBar.visibility = if (it) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })

        sharedEquipment.loadingFlag.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.charaListProgressBar.visibility = View.VISIBLE
            }
        })

        sharedChara.charaList.observe(viewLifecycleOwner, Observer {
            updateList()
        })
    }

    private fun setDropdownText(){
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
}