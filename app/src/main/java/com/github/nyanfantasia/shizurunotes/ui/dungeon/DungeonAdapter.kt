package com.github.nyanfantasia.shizurunotes.ui.dungeon

import android.view.View
import androidx.navigation.findNavController
import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.data.Dungeon
import com.github.nyanfantasia.shizurunotes.databinding.ListItemDungeonBinding
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelClanBattle
import com.github.nyanfantasia.shizurunotes.ui.base.BaseRecyclerAdapter

class DungeonAdapter(
    private val sharedClanBattle: SharedViewModelClanBattle
) : BaseRecyclerAdapter<Dungeon, ListItemDungeonBinding>(R.layout.list_item_dungeon) {

    override fun onBindViewHolder(holder: VH<ListItemDungeonBinding>, position: Int) {
        with(holder.binding){
            val thisDungeon = itemList[position]
            dungeon = thisDungeon
            textDungeonDescription.text = thisDungeon.description
            clickListener = View.OnClickListener {
                sharedClanBattle.mSetSelectedBoss(thisDungeon.dungeonBoss)
                it.findNavController().navigate(
                    DungeonFragmentDirections.actionNavDungeonToNavEnemy()
                )
            }
            executePendingBindings()
        }
    }
}