package com.illu.demo.ui.navigation

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.illu.demo.R
import com.illu.demo.bean.ArticleBean
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter

class NavSpecialAdaper(val list: List<ArticleBean>) : TagAdapter<ArticleBean>(list) {

    override fun getView(parent: FlowLayout, position: Int, t: ArticleBean): View {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_nav_tag, parent, false)
        val tv = view.findViewById<TextView>(R.id.tvTag)
        tv.text = list[position].title
        return view
    }

}