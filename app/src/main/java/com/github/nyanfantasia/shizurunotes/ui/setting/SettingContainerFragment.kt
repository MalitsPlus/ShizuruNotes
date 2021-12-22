package com.github.nyanfantasia.shizurunotes.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.databinding.FragmentSettingContainerBinding

class SettingContainerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentSettingContainerBinding>(
            inflater, R.layout.fragment_setting_container, container, false
        ).apply {
            toolbarSettingFragment.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }

        childFragmentManager.beginTransaction()
            .replace(R.id.settingFragmentContainer, SettingFragment())
            .commit()

        return binding.root
    }

}
