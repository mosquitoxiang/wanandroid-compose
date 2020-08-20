package com.illu.demo.ui.find

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.illu.demo.R
import kotlinx.android.synthetic.main.item_hot_word.view.*

class HotKeyAdapter(layoutRes: Int = R.layout.item_hot_word) : BaseQuickAdapter<HotKey, BaseViewHolder>(layoutRes) {

    override fun convert(helper: BaseViewHolder, item: HotKey) {
        helper.itemView.run {
            tvName.text = item.name
        }
    }
}