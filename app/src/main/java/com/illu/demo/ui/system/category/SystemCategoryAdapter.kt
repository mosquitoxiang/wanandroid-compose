package com.illu.demo.ui.system.category

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.illu.baselibrary.ext.htmlToSpanned
import com.illu.demo.R
import com.illu.demo.ui.home.project.CategoryBean
import kotlinx.android.synthetic.main.item_system_category.view.*

class SystemCategoryAdapter(
    layoutResId: Int = R.layout.item_system_category,
    categoryList: List<CategoryBean>,
    var checked: Pair<Int, Int>
) : BaseQuickAdapter<CategoryBean, BaseViewHolder>(layoutResId, categoryList) {

    var onCheckedListener: ((checked: Pair<Int, Int>) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: CategoryBean) {
        helper.itemView.run {
            title.text = item.name.htmlToSpanned()
            tagFlowLayout.adapter = ItemTagAdapter(item.children)
            if (checked.first == helper.adapterPosition) {
                tagFlowLayout.adapter.setSelectedList(checked.second)
            }
            tagFlowLayout.setOnTagClickListener { _, position, _ ->
                checked = helper.adapterPosition to position
                notifyDataSetChanged()
                tagFlowLayout.postDelayed({
                    onCheckedListener?.invoke(checked)
                }, 300)
                true
            }
        }
    }
}