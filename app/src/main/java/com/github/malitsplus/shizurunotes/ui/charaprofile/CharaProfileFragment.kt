package com.github.malitsplus.shizurunotes.ui.charaprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.FragmentCharaProfileBinding
import com.github.malitsplus.shizurunotes.ui.SharedViewModel
import com.github.malitsplus.shizurunotes.ui.SharedViewModelFactory

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
        val sharedViewModel = ViewModelProvider(activity!!).get(SharedViewModel::class.java)
        val profileViewModel = ViewModelProvider(this, SharedViewModelFactory(sharedViewModel)).get(
            CharaProfileViewModel::class.java
        )
        binding.chara = sharedViewModel.selectedChara
        //(activity as AppCompatActivity).supportActionBar!!.title = sharedViewModel.getSelectedChara().actualName
        return binding.root
    }
}