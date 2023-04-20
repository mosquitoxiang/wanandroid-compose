//package com.illu.demo.ui.find
//
//import android.view.View
//import androidx.core.view.isGone
//import androidx.core.view.isVisible
//import androidx.lifecycle.Observer
//import com.illu.baselibrary.core.ActivityHelper
//import com.illu.demo.base.BaseVmFragment
//import com.illu.demo.R
//import com.illu.demo.bean.ArticleBean
//import com.illu.demo.ui.MainActivity
//import com.illu.demo.ui.find.share.ShareActivity
//import com.illu.demo.ui.web.WebActivity
//import com.youth.banner.indicator.CircleIndicator
//import com.youth.banner.util.BannerUtils
//
//class FindFragment : BaseVmFragment<FindViewModel>() {
//
//    companion object {
//        fun instance() = FindFragment()
//    }
//
//    private lateinit var mHotKeyAdapter: HotKeyAdapter
//
//    override fun viewModelClass(): Class<FindViewModel> = FindViewModel::class.java
//
//    override fun getLayoutId(): Int = R.layout.fragment_find
//
//    override fun lazyLoadData() {
//        mViewModel.refreshData()
//    }
//
//    override fun initView() {
//        bannerView.addBannerLifecycleObserver(this)
//        mHotKeyAdapter = HotKeyAdapter().apply {
//            bindToRecyclerView(rvHotWord)
//            setOnItemClickListener { _, _, position ->
////                val article = mHotKeyAdapter.data[position]
////                ActivityHelper.start(
////                    WebActivity::class.java,
////                    mapOf(WebActivity.ARTICLE_DATA to article)
////                )
//            }
//        }
//        nestedScollView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
//            if (activity is MainActivity && scrollY != oldScrollY) {
//                (activity as MainActivity).animateBottomNavigationView(scrollY < oldScrollY)
//            }
//        }
//        tb.setOnTitleBarListener(this)
//    }
//
//    override fun observe() {
//        super.observe()
//        mViewModel.run {
//            reloadStatus.observe(viewLifecycleOwner, Observer {
//                reloadView.isVisible = it
//            })
//            refreshStatus.observe(viewLifecycleOwner, Observer {
//                swipeRefreshLayout.isRefreshing = it
//            })
//            bannerList.observe(viewLifecycleOwner, Observer {
//                setBanner(it)
//            })
//            hotKeyList.observe(viewLifecycleOwner, Observer {
//                tvHotWordTitle.isGone = it.isEmpty()
//                mHotKeyAdapter.setNewData(it)
//            })
//            commonWebList.observe(viewLifecycleOwner, Observer {
//                tvFrquently.isGone = it.isEmpty()
//                tagFlowLayout.adapter = TagAdapter(it)
//                tagFlowLayout.setOnTagClickListener { _, position, _ ->
//                    val article = it[position]
//                    ActivityHelper.start(
//                        WebActivity::class.java,
//                        mapOf(
//                            WebActivity.ARTICLE_DATA to ArticleBean
//                                (
//                                title = article.name,
//                                link = article.link
//                            )
//                        )
//                    )
//                    false
//                }
//            })
//        }
//        swipeRefreshLayout.apply {
//            setColorSchemeResources(R.color.textColorPrimary)
//            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
//            setOnRefreshListener { mViewModel.refreshData() }
//        }
//    }
//
//    private fun setBanner(banners: List<Banner>) {
//        bannerView.run {
//            adapter = ImageAdapter(banners)
//            setIndicator(CircleIndicator(requireContext()))
//            setIndicatorSpace(BannerUtils.dp2px(4F).toInt())
//            setIndicatorRadius(0)
//        }
//    }
//
//    override fun onLeftClick(v: View?) {
//        checkLogin{
//            ActivityHelper.start(ShareActivity::class.java)
//        }
//    }
//}