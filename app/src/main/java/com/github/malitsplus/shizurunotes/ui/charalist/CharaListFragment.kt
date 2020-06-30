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
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Chara
import com.github.malitsplus.shizurunotes.databinding.FragmentCharaListBinding
import com.github.malitsplus.shizurunotes.databinding.ItemCharaBinding
import com.github.malitsplus.shizurunotes.ui.BottomNaviFragmentDirections
import com.github.malitsplus.shizurunotes.ui.base.*
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelCharaFactory
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelEquipment
import org.w3c.dom.Text


class CharaListFragment : Fragment(), OnCharaActionListener
{
    private lateinit var binding: FragmentCharaListBinding
    private lateinit var charaListVM: CharaListViewModel
    private lateinit var sharedChara: SharedViewModelChara
    private lateinit var sharedEquipment: SharedViewModelEquipment
    private val charaListAdapter by lazy { ViewTypeAdapter<ViewType<*>>(onItemActionListener = this) }

    override fun onResume() {
        super.onResume()
        binding.apply {
            dropdownText1.dismissDropDown()
            dropdownText2.dismissDropDown()
            dropdownText3.dismissDropDown()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedChara = ViewModelProvider(requireActivity())[SharedViewModelChara::class.java]
        sharedEquipment = ViewModelProvider(requireActivity())[SharedViewModelEquipment::class.java]
        charaListVM = ViewModelProvider(this, SharedViewModelCharaFactory(sharedChara))[CharaListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharaListBinding.inflate(inflater, container, false)
        setDropdownText()
        setObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with (binding) {
            charaSearchBar.addTextChangeListener(charaListVM.textWatcher)
            charaListAdapter.setList(charaListVM.getViewList())
            charaListRecycler.apply {
                adapter = charaListAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)

                parentFragment?.postponeEnterTransition()
                viewTreeObserver.addOnPreDrawListener {
                    parentFragment?.startPostponedEnterTransition()
                    true
                }
            }
        }
    }

    private fun setHintTextVisibility(downloadDb: Boolean = false, noResult: Boolean = false) {
        binding.downloadDbHint.visibility = if (downloadDb) View.VISIBLE else View.GONE
        binding.textSearchNoResult.visibility = if (noResult) View.VISIBLE else View.GONE
    }

    private fun setObserver() {
        charaListVM.liveCharaList.observe(viewLifecycleOwner, Observer {
            if (sharedChara.charaList.value.isNullOrEmpty() && sharedChara.loadingFlag.value == false && sharedEquipment.loadingFlag.value == false) {
                setHintTextVisibility(downloadDb = true)
            } else if (sharedChara.charaList.value?.isNotEmpty() == true && it.isEmpty()) {
                setHintTextVisibility(noResult = true)
            } else {
                setHintTextVisibility()
            }
            charaListAdapter.setUpdatedList(charaListVM.getViewList(it))
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
                onItemClickListener = charaListVM.getSpinnerClickListener(1)
                setAdapter(getSpinnerAdapter(1))
            }
            dropdownText2.apply {
                onItemClickListener = charaListVM.getSpinnerClickListener(2)
                setAdapter(getSpinnerAdapter(2))
            }
            dropdownText3.apply {
                onItemClickListener = charaListVM.getSpinnerClickListener(3)
                setAdapter(getSpinnerAdapter(3))
            }
        }
    }

    private fun updateList() {
        charaListVM.filterDefault()
    }

    private fun getSpinnerAdapter(type: Int): MaterialSpinnerAdapter<String> {
        return MaterialSpinnerAdapter(
            requireContext(),
            R.layout.dropdown_item_chara_list,
            charaListVM.dropDownValuesMap[type] ?: arrayOf()
        )
    }

    override fun onCharaClickedListener(chara: Chara, position: Int) {
        sharedChara.mSetSelectedChara(chara)
        sharedChara.backFlag = false
        val viewHolder = binding.charaListRecycler.findViewHolderForAdapterPosition(position) as ViewTypeHolder.GeneralViewHolder
        val extras = FragmentNavigatorExtras(
            (viewHolder.binding as ItemCharaBinding).itemChara to "transItem_${chara.charaId}"
        )
        val action = BottomNaviFragmentDirections.actionNavBottomNavigationToNavCharaDetails().setCharaId(chara.charaId)
        findNavController().navigate(action, extras)
    }

    override fun onItemClicked(position: Int) {
    }
}