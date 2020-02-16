package com.github.malitsplus.shizurunotes.ui.charaprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.FragmentCharaProfileBinding
import com.github.malitsplus.shizurunotes.ui.SharedViewModelChara
import com.github.malitsplus.shizurunotes.ui.SharedViewModelCharaFactory

class CharaProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.inflate<FragmentCharaProfileBinding>(
                inflater,
                R.layout.fragment_chara_profile,
                container,
                false
            )
        val sharedViewModel = ViewModelProvider(activity!!).get(SharedViewModelChara::class.java)
        val profileViewModel = ViewModelProvider(this,
            SharedViewModelCharaFactory(
                sharedViewModel
            )
        ).get(
            CharaProfileViewModel::class.java
        )

        binding.toolbarCharaProfile.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }

        binding.chara = sharedViewModel.selectedChara
        //(activity as AppCompatActivity).supportActionBar!!.title = sharedViewModel.getSelectedChara().actualName
        return binding.root
    }
}