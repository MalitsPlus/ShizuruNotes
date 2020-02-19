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

class CharaDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var detailsViewModel: CharaDetailsViewModel
    private lateinit var binding: FragmentCharaDetailsBinding
    private val args: CharaDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater
            .from(context)
            .inflateTransition(android.R.transition.move).setDuration(300)
        sharedElementReturnTransition = TransitionInflater
            .from(context)
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
            SharedViewModelCharaFactory(
                sharedViewModel
            )
        ).get(CharaDetailsViewModel::class.java
        )

        binding.run {
            detailsViewModel = detailsViewModel
            detailsItemChara.transitionName = "transItem_${args.charaId}"
            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }

        binding.clickListener = this

        //角色技能顺序
        val recyclerView = binding.attackPatternRecycler
        val layoutManager = GridLayoutManager(context, 6)
        val adapter = AttackPatternAdapter(context!!)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = false

        //技能
        val recyclerViewSkill = binding.skillRecycler
        val layoutManagerSkill = LinearLayoutManager(context)
        val adapterSkill = SkillAdapter(context!!)
        recyclerViewSkill.layoutManager = layoutManagerSkill
        recyclerViewSkill.adapter = adapterSkill
        recyclerViewSkill.isNestedScrollingEnabled = false


        detailsViewModel.mutableChara.observe(viewLifecycleOwner, Observer<Chara>{ chara: Chara ->
                binding.detailsViewModel = detailsViewModel
                adapter.update(chara.attackPatternList[0].items)
                adapterSkill.update(chara.skills)
                if (chara.attackPatternList.size == 1){
                    binding.textAnotherMode.visibility = View.GONE
                    binding.attackPatternRecycler2.visibility = View.GONE
                } else{
                    val recyclerView2 = binding.attackPatternRecycler2
                    val layoutManager2 = GridLayoutManager(context, 6)
                    val adapter2 = AttackPatternAdapter(context!!)
                    recyclerView2.layoutManager = layoutManager2
                    recyclerView2.adapter = adapter2
                    recyclerView2.isNestedScrollingEnabled = false
                    adapter2.update(chara.attackPatternList[1].items)
                }
            }
        )

        return binding.root
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.detailsItemCharaContainer) {
            val action =
                CharaDetailsFragmentDirections.actionNavCharaDetailsToNavCharaProfile()
            Navigation.findNavController(v).navigate(action)
        }
    }

}