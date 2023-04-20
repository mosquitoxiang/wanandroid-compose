//package com.illu.demo.ui.home.square
//
//import androidx.core.view.isVisible
//import com.chad.library.adapter.base.BaseQuickAdapter
//import com.chad.library.adapter.base.BaseViewHolder
//import com.chad.library.adapter.base.loadmore.LoadMoreView
//import com.illu.demo.R
//import com.illu.demo.bean.ArticleBean
//import kotlinx.android.synthetic.main.item_article_simple.view.*
//import java.util.*
//
//class SimpleArticleAdapter(layoutId: Int = R.layout.item_article_simple) :
//    BaseQuickAdapter<ArticleBean, BaseViewHolder>(layoutId){
//
//    override fun convert(helper: BaseViewHolder, item: ArticleBean) {
//        helper.run {
//            itemView.run {
//                tv_author.text = when {
//                    !item.author.isNullOrEmpty() -> item.author
//                    !item.shareUser.isNullOrEmpty() -> item.shareUser
//                    else -> context.getString(R.string.no_name)
//                }
//                tv_fresh.isVisible = item.fresh
//                tv_title.text = item.title
//                tv_time.text = item.niceDate
//                iv_collect.isSelected = item.collect
//            }
//            addOnClickListener(R.id.iv_collect)
//        }
//    }
//}