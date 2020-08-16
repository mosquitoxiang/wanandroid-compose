package com.illu.demo.ui.home.project

import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.illu.baselibrary.ext.dpToPxInt
import com.illu.demo.R
import kotlinx.android.synthetic.main.item_category_sub.view.*

class ProjectTreeAdapter(layoutRes: Int = R.layout.item_category_sub) :
    BaseQuickAdapter<ProjectBean, BaseViewHolder>(layoutRes) {

    private var checkedPosition = 0
    var onCheckedListener: ((position: Int) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: ProjectBean) {
        helper.itemView.run {
            ctvCategory.text = item.name
            ctvCategory.isChecked = checkedPosition == helper.adapterPosition
            setOnClickListener {
                val position = helper.adapterPosition
                check(position)
                onCheckedListener?.invoke(position)
            }
            updateLayoutParams<ViewGroup.MarginLayoutParams> {
                marginStart = 8f.dpToPxInt()
            }
        }
    }

    fun check(position: Int) {
        checkedPosition = position
        notifyDataSetChanged()
    }

    override fun setEmptyView(layoutResId: Int, viewGroup: ViewGroup?) {
        super.setEmptyView(layoutResId, viewGroup)
    }

}