package com.github.nyanfantasia.shizurunotes.ui.calendar

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.data.CampaignSchedule
import com.github.nyanfantasia.shizurunotes.data.EventSchedule
import com.github.nyanfantasia.shizurunotes.databinding.ItemScheduleBinding
import com.github.nyanfantasia.shizurunotes.ui.base.BaseRecyclerAdapter

class DayScheduleAdapter : BaseRecyclerAdapter<EventSchedule, ItemScheduleBinding>(R.layout.item_schedule) {
    override fun onBindViewHolder(holder: VH<ItemScheduleBinding>, position: Int) {
        with(holder.binding) {
            val item = itemList[position]
            schedule = item
            if (item is CampaignSchedule) {
                typeDot.setColorFilter(item.campaignType.shortColor())
            } else {
                typeDot.setColorFilter(item.type.color)
            }
            executePendingBindings()
        }
    }
}