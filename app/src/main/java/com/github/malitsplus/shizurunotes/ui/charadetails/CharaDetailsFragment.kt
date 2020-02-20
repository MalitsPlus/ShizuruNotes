package com.github.malitsplus.shizurunotes.ui.charadetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.github.malitsplus.shizurunotes.ui.SharedViewModelChara
import com.github.malitsplus.shizurunotes.ui.SharedViewModelCharaFactory
import com.jaredrummler.materialspinner.MaterialSpinner

class CharaDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var detailsViewModel: CharaDetailsViewModel
    private lateinit var binding: FragmentCharaDetailsBinding
    private val args: CharaDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context)
                .inflateTransition(android.R.transition.move).setDuration(300)

        sharedElementReturnTransition =
            TransitionInflater.from(context)
                .inflateTransition(android.R.transition.move).setDuration(300)
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

        val sharedViewModel = ViewModelProvider(activity!!).get(SharedViewModelChara::class.java)
        detailsViewModel = ViewModelProvider(
            this,
            SharedViewModelCharaFactory(sharedViewModel)).get(CharaDetailsViewModel::class.java
        )

        binding.apply {
            detailsItemChara.transitionName = "transItem_${args.charaId}"
            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }

            val rankList: List<Int>? = detailsViewModel.getChara()!!.promotionStatus.keys.toList()
            rankSpinner.apply {
                setItems(rankList?: "No Items")
                setOnItemSelectedListener(
                    MaterialSpinner.OnItemSelectedListener { _, _, _, item: String ->
                        detailsViewModel.changeRank(item)
                    }
                )
            }
        }.also {
            it.clickListener = this
        }

        //角色技能顺序 Recycler
        val attackPatternLayoutManager = GridLayoutManager(context, 6)
        val attackPatternAdapter = AttackPatternAdapter(context!!)
        binding.attackPatternRecycler.apply {
            layoutManager = attackPatternLayoutManager
            adapter = attackPatternAdapter.apply {
                update(detailsViewModel.getChara()!!.attackPatternList[0].items)
            }
            isNestedScrollingEnabled = false
        }

        //2套攻击顺序 Recycler
        if (detailsViewModel.getChara()!!.attackPatternList.size == 1){
            binding.textAnotherMode.visibility = View.GONE
            binding.attackPatternRecycler2.visibility = View.GONE
        } else {
            val layoutManager2 = GridLayoutManager(context, 6)
            val adapter2 = AttackPatternAdapter(context!!)
            binding.attackPatternRecycler2.apply {
                layoutManager = layoutManager2
                isNestedScrollingEnabled = false
                adapter = adapter2.apply {
                    update(detailsViewModel.getChara()!!.attackPatternList[1].items)
                }
            }
        }

        //技能 Recycler
        val layoutManagerSkill = LinearLayoutManager(context)
        val adapterSkill = SkillAdapter(context!!)
        binding.skillRecycler.apply {
            layoutManager = layoutManagerSkill
            adapter = adapterSkill
            isNestedScrollingEnabled = false
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