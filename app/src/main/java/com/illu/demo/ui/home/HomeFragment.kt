package com.illu.demo.ui.home

import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.illu.demo.base.BaseVmFragment
import com.illu.demo.R
import com.illu.demo.base.BaseFragmentPagerAdapter
import com.illu.demo.ui.MainActivity
import com.illu.demo.ui.home.gzh.GzhFragment
import com.illu.demo.ui.home.hot.HotFragment
import com.illu.demo.ui.home.newest.NewestFragment
import com.illu.demo.ui.home.project.ProjectFragment
import com.illu.demo.ui.home.square.SquareFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseVmFragment<HomeViewModel>() {

    private lateinit var fragments: List<Fragment>
    private var currentOffset = 0

    companion object {
        fun INSTANCE() = HomeFragment()
    }

    override fun viewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
        fragments = listOf(
            HotFragment.INSTANCE(),
            NewestFragment.INSTANCE(),
            SquareFragment.INSTANCE(),
            ProjectFragment.INSTANCE(),
            GzhFragment.INSTANCE()
        )

        val titles = listOf("热门", "最新", "广场", "项目", "公众号")
        vp.adapter = BaseFragmentPagerAdapter(
            fm = childFragmentManager,
            fragments = fragments,
            titles = titles
        )
        vp.offscreenPageLimit = fragments.size
        tabLayout.setupWithViewPager(vp)

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (activity is MainActivity && this.currentOffset != verticalOffset) {
                currentOffset = verticalOffset
            }
        })
    }

}