package com.illu.demo.ui.home

import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.illu.demo.base.BaseVmFragment
import com.illu.demo.R
import com.illu.demo.base.BaseFragmentPagerAdapter
import com.illu.demo.ui.MainActivity
import com.illu.demo.ui.home.gzh.GzhFragment
import com.illu.demo.ui.home.hot.HotFragment
import com.illu.demo.ui.home.project.ProjectFragment
import com.illu.demo.ui.home.square.SquareFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseVmFragment<HomeViewModel>() {

    private lateinit var fragments: List<Fragment>
    private var currentOffset = 0

    companion object {
        fun instance() = HomeFragment()
    }

    override fun viewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
        fragments = listOf(
            HotFragment.instance(),
            SquareFragment.instance(),
            ProjectFragment.instance(),
            GzhFragment.instance()
        )

        val titles = listOf(
            getString(R.string.hot),
            getString(R.string.square),
            getString(R.string.project),
            getString(R.string.gzh)
        )
        vp.adapter = BaseFragmentPagerAdapter(
            fm = childFragmentManager,
            fragments = fragments,
            titles = titles
        )
        vp.offscreenPageLimit = fragments.size
        tabLayout.setupWithViewPager(vp)

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            if (activity is MainActivity && this.currentOffset != verticalOffset) {
                (activity as MainActivity).animateBottomNavigationView(verticalOffset > currentOffset)
                currentOffset = verticalOffset
            }
        })
    }

}