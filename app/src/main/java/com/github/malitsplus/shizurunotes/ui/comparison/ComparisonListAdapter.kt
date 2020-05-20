package com.github.malitsplus.shizurunotes.ui.comparison

import android.widget.TextView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.ResourceManager
import com.github.malitsplus.shizurunotes.data.RankComparison
import com.github.malitsplus.shizurunotes.databinding.ItemComparisonBinding
import com.github.malitsplus.shizurunotes.ui.base.BaseRecyclerAdapter

class ComparisonListAdapter : BaseRecyclerAdapter<RankComparison, ItemComparisonBinding>(R.layout.item_comparison) {
    override fun onBindViewHolder(holder: VH<ItemComparisonBinding>, position: Int) {
        with(holder.binding) {
            val item = itemList[position]
            comparison = item
            setTextColor(item.property.getAtk(), cmpAtk)
            setTextColor(item.property.getDef(), cmpDef)
            setTextColor(item.property.getPhysicalCritical(), cmpPhysicalCritical)
            setTextColor(item.property.getMagicStr(), cmpMagicStr)
            setTextColor(item.property.getMagicDef(), cmpMagicDef)
            setTextColor(item.property.getMagicCritical(), cmpMagicCritical)
            setTextColor(item.property.getHp(), cmpHp)
            setTextColor(item.property.getEnergyRecoveryRate(), cmpEnergyRecoveryRate)
            setTextColor(item.property.getEnergyReduceRate(), cmpEnergyReduceRate)
            setTextColor(item.property.getWaveEnergyRecovery(), cmpWaveEnergyRecovery)
            setTextColor(item.property.getLifeSteal(), cmpLifeSteal)
            setTextColor(item.property.getHpRecoveryRate(), cmpHpRecoveryRate)
            setTextColor(item.property.getWaveHpRecovery(), cmpWaveHpRecovery)
            setTextColor(item.property.getAccuracy(), cmpAccuracy)
            setTextColor(item.property.getDodge(), cmpDodge)
            executePendingBindings()
        }
    }

    private fun setTextColor(num: Int, textView: TextView) {
        if (num > 0) {
            textView.setTextColor(ResourceManager.get().getColor(R.color.green_350))
        } else if (num < 0) {
            textView.setTextColor(ResourceManager.get().getColor(R.color.red_500))
        } else {
            textView.setTextColor(ResourceManager.get().getColor(R.color.textPrimary))
        }
    }
}