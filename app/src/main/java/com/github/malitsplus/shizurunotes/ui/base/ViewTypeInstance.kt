package com.github.malitsplus.shizurunotes.ui.base

import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Chara

data class CharaProfileVT(
    private val chara: Chara
) : ViewType<Chara> {
    override val layoutId = R.layout.item_chara_profile
    override val isUserInteractionEnabled = false
    override val data = chara
}

