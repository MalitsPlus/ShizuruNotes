package com.github.malitsplus.shizurunotes.ui.charadetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.AdapterViewBindingAdapter
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
import com.github.malitsplus.shizurunotes.ui.basic.AttackPatternContainerAdapter
import kotlinx.android.synthetic.main.fragment_chara_details.view.*
import org.angmarch.views.OnSpinnerItemSelectedListener

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

            val rankList: List<Int> = detailsViewModel.getChara()!!.promotionStatus.keys.toList()
            rankSpinner.apply {
                attachDataSource(rankList)
                onSpinnerItemSelectedListener = OnSpinnerItemSelectedListener { parent, _, position, _ ->
                    detailsViewModel.changeRank(parent.getItemAtPosition(position).toString())
                }
            }

            if (sharedViewModel.backFlag)
                appbar.setExpanded(false, false)


        }.also {
            it.clickListener = this
        }

        //攻击顺序
        binding.attackPatternRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AttackPatternContainerAdapter().apply { itemList = detailsViewModel.getChara()!!.attackPatternList }
        }

        //技能 Recycler
        val layoutManagerSkill = LinearLayoutManager(context)
        val adapterSkill = SkillAdapter(sharedViewModel)
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