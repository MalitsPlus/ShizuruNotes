package com.github.malitsplus.shizurunotes.ui.charaprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.FragmentCharaProfileBinding
import com.github.malitsplus.shizurunotes.ui.base.ViewType
import com.github.malitsplus.shizurunotes.ui.base.ViewTypeAdapter
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelCharaFactory

class CharaProfileFragment : Fragment() {

    private lateinit var binding: FragmentCharaProfileBinding

    private val maxSpan = 2

    private val sharedChara: SharedViewModelChara by lazy {
        ViewModelProvider(requireActivity())[SharedViewModelChara::class.java].apply {
            backFlag = false
        }
    }

    private val charaProfileVM: CharaProfileViewModel by lazy {
        ViewModelProvider(this, SharedViewModelCharaFactory(sharedChara))[CharaProfileViewModel::class.java]
    }

    private val charaProfileAdapter by lazy { ViewTypeAdapter<ViewType<*>>() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharaProfileBinding.inflate(inflater, container, false).apply {
            toolbarCharaProfile.apply {
                title = sharedChara.selectedChara?.unitName
                setNavigationOnClickListener { view ->
                    view.findNavController().navigateUp()
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.charaProfileRecycler.apply {
            charaProfileAdapter.setList(charaProfileVM.viewList)
            adapter = charaProfileAdapter
            layoutManager = GridLayoutManager(requireContext(), maxSpan).apply {
                spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (charaProfileAdapter.getItemViewType(position)) {
                            R.layout.item_chara_profile -> maxSpan

                            else -> maxSpan
                        }
                    }
                }
            }
        }
    }
}