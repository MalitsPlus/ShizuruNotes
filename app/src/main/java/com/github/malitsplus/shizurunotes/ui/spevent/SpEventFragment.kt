package com.github.malitsplus.shizurunotes.ui.spevent

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
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
import com.github.malitsplus.shizurunotes.databinding.FragmentSpEventBinding
import com.github.malitsplus.shizurunotes.ui.dungeon.DungeonAdapter
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelClanBattle

class SpEventFragment : Fragment() {

    private lateinit var sharedClanBattle: SharedViewModelClanBattle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedClanBattle = ViewModelProvider(requireActivity())[SharedViewModelClanBattle::class.java].apply {
            loadSpEvent()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val spEventAdapter = SpEventAdapter(sharedClanBattle)

        val binding = DataBindingUtil.inflate<FragmentSpEventBinding>(
            inflater, R.layout.fragment_sp_event, container, false
        ).apply {
            toolbarSpEventFragment.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }

            with(recyclerSpEvent){
                layoutManager = LinearLayoutManager(context)
                adapter = spEventAdapter
                setHasFixedSize(true)
            }
        }

        sharedClanBattle.loadingFlag.observe(viewLifecycleOwner) {
            if (!it) {
                spEventAdapter.update(sharedClanBattle.spEventList)
                binding.spEventListProgressBar.visibility = View.GONE
            } else {
                binding.spEventListProgressBar.visibility = View.VISIBLE
            }
        }

        return binding.root
    }
}