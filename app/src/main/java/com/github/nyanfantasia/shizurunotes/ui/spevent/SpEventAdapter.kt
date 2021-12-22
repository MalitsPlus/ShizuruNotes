package com.github.nyanfantasia.shizurunotes.ui.spevent

import android.view.View
import androidx.navigation.findNavController
import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.data.SpEvent
import com.github.nyanfantasia.shizurunotes.databinding.ItemSpEventBinding
import com.github.nyanfantasia.shizurunotes.ui.base.BaseRecyclerAdapter
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelClanBattle

class SpEventAdapter(
    private val sharedClanBattle: SharedViewModelClanBattle
) : BaseRecyclerAdapter<SpEvent, ItemSpEventBinding>(R.layout.item_sp_event) {
    override fun onBindViewHolder(holder: VH<ItemSpEventBinding>, position: Int) {
        with(holder.binding){
            val thisSpEvent = itemList[position]
            spEvent = thisSpEvent
            spName.text = thisSpEvent.name
            clickListener = View.OnClickListener {
                sharedClanBattle.mSetSelectedBoss(thisSpEvent.spBoss)
                it.findNavController().navigate(
                    SpEventFragmentDirections.actionNavSpEventToNavEnemy()
                )
            }
            executePendingBindings()
        }
    }
}