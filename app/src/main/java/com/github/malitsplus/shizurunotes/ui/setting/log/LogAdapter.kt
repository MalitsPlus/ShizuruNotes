package com.github.malitsplus.shizurunotes.ui.setting.log

import android.view.View
import androidx.navigation.findNavController
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.ItemSelectableTextBinding
import com.github.malitsplus.shizurunotes.ui.base.BaseRecyclerAdapter
import java.io.File

class LogAdapter : BaseRecyclerAdapter<File, ItemSelectableTextBinding>(R.layout.item_selectable_text) {
    override fun onBindViewHolder(holder: VH<ItemSelectableTextBinding>, position: Int) {
        with(holder.binding) {
            val item = itemList[position]
            text = item.name
            clickListener = View.OnClickListener {
                if (item.length() < 1024 * 1024 * 1024) {
                    val action = LogFragmentDirections.actionNavLogToNavLogText().setLogText(item.readText())
                    it.findNavController().navigate(action)
                }
            }
        }
    }
}