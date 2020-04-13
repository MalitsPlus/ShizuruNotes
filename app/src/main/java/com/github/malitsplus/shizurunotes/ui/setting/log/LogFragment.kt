package com.github.malitsplus.shizurunotes.ui.setting.log

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.FragmentLogBinding
import com.github.malitsplus.shizurunotes.utils.LogUtils

class LogFragment : Fragment() {

    private lateinit var binding: FragmentLogBinding
    private lateinit var mAdapter: LogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogBinding.inflate(inflater, container, false).apply {
            logProgressBar.visibility = View.VISIBLE
            logToolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
            logFloatingButton.setOnClickListener {
                MaterialDialog(requireContext(), MaterialDialog.DEFAULT_BEHAVIOR)
                    .title(res = R.string.title_log)
                    .message(res = R.string.text_log_clean)
                    .show {
                        positiveButton(res = R.string.text_delete) {
                            deleteLogFiles()
                        }
                        negativeButton(res = R.string.text_deney)
                    }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mLayoutManager = LinearLayoutManager(requireContext())
        mAdapter = LogAdapter()
        binding.recyclerViewLog.apply {
            layoutManager = mLayoutManager
            adapter = mAdapter
            setHasFixedSize(true)
        }
        val files = LogUtils.getLogFiles().filter {
            it.isFile
        }.sortedDescending()
        binding.logProgressBar.visibility = View.GONE
        mAdapter.update(files.toMutableList())
    }

    private fun deleteLogFiles() {
        LogUtils.getLogFiles().forEach {
            if (it.isFile) {
                it.delete()
            }
        }
        mAdapter.update(mutableListOf())
    }

}
