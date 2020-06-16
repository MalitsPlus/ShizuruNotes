package com.github.malitsplus.shizurunotes.ui.hatsune

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.github.malitsplus.shizurunotes.data.Skill
import com.github.malitsplus.shizurunotes.data.WaveGroup
import com.github.malitsplus.shizurunotes.databinding.FragmentHatsuneWaveBinding
import com.github.malitsplus.shizurunotes.ui.base.ViewType
import com.github.malitsplus.shizurunotes.ui.base.ViewTypeAdapter
import com.github.malitsplus.shizurunotes.ui.enemy.OnEnemyActionListener
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelClanBattle
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelHatsune
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelHatsuneFactory

class HatsuneWaveFragment : Fragment(), OnWaveClickListener {

    private lateinit var binding: FragmentHatsuneWaveBinding
    private lateinit var sharedHatsune: SharedViewModelHatsune
    private lateinit var sharedClanBattle: SharedViewModelClanBattle
    private lateinit var hatsuneWaveVM: HatsuneWaveViewModel
    private val hatsuneWaveAdapter by lazy { ViewTypeAdapter<ViewType<*>>(onItemActionListener = this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedHatsune = ViewModelProvider(requireActivity())[SharedViewModelHatsune::class.java]
        sharedClanBattle = ViewModelProvider(requireActivity())[SharedViewModelClanBattle::class.java]
        hatsuneWaveVM = ViewModelProvider(this, SharedViewModelHatsuneFactory(sharedHatsune))[HatsuneWaveViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHatsuneWaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.hatsuneWaveToolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
        hatsuneWaveAdapter.setList(hatsuneWaveVM.viewList)
        with (binding.hatsuneWaveRecycler) {
            adapter = hatsuneWaveAdapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        }
    }

    override fun onWaveClick(waveGroup: WaveGroup) {
        sharedClanBattle.mSetSelectedBoss(waveGroup.enemyList)
        findNavController().navigate(HatsuneWaveFragmentDirections.actionNavHatsuneWaveToNavEnemy())
    }

    override fun onItemClicked(position: Int) {
    }
}
