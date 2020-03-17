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
import com.github.malitsplus.shizurunotes.databinding.FragmentClanBattleBinding
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelClanBattle
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelClanBattleFactory

class ClanBattleFragment : Fragment() {

    private lateinit var clanBattleViewModel: ClanBattleViewModel
    private lateinit var sharedClanBattle: SharedViewModelClanBattle
    private lateinit var adapter: ClanBattleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedClanBattle = ViewModelProvider(activity!!)[SharedViewModelClanBattle::class.java]
        clanBattleViewModel = ViewModelProvider(this, SharedViewModelClanBattleFactory(sharedClanBattle))[ClanBattleViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding =
            DataBindingUtil.inflate<FragmentClanBattleBinding>(
                inflater, R.layout.fragment_clan_battle, container, false
            ).apply{
                lifecycleOwner = viewLifecycleOwner
                adapter = ClanBattleAdapter(context!!, sharedClanBattle)
                clanBattleListRecycler.layoutManager = LinearLayoutManager(context)
                clanBattleListRecycler.adapter = adapter
                clanBattleListRecycler.setHasFixedSize(true)
            }

        sharedClanBattle.apply {
            periodList.observe(
                viewLifecycleOwner, Observer{
                    adapter.update(it)
                }
            )
            loadingFlag.observe(
                viewLifecycleOwner, Observer {
                    if (it) binding.clanBattleProgressBar.visibility = View.VISIBLE
                    else binding.clanBattleProgressBar.visibility = View.GONE
                }
            )
        }

        binding.viewModel = clanBattleViewModel
        sharedClanBattle.loadData()
        return binding.root
    }
}