package com.illu.demo.ui.find

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.illu.demo.R
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter

class TagAdapter(val list: List<CommonWeb>) : TagAdapter<CommonWeb>(list) {

    override fun getView(parent: FlowLayout?, position: Int, t: CommonWeb?): View {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_nav_tag, parent, false)
        val tv = view.findViewById<TextView>(R.id.tvTag)
        tv.text = list[position].name
        return view
    }

}