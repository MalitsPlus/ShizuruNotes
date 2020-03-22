package com.github.malitsplus.shizurunotes.ui.minion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.FragmentMinionBinding
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelClanBattle

class MinionFragment : Fragment() {

    private val args: MinionFragmentArgs by navArgs()
    private lateinit var sharedChara: SharedViewModelChara
    private lateinit var sharedClanBattle: SharedViewModelClanBattle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedChara = ViewModelProvider(requireActivity())[SharedViewModelChara::class.java]
        sharedClanBattle = ViewModelProvider(requireActivity())[SharedViewModelClanBattle::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentMinionBinding>(
            inflater, R.layout.fragment_minion, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            toolbarMinion.setNavigationOnClickListener {
                it.findNavController().navigateUp()
            }
        }

        when(args.minionType){
            1 -> with(binding.minionRecycler) {
                adapter = MinionAdapter(R.layout.list_item_minion, sharedChara).apply {
                    sharedChara.selectedMinion?.let { itemList = it }
                }
                layoutManager = LinearLayoutManager(this@MinionFragment.context)
                sharedChara.backFlag = true
            }
            2 -> with(binding.minionRecycler) {
                adapter = EnemyMinionAdapter(R.layout.list_item_minion, sharedClanBattle).apply {
                    sharedClanBattle.selectedMinion?.let { itemList = it }
                }
                layoutManager = LinearLayoutManager(this@MinionFragment.context)
            }
        }

        return binding.root
    }
}
