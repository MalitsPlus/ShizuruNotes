package com.github.malitsplus.shizurunotes.ui.calendar

import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.CampaignSchedule
import com.github.malitsplus.shizurunotes.data.EventSchedule
import com.github.malitsplus.shizurunotes.databinding.ItemScheduleBinding
import com.github.malitsplus.shizurunotes.ui.base.BaseRecyclerAdapter

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