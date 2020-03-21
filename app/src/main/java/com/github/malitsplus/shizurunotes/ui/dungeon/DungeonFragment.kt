package com.github.malitsplus.shizurunotes.ui.dungeon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.FragmentDungeonBinding
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelClanBattle

class DungeonFragment : Fragment() {

    private lateinit var sharedClanBattle: SharedViewModelClanBattle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedClanBattle = ViewModelProvider(requireActivity())[SharedViewModelClanBattle::class.java].apply {
            loadDungeon()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val dungeonAdapter = DungeonAdapter(sharedClanBattle)

        val binding = DataBindingUtil.inflate<FragmentDungeonBinding>(
            inflater, R.layout.fragment_dungeon, container, false
        ).apply {
            toolbarDungeonFragment.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }

            with(recyclerDungeon){
                layoutManager = LinearLayoutManager(context)
                adapter = dungeonAdapter
                setHasFixedSize(true)
            }
        }

        sharedClanBattle.loadingFlag.observe(viewLifecycleOwner, Observer {
            if (!it){
                dungeonAdapter.update(sharedClanBattle.dungeonList)
            }
        })

        sharedClanBattle.loadingFlag.observe(viewLifecycleOwner,
            Observer {
                if (it) {
                    binding.dungeonListProgressBar.visibility = View.VISIBLE
                } else {
                    binding.dungeonListProgressBar.visibility = View.GONE
                }
            }
        )

        return binding.root
    }

}