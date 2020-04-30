package com.github.malitsplus.shizurunotes.ui.comparison

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.FragmentRankComparisonBinding

class RankComparisonFragment : Fragment() {

    lateinit var binding: FragmentRankComparisonBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankComparisonBinding.inflate(layoutInflater, container, false)
        binding.rankComparisonToolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }

        return binding.root
    }

}
