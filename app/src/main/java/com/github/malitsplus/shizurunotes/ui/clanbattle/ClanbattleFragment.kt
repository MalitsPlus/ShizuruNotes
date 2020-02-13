package com.github.malitsplus.shizurunotes.ui.clanbattle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.malitsplus.shizurunotes.R

class ClanbattleFragment : Fragment() {

    private lateinit var clanbattleViewModel: ClanbattleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        clanbattleViewModel =
            ViewModelProvider(this).get(ClanbattleViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_clan_battle, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        clanbattleViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}