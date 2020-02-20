package com.github.malitsplus.shizurunotes.ui.charalist

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.common.MaterialSpinnerAdapter
import com.github.malitsplus.shizurunotes.data.Chara
import com.github.malitsplus.shizurunotes.databinding.FragmentCharaListBinding
import com.github.malitsplus.shizurunotes.ui.SharedViewModelChara
import com.github.malitsplus.shizurunotes.ui.SharedViewModelCharaFactory
import com.google.android.material.snackbar.Snackbar
import kotlin.concurrent.thread


class CharaListFragment : Fragment() {

    private lateinit var charaListViewModel: CharaListViewModel
    private lateinit var adapter: CharaListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedViewModel = ViewModelProvider(activity!!).get(SharedViewModelChara::class.java)

        charaListViewModel = ViewModelProvider(
            this,
            SharedViewModelCharaFactory(
                sharedViewModel
            )
        ).get(CharaListViewModel::class.java)

        val binding =
            DataBindingUtil.inflate<FragmentCharaListBinding>(
                inflater, R.layout.fragment_chara_list, container, false
            ).apply{
                viewModel = charaListViewModel
                lifecycleOwner = viewLifecycleOwner

                adapter = CharaListAdapter(context!!, sharedViewModel)
                charaListRecycler.let {
                    it.layoutManager = LinearLayoutManager(context)
                    it.adapter = adapter
                    it
                }.apply {
                    setHasFixedSize(true)
                    parentFragment?.postponeEnterTransition()
                    viewTreeObserver.addOnPreDrawListener {
                        parentFragment?.startPostponedEnterTransition()
                        true
                    }
                }

                dropdownText1.apply {
                    onItemClickListener = AdapterView.OnItemClickListener {
                            _, _, position: Int, _ ->
                        charaListViewModel.filter(position.toString(), null, null, null)
                    }
                }

                dropdownText2.apply {
                    onItemClickListener = AdapterView.OnItemClickListener {
                            _, _, position: Int, _ ->
                        charaListViewModel.filter(null, position.toString(), null, null)
                    }
                }


            }

        binding.dropdownText3.apply {
            onItemClickListener = AdapterView.OnItemClickListener {
                    _, _, position: Int, _ ->
                if (position == 9)
                    Snackbar.make(binding.root, I18N.getString(R.string.snack_bar_cant_sort), Snackbar.LENGTH_LONG).show()
                else
                    charaListViewModel.filter(null, null, position.toString(), null)
            }
        }

        setDropdownText(binding)

        //设置观察者
        charaListViewModel.liveCharaList.observe(viewLifecycleOwner,
            Observer<List<Chara>> {
                binding.downloadDbHint.visibility = if (it.isEmpty() && sharedViewModel.loadingFlag.value == false){
                    View.VISIBLE
                } else {
                    View.GONE
                }
                adapter.update(it)
            }
        )

        sharedViewModel.loadingFlag.observe(viewLifecycleOwner,
            Observer {
                if (it) {
                    binding.charaListProgressBar.visibility = View.VISIBLE
                } else {
                    binding.charaListProgressBar.visibility = View.GONE
                }
            }
        )

        sharedViewModel.charaList.observe(viewLifecycleOwner,
            Observer {
                updateList()
            }
        )

        return binding.root
    }


    private fun setDropdownText(binding: FragmentCharaListBinding){
        binding.dropdownText1.setAdapter(
            MaterialSpinnerAdapter(
                context!!,
                R.layout.dropdown_item_chara_list,
                charaListViewModel.attackTypeMap.values.toTypedArray<String>()
            ))

        binding.dropdownText2.setAdapter(MaterialSpinnerAdapter(
            context!!,
            R.layout.dropdown_item_chara_list,
            charaListViewModel.positionMap.values.toTypedArray<String>()
        ))

        binding.dropdownText3.setAdapter(MaterialSpinnerAdapter(
            context!!,
            R.layout.dropdown_item_chara_list,
            charaListViewModel.sortMap.values.toTypedArray<String>()
        ))
    }

    /*
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_chara_bar, menu)
    }
*/
    private fun updateList() {
        charaListViewModel.filterDefault()
    }
}