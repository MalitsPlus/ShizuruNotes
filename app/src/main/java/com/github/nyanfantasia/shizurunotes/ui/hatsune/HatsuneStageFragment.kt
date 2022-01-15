package com.github.nyanfantasia.shizurunotes.ui.hatsune

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nyanfantasia.shizurunotes.data.HatsuneStage
import com.github.nyanfantasia.shizurunotes.databinding.FragmentHatsuneStageBinding
import com.github.nyanfantasia.shizurunotes.ui.base.ViewType
import com.github.nyanfantasia.shizurunotes.ui.base.ViewTypeAdapter
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelHatsune
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelHatsuneFactory

class HatsuneStageFragment : Fragment(), OnHatsuneClickListener<HatsuneStage> {

    private lateinit var binding: FragmentHatsuneStageBinding
    private lateinit var sharedHatsune: SharedViewModelHatsune
    private lateinit var hatsuneStageVM: HatsuneStageViewModel
    private val hatsuneStageAdapter by lazy { ViewTypeAdapter<ViewType<*>>(onItemActionListener = this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedHatsune = ViewModelProvider(requireActivity())[SharedViewModelHatsune::class.java]
        hatsuneStageVM = ViewModelProvider(this, SharedViewModelHatsuneFactory(sharedHatsune))[HatsuneStageViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHatsuneStageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedHatsune.loadData()
        sharedHatsune.hatsuneStageList.observe(viewLifecycleOwner, {
            binding.hatsuneStageProgressBar.visibility = if (it.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
            hatsuneStageAdapter.setUpdatedList(hatsuneStageVM.viewList)
        })
        binding.hatsuneStageToolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
        with (binding.hatsuneStageRecycler) {
            adapter = hatsuneStageAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onStageClicked(item: HatsuneStage) {
        sharedHatsune.selectedHatsune = item
        findNavController().navigate(HatsuneStageFragmentDirections.actionNavHatsuneStageToNavHatsuneWave())
    }

    override fun onItemClicked(position: Int) {
    }
}
