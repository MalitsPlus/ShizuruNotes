package com.github.nyanfantasia.shizurunotes.ui.comparison

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.databinding.FragmentRankComparisonBinding
import com.github.nyanfantasia.shizurunotes.ui.base.MaterialSpinnerAdapter
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelChara
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelCharaFactory

class RankComparisonFragment : Fragment() {

    lateinit var binding: FragmentRankComparisonBinding
    lateinit var sharedChara: SharedViewModelChara
    lateinit var comparisonViewModel: ComparisonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedChara = ViewModelProvider(requireActivity())[SharedViewModelChara::class.java]
        comparisonViewModel = ViewModelProvider(this, SharedViewModelCharaFactory(sharedChara))[ComparisonViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRankComparisonBinding.inflate(layoutInflater, container, false).apply {
            rankComparisonToolbar.setNavigationOnClickListener {
                it.findNavController().navigateUp()
            }
            dropdownRankFrom.apply {
                setAdapter(
                    MaterialSpinnerAdapter(
                        this@RankComparisonFragment.requireContext(),
                        R.layout.dropdown_item_chara_list,
                        comparisonViewModel.rankList.toTypedArray()
                    )
                )
                setText(comparisonViewModel.rankList[1].toString())
            }
            dropdownRankTo.apply {
                setAdapter(
                    MaterialSpinnerAdapter(
                        this@RankComparisonFragment.requireContext(),
                        R.layout.dropdown_item_chara_list,
                        comparisonViewModel.rankList.toTypedArray()
                    )
                )
                setText(comparisonViewModel.rankList[0].toString())
            }
            calculateButton.setOnClickListener {
                sharedChara.rankComparisonFrom = dropdownRankFrom.text.toString().toInt()
                sharedChara.rankComparisonTo = dropdownRankTo.text.toString().toInt()
                it.findNavController().navigate(RankComparisonFragmentDirections.actionNavRankCompareToNavCompareList())
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            dropdownRankFrom.dismissDropDown()
            dropdownRankTo.dismissDropDown()
        }
    }

}
