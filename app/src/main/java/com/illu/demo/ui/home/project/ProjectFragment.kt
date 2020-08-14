package com.illu.demo.ui.home.project

import com.illu.demo.R
import com.illu.demo.base.BaseVmFragment

class ProjectFragment : BaseVmFragment<ProjectViewModel>() {

    companion object {
        fun instance() = ProjectFragment()
    }
    override fun viewModelClass(): Class<ProjectViewModel> = ProjectViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_project

    override fun initView() {
    }

    override fun initData() {
    }

}