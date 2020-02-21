package com.github.malitsplus.shizurunotes.ui.setting

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.FragmentAboutBinding

class AboutFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val aboutViewModel = ViewModelProvider(this).get(AboutViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentAboutBinding>(
            inflater, R.layout.fragment_about, container, false
        ).apply {
            textDeveloper.apply {
                text = aboutViewModel.developer
                movementMethod = LinkMovementMethod.getInstance()
            }
            textLicense.apply {
                text = aboutViewModel.license
                movementMethod = LinkMovementMethod.getInstance()
            }
        }



        return binding.root
    }
}