package com.github.nyanfantasia.shizurunotes.ui.drop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nyanfantasia.shizurunotes.databinding.FragmentDropQuestBinding
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelEquipment
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelQuest

class DropQuestFragment : Fragment() {

    private lateinit var sharedEquipment: SharedViewModelEquipment
    private lateinit var sharedQuest: SharedViewModelQuest
    private lateinit var dropQuestVM: DropQuestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedEquipment = ViewModelProvider(requireActivity())[SharedViewModelEquipment::class.java]
        sharedQuest = ViewModelProvider(requireActivity())[SharedViewModelQuest::class.java]
        dropQuestVM = ViewModelProvider(this, DropQuestViewModelFactory(sharedQuest, sharedEquipment.selectedDrops.value))[DropQuestViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mAdapter = DropQuestAdapter(requireContext(), sharedEquipment)
        val binding = FragmentDropQuestBinding.inflate(
            inflater, container, false
        ).apply {
            questToolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
            questRecycler.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }

        sharedQuest.apply {
            loadingFlag.observe(viewLifecycleOwner, Observer {
                binding.questProgressBar.visibility = if (it) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            })
            questList.observe(viewLifecycleOwner, Observer {
                if (it.isNotEmpty()) {
                    dropQuestVM.search()
                }
            })
            if (questList.value.isNullOrEmpty()) {
                loadData()
            }
        }

        dropQuestVM.searchedQuestList.observe(viewLifecycleOwner, Observer {
            mAdapter.update(it)
        })

        return binding.root
    }
}
