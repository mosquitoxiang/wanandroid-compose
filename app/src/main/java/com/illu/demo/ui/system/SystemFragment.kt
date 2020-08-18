package com.illu.demo.ui.system

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.illu.demo.base.BaseVmFragment
import com.illu.baselibrary.utils.LogUtil
import com.illu.demo.R
import com.illu.demo.base.BaseFragmentPagerAdapter
import com.illu.demo.common.ScrollToTop
import com.illu.demo.ui.MainActivity
import com.illu.demo.ui.home.project.CategoryBean
import com.illu.demo.ui.system.pager.SystemPagerFragment
import kotlinx.android.synthetic.main.fragment_system.*
import kotlinx.android.synthetic.main.include_reload.*

class SystemFragment : BaseVmFragment<SystemViewModel>(), ScrollToTop {

    companion object {
        fun instance() = SystemFragment()
    }

    private var titles = mutableListOf<String>()
    private var fragments = mutableListOf<SystemPagerFragment>()
    private var currentOffset = 0

    override fun viewModelClass(): Class<SystemViewModel> = SystemViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_system

    override fun initView() {
        btnReload.setOnClickListener {
            mViewModel.getCategory()
        }
    }

    override fun lazyLoadData() {
        mViewModel.getCategory()
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            categoryList.observe(viewLifecycleOwner, Observer {
                ivFilter.visibility = View.VISIBLE
                tabLayout.visibility = View.VISIBLE
                viewPager.visibility = View.VISIBLE
                setUp(it)
            })
            loadingStatus.observe(viewLifecycleOwner, Observer {
                progressBar.isVisible = it
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible = it
            })
        }
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offset ->
            if (activity is MainActivity && currentOffset != offset) {
                (activity as MainActivity).animateBottomNavigationView(offset > currentOffset)
                currentOffset = offset
            }
        })
    }

    private fun setUp(categories: MutableList<CategoryBean>) {
        titles.clear()
        fragments.clear()
        categories.forEach {
            titles.add(it.name!!)
            fragments.add(SystemPagerFragment.instance(it.children))
        }
        viewPager.adapter = BaseFragmentPagerAdapter(
            childFragmentManager,
            fragments,
            titles
        )
        viewPager.offscreenPageLimit = titles.size
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun scrollToTop() {
        if (fragments.isEmpty() || viewPager == null) return
        fragments[viewPager.currentItem].scrollToTop()
    }
}