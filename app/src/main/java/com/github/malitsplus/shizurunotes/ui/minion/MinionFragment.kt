package com.github.malitsplus.shizurunotes.ui.minion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Skill
import com.github.malitsplus.shizurunotes.databinding.FragmentMinionBinding
import com.github.malitsplus.shizurunotes.ui.base.ViewType
import com.github.malitsplus.shizurunotes.ui.base.ViewTypeAdapter
import com.github.malitsplus.shizurunotes.ui.enemy.EnemyFragmentDirections
import com.github.malitsplus.shizurunotes.ui.enemy.OnEnemyActionListener
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelCharaFactory
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelClanBattle

class MinionFragment : Fragment(), OnEnemyActionListener {

    private val args: MinionFragmentArgs by navArgs()
    private lateinit var binding: FragmentMinionBinding
    private lateinit var sharedChara: SharedViewModelChara
    private lateinit var sharedClanBattle: SharedViewModelClanBattle
    private lateinit var minionVM: MinionViewModel
    private val maxSpan = 6
    private val minionAdapter by lazy { ViewTypeAdapter<ViewType<*>>(onItemActionListener = this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedChara = ViewModelProvider(requireActivity())[SharedViewModelChara::class.java]
        sharedClanBattle = ViewModelProvider(requireActivity())[SharedViewModelClanBattle::class.java]
        minionVM = ViewModelProvider(this, SharedViewModelCharaFactory(sharedChara))[MinionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMinionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarMinion.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }

        when(args.minionType) {
            1 -> {
                minionVM.minionList = sharedChara.selectedMinion
                sharedChara.backFlag = true
            }
            2 -> {
                minionVM.minionList = sharedClanBattle.selectedMinion
            }
        }

        minionAdapter.setList(minionVM.viewList)
        with(binding.minionRecycler) {
            adapter = minionAdapter
            layoutManager = GridLayoutManager(requireContext(), maxSpan).apply {
                spanSizeLookup = spanSize
            }
        }
    }

    private val spanSize = object: GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when (minionAdapter.getItemViewType(position)) {
                R.layout.item_attack_pattern -> 1
                R.layout.item_resist_property -> maxSpan / 2
                else -> maxSpan
            }
        }
    }

    override fun onMinionClicked(skill: Skill) {
        if (skill.enemyMinionList.isNotEmpty()) {
            sharedClanBattle.selectedMinion = skill.enemyMinionList
            findNavController().navigate(EnemyFragmentDirections.actionNavEnemyToNavMinion())
        }
    }

    override fun onItemClicked(position: Int) {
    }
}
