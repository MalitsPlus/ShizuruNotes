package com.github.malitsplus.shizurunotes.ui.charadetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Chara
import com.github.malitsplus.shizurunotes.databinding.FragmentCharaDetailsBinding
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelCharaFactory
import com.github.malitsplus.shizurunotes.ui.base.AttackPatternContainerAdapter
import com.github.malitsplus.shizurunotes.ui.base.BaseHintAdapter
import com.github.malitsplus.shizurunotes.ui.base.MaterialSpinnerAdapter

class CharaDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var detailsViewModel: CharaDetailsViewModel
    private lateinit var sharedChara: SharedViewModelChara
    private lateinit var binding: FragmentCharaDetailsBinding
    private val args: CharaDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedChara = ViewModelProvider(requireActivity()).get(SharedViewModelChara::class.java)

        sharedElementEnterTransition =
            TransitionInflater.from(context)
                .inflateTransition(android.R.transition.move).setDuration(300)

        sharedElementReturnTransition =
            TransitionInflater.from(context)
                .inflateTransition(android.R.transition.move).setDuration(300)
    }

    override fun onResume() {
        super.onResume()
        binding.rankSpinner.dismissDropDown()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_chara_details,
            container,
            false
        )

        detailsViewModel = ViewModelProvider(
            this,
            SharedViewModelCharaFactory(sharedChara)
        ).get(CharaDetailsViewModel::class.java)

        binding.apply {
            detailsItemChara.transitionName = "transItem_${args.charaId}"
            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }

            val rankList = mutableListOf<Int>()
            detailsViewModel.getChara()?.let {
                for (i in it.maxCharaRank downTo 2) {
                    rankList.add(i)
                }
            }
            rankSpinner.apply {
                onItemClickListener = AdapterView.OnItemClickListener { _, _, position: Int, _ ->
                    detailsViewModel.changeRank(adapter.getItem(position).toString())
                }
                setAdapter(
                    MaterialSpinnerAdapter(
                        this@CharaDetailsFragment.requireContext(),
                        R.layout.dropdown_item_chara_list,
                        rankList.toTypedArray()
                    )
                )
                setText(rankList[0].toString())
            }

            if (sharedChara.backFlag)
                appbar.setExpanded(false, false)


        }.also {
            it.clickListener = this
        }

        //攻击顺序
        val adapterAttackPattern = AttackPatternContainerAdapter(context).apply {
            initializeItems(detailsViewModel.mutableChara.value?.attackPatternList)
        }
        binding.attackPatternRecycler.apply {
            layoutManager = GridLayoutManager(context, 6).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when(adapterAttackPattern.getItemViewType(position)) {
                            BaseHintAdapter.HINT_TEXT -> 6
                            else -> 1
                        }
                    }
                }
            }
            adapter = adapterAttackPattern
        }

        //技能 Recycler
        val layoutManagerSkill = LinearLayoutManager(context)
        val adapterSkill = SkillAdapter(sharedChara)
        binding.skillRecycler.apply {
            layoutManager = layoutManagerSkill
            adapter = adapterSkill
        }

        //观察chara变化
        detailsViewModel.mutableChara.observe(
            viewLifecycleOwner,
            Observer<Chara> { chara: Chara ->
                binding.detailsVM = detailsViewModel
                adapterSkill.update(chara.skills)
            }
        )

        return binding.run {
            detailsVM = detailsViewModel
            root
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.detailsItemCharaContainer) {
            val action =
                CharaDetailsFragmentDirections.actionNavCharaDetailsToNavCharaProfile()
            Navigation.findNavController(v).navigate(action)
        }
    }

}