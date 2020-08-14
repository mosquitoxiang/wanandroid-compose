package com.illu.demo.ui.home.square

import androidx.lifecycle.Observer
import com.illu.demo.R
import com.illu.demo.base.BaseVmFragment
import kotlinx.android.synthetic.main.fragment_square.*

class SquareFragment : BaseVmFragment<SquareViewModel>() {

    private lateinit var mAdapter: SquareAdapter

    companion object {
        fun instance() = SquareFragment()
    }

    override fun viewModelClass(): Class<SquareViewModel> = SquareViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_square

    override fun initView() {
        mAdapter = SquareAdapter()
    }

    override fun lazyLoadData() {
        mViewModel.getSquareData()
    }

    override fun observe() {
        mViewModel.run {
            sqareDataList.observe(viewLifecycleOwner, Observer {
                mAdapter.setNewData(it)
            })
            refrestStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
        }
    }
}