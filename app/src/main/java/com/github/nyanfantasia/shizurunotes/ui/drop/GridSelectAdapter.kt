package com.github.nyanfantasia.shizurunotes.ui.drop

import android.content.Context
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N
import com.github.nyanfantasia.shizurunotes.data.Equipment
import com.github.nyanfantasia.shizurunotes.databinding.ItemGridIconBinding
import com.github.nyanfantasia.shizurunotes.databinding.ItemHintTextBinding
import com.github.nyanfantasia.shizurunotes.ui.BottomNaviFragmentDirections
import com.github.nyanfantasia.shizurunotes.ui.base.BaseHintAdapter
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelEquipment

class GridSelectAdapter(
    private val mContext: Context,
    private val sharedEquipment: SharedViewModelEquipment
) : BaseHintAdapter<ItemGridIconBinding, ItemHintTextBinding>(mContext, R.layout.item_grid_icon, R.layout.item_hint_text) {

    private val maxSelectNum = 5

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is HintTextViewHolder -> {
                with(holder.binding as ItemHintTextBinding) {
                    holder.binding.hintText = I18N.getString(R.string.text_drop_rarity).format(itemList[position])
                    executePendingBindings()
                }
            }
            is InstanceViewHolder -> {
                with(holder.binding as ItemGridIconBinding){
                    val thisEquipment = itemList[position] as Equipment
                    root.tag = "equipmentItem${thisEquipment.equipmentId}"
                    iconUrl = thisEquipment.iconUrl
                    sharedEquipment.selectedDrops.value?.let {
                        if (it.contains(thisEquipment)) {
                            setItemStatus(root, true)
                        } else {
                            setItemStatus(root, false)
                        }
                        clickListener = View.OnClickListener { v ->
                            if (it.contains(thisEquipment)) {
                                it.remove(thisEquipment)
                                setItemStatus(v, false)
                            } else if (it.size < maxSelectNum){
                                it.add(thisEquipment)
                                setItemStatus(v, true)
                            }
                        }
                    }
                    this.itemGridContainer.setOnLongClickListener { v ->
                        sharedEquipment.selectedEquipment = thisEquipment
                        v.findNavController().navigate(BottomNaviFragmentDirections.actionNavBottomNavigationToNavEquipment())
                        true
                    }
                    executePendingBindings()
                }
            }
        }
    }

    private fun setItemStatus(view: View, selected: Boolean) {
        view.background = if (selected) {
            mContext.getDrawable(R.drawable.shape_selected_background)
        } else {
            mContext.getDrawable(R.drawable.shape_unselected_background)
        }
    }
}