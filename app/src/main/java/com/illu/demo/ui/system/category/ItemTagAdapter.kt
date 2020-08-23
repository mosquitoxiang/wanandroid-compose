package com.illu.demo.ui.system.category

import android.view.LayoutInflater
import android.view.View
import com.illu.demo.R
import com.illu.demo.ui.home.project.CategoryBean
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.item_nav_tag.view.*

class ItemTagAdapter(private val categoryList: List<CategoryBean>) :
    TagAdapter<CategoryBean>(categoryList) {
    override fun getView(parent: FlowLayout?, position: Int, category: CategoryBean?): View {
        return LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_system_category_tag, parent, false)
            .apply {
                tvTag.text = categoryList[position].name
            }
    }

    override fun setSelected(position: Int, t: CategoryBean?): Boolean {
        return super.setSelected(position, t)
    }
}