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
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara

class CharaProfileFragment : Fragment() {

    lateinit var sharedChara: SharedViewModelChara

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedChara = ViewModelProvider(requireActivity()).get(SharedViewModelChara::class.java)
    }

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

        sharedChara.backFlag = false

        binding.toolbarCharaProfile.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }

        binding.chara = sharedChara.selectedChara
        //(activity as AppCompatActivity).supportActionBar!!.title = sharedViewModel.getSelectedChara().actualName
        return binding.root
    }
}