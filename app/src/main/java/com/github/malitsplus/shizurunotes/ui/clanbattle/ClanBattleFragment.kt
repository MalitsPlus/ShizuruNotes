package com.github.malitsplus.shizurunotes.ui.clanbattle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.ClanBattlePeriod
import com.github.malitsplus.shizurunotes.databinding.FragmentClanBattleBinding
import com.github.malitsplus.shizurunotes.ui.SharedViewModelClanBattle
import kotlinx.android.synthetic.main.fragment_clan_battle.*

class ClanBattleFragment : Fragment() {

    private lateinit var clanBattleViewModel: ClanBattleViewModel
    private lateinit var adapter: ClanBattleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedViewModel = ViewModelProvider(activity!!).get(SharedViewModelClanBattle::class.java)
        clanBattleViewModel = ViewModelProvider(this).get(ClanBattleViewModel::class.java)

        val binding =
            DataBindingUtil.inflate<FragmentClanBattleBinding>(
                inflater, R.layout.fragment_clan_battle, container, false
            ).apply{
                viewModel = clanBattleViewModel
                lifecycleOwner = viewLifecycleOwner

                adapter = ClanBattleAdapter(context!!, sharedViewModel)
                clanBattleListRecycler.layoutManager = LinearLayoutManager(context)
                clanBattleListRecycler.adapter = adapter
                clanBattleListRecycler.setHasFixedSize(true)


            }

        clanBattleViewModel.periodList.observe(
            viewLifecycleOwner, Observer<List<ClanBattlePeriod>>{
            adapter.update(it)
        })

        clanBattleViewModel.periodList.value = sharedViewModel.periodList

        return binding.root
    }
}