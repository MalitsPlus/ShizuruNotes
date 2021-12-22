package com.github.nyanfantasia.shizurunotes.ui.setting.log

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.databinding.FragmentLogTextBinding
import com.google.android.material.snackbar.Snackbar

class LogTextFragment : Fragment() {

    private lateinit var binding: FragmentLogTextBinding
    private val args: LogTextFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogTextBinding.inflate(inflater, container, false).apply {
            logText = args.logText
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            logTextToolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
            setOptionItemClickListener(logTextToolbar)
        }
    }

    private fun setOptionItemClickListener(toolbar: Toolbar) {
        toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_copy -> {
                    val clipboard = getSystemService<ClipboardManager>(requireContext(), ClipboardManager::class.java)
                    val clip = ClipData.newPlainText("logText", args.logText)
                    clipboard?.setPrimaryClip(clip)
                    Snackbar
                        .make(binding.root, R.string.text_copy_finished, Snackbar.LENGTH_LONG)
                        .show()
                    true
                }
                else -> true
            }
        }
    }
}
