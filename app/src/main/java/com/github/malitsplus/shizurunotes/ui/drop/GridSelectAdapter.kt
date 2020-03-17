package com.github.malitsplus.shizurunotes.ui.drop

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.data.Equipment
import com.github.malitsplus.shizurunotes.databinding.ItemGridIconBinding
import com.github.malitsplus.shizurunotes.ui.base.BaseHintAdapter
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelEquipment

class GridSelectAdapter(
    private val mContext: Context,
    private val sharedEquipment: SharedViewModelEquipment
) : BaseHintAdapter<ItemGridIconBinding>(mContext, R.layout.item_grid_icon) {

    private val maxSelectNum = 5

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is HintTextViewHolder -> {
                with(holder.binding) {
                    holder.binding.hintText = I18N.getString(R.string.text_drop_rarity).format(itemList[position])
                    executePendingBindings()
                }
            }
            is InstanceViewHolder -> {
                with(holder.binding as ItemGridIconBinding){
                    val thisEquipment = itemList[position] as Equipment
                    iconUrl = thisEquipment.iconUrl
                    sharedEquipment.selectedDrops.value?.let {
                        if (it.contains(thisEquipment)) {
                            setItemStatus(this, true)
                        } else {
                            setItemStatus(this, false)
                        }
                        clickListener = View.OnClickListener { _ ->
                            if (it.contains(thisEquipment)) {
                                it.remove(thisEquipment)
                                setItemStatus(this, false)
                            } else if (it.size < maxSelectNum){
                                it.add(thisEquipment)
                                setItemStatus(this, true)
                            }
                        }
                    }
                    executePendingBindings()
                }
            }
        }
    }

    private fun setItemStatus(binding: ItemGridIconBinding, selected: Boolean) {
        binding.itemGridContainer.background = if (selected) {
            mContext.getDrawable(R.drawable.color_selected_background)
        } else {
            mContext.getDrawable(R.drawable.color_unselected_background)
        }
    }
}