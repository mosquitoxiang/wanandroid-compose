package com.illu.demo.ui.home.search.history

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.illu.demo.R
import com.illu.demo.common.room.History
import kotlinx.android.synthetic.main.item_search_history.view.*

class HistoryAdapter(layoutRes: Int = R.layout.item_search_history) : BaseQuickAdapter<History, BaseViewHolder>(layoutRes) {

    var onDeleteCliciListener: ((position: Int) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: History?) {
        helper.itemView.run {
            tvLabel.text = item?.searchWord
            ivDelete.setOnClickListener {
                onDeleteCliciListener?.invoke(helper.layoutPosition)
            }
        }
    }

}