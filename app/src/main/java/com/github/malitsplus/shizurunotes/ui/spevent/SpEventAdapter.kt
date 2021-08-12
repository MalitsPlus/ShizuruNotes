package com.github.malitsplus.shizurunotes.ui.spevent

import android.view.View
import androidx.navigation.findNavController
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.SpEvent
import com.github.malitsplus.shizurunotes.databinding.ItemSpEventBinding
import com.github.malitsplus.shizurunotes.ui.base.BaseRecyclerAdapter
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelClanBattle

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