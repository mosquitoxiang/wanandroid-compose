//package com.illu.demo.ui.mine.rank.minepoints
//
//import android.annotation.SuppressLint
//import com.chad.library.adapter.base.BaseQuickAdapter
//import com.chad.library.adapter.base.BaseViewHolder
//import com.illu.baselibrary.ext.toDateTime
//import com.illu.demo.R
//import kotlinx.android.synthetic.main.item_mine_points.view.*
//
//class MinePointsAdapter(layoutRes: Int = R.layout.item_mine_points) : BaseQuickAdapter<MinePointsListBean, BaseViewHolder>(layoutRes) {
//
//    @SuppressLint("SetTextI18n")
//    override fun convert(helper: BaseViewHolder, item: MinePointsListBean) {
//        helper.itemView.run {
//            tvReason.text = item.reason
//            tvTime.text = item.date.toDateTime("YYYY-MM-dd HH:mm:ss")
//            tvPoint.text = "+${item.coinCount}"
//        }
//    }
//
//}