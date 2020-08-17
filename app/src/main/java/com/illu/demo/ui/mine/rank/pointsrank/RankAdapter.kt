package com.illu.demo.ui.mine.rank.pointsrank

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.illu.demo.R
import com.illu.demo.base.BaseViewModel
import kotlinx.android.synthetic.main.item_points_rank.view.*

class RankAdapter(layoutRes: Int = R.layout.item_points_rank) : BaseQuickAdapter<Rank, BaseViewHolder>(layoutRes) {

    override fun convert(helper: BaseViewHolder, item: Rank) {
        helper.itemView.run {
            tvNo.text = item.rank
            tvName.text = item.username
            tvPoints.text = item.coinCount.toString()
        }
    }

}