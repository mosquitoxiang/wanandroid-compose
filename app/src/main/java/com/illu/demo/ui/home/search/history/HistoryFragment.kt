package com.illu.demo.ui.home.search.history

import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.illu.baselibrary.core.ActivityHelper
import com.illu.demo.R
import com.illu.demo.base.BaseVmFragment
import com.illu.demo.bean.ArticleBean
import com.illu.demo.common.room.History
import com.illu.demo.ui.find.HotKey
import com.illu.demo.ui.home.search.SearchActivity
import com.illu.demo.ui.web.WebActivity
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_find.*
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.item_hot_search.view.*

class HistoryFragment : BaseVmFragment<HistoryViewModel>() {

    companion object {
        fun instance(): HistoryFragment {
            return HistoryFragment()
        }
    }

    private lateinit var historyAdapter: HistoryAdapter

    override fun viewModelClass(): Class<HistoryViewModel> = HistoryViewModel::class.java

    override fun getLayoutId(): Int {
        return R.layout.fragment_history
    }

    override fun initView() {
        historyAdapter = HistoryAdapter().apply {
            bindToRecyclerView(rvSearchHistory)
            setOnItemClickListener { _, _, position ->
                (activity as SearchActivity).fillInputText(data[position].searchWord!!)
            }
            onDeleteCliciListener = {
                mViewModel.deleteHistory(data[it].searchWord!!)
            }
        }

    }

    override fun lazyLoadData() {
        mViewModel.getHotSearch()
        mViewModel.getHistory()
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            hotKeyList.observe(viewLifecycleOwner, Observer {
                tvHotSearch.isGone = it.isEmpty()
                setTagData(it)
            })
            historyList.observe(viewLifecycleOwner, Observer {
                historyAdapter.setNewData(it)
            })
        }
    }

    private fun setTagData(data: List<HotKey>) {
        tflHotSearch.run {
            adapter = object : TagAdapter<HotKey>(data) {
                override fun getView(parent: FlowLayout, position: Int, t: HotKey): View {
                    return LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_hot_search, parent, false)
                        .apply { tvTag.text = t.name }
                }
            }
            setOnTagClickListener { _, position, _ ->
                (activity as SearchActivity).fillInputText(data[position].name!!)
                true
            }
        }
    }

    fun addHistory(content: String) {
        mViewModel.addHistory(content)
    }
}