package com.illu.demo.ui.system.category

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.illu.baselibrary.App
import com.illu.baselibrary.utils.getScreenHeight
import com.illu.demo.R
import com.illu.demo.ui.home.project.CategoryBean
import com.illu.demo.ui.system.SystemFragment
import kotlinx.android.synthetic.main.fragment_system_category.*

class SystemCategoryFragment : BottomSheetDialogFragment() {

    companion object {

        const val CATEGORY_LIST = "categoryList"

        fun instance(categoryList: ArrayList<CategoryBean>): SystemCategoryFragment {
            return SystemCategoryFragment().apply {
                arguments = Bundle().apply { putParcelableArrayList(CATEGORY_LIST, categoryList) }
            }
        }
    }

    private var height: Int? = null
    private var behavior: BottomSheetBehavior<View>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_system_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryList: List<CategoryBean> = arguments?.getParcelableArrayList(CATEGORY_LIST)!!
        val checked = (parentFragment as SystemFragment).getCurrentChecked()
        SystemCategoryAdapter(categoryList = categoryList, checked = checked).run {
            bindToRecyclerView(rv)
            onCheckedListener = {
                behavior?.state = BottomSheetBehavior.STATE_HIDDEN
                view.postDelayed({
                    (parentFragment as SystemFragment).check(it)
                }, 300)
            }
        }
        view.post {
            (rv.layoutManager as LinearLayoutManager)
                .scrollToPositionWithOffset(checked.first, 0)
        }
    }

    override fun onStart() {
        super.onStart()
        val bottomSheet: View = (dialog as BottomSheetDialog).delegate
            .findViewById(com.google.android.material.R.id.design_bottom_sheet)
            ?: return
        behavior = BottomSheetBehavior.from(bottomSheet)
        height?.let { behavior?.peekHeight = it }
        dialog?.window?.let {
            it.setGravity(Gravity.BOTTOM)
            it.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, height ?: ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    fun show(manager: FragmentManager, height: Int? = null) {
        this.height = height ?: (getScreenHeight(App.INSTANCE) * 0.75f).toInt()
        if (!this.isAdded) {
            super.show(manager, "SystemCategoryFragment")
        }
    }

}