package com.github.malitsplus.shizurunotes.ui.dungeon

import android.view.View
import androidx.navigation.findNavController
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Dungeon
import com.github.malitsplus.shizurunotes.databinding.ListItemDungeonBinding
import com.github.malitsplus.shizurunotes.ui.SharedViewModelClanBattle
import com.github.malitsplus.shizurunotes.ui.basic.BasicRecyclerAdapter

class DungeonAdapter(
    private val sharedClanBattle: SharedViewModelClanBattle
) : BasicRecyclerAdapter<Dungeon, ListItemDungeonBinding>(R.layout.list_item_dungeon) {

    override fun onBindViewHolder(holder: VH<ListItemDungeonBinding>, position: Int) {
        with(holder.binding){
            val thisDungeon = itemList[position]
            dungeon = thisDungeon
            textDungeonDescription.text = thisDungeon.description
            clickListener = View.OnClickListener {
                sharedClanBattle.mSetSelectedBoss(thisDungeon.dungeonBoss)
                it.findNavController().navigate(
                    DungeonFragmentDirections.actionNavDungeonToNavClanBattleBossDetails()
                )
            }
        }
    }
}