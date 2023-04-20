//package com.illu.demo.ui.navigation
//
//import com.chad.library.adapter.base.BaseQuickAdapter
//import com.chad.library.adapter.base.BaseViewHolder
//import com.illu.demo.R
//import com.illu.demo.bean.ArticleBean
//import kotlinx.android.synthetic.main.item_navigation.view.*
//
//class NavAdapter(layoutRes: Int = R.layout.item_navigation) : BaseQuickAdapter<Nav, BaseViewHolder>(layoutRes) {
//
//    var onItemTagClickListener: ((article: ArticleBean) -> Unit)? = null
//
//    override fun convert(helper: BaseViewHolder, item: Nav) {
//        helper.itemView.run {
//            title.text = item.name
//            tagFlawLayout.adapter = NavSpecialAdaper(item.articles)
//            tagFlawLayout.setOnTagClickListener { _, position, _ ->
//                onItemTagClickListener?.invoke(item.articles[position])
//                true
//            }
//        }
//    }
//
//}