package com.github.malitsplus.shizurunotes.ui.minion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.FragmentMinionBinding
import com.github.malitsplus.shizurunotes.ui.SharedViewModelChara

class MinionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedChara = ViewModelProvider(activity!!)[SharedViewModelChara::class.java]

        val binding = DataBindingUtil.inflate<FragmentMinionBinding>(
            inflater, R.layout.fragment_minion, container, false
        ).apply {
            toolbarMinion.setNavigationOnClickListener {
                it.findNavController().navigateUp()
            }
            with(minionRecycler){
                adapter = MinionAdapter(R.layout.list_item_minion, sharedChara).apply {
                    sharedChara.selectedMinion?.let { itemList = it }
                }
                layoutManager = LinearLayoutManager(this@MinionFragment.context)
            }

        }



        return binding.root
    }
}
