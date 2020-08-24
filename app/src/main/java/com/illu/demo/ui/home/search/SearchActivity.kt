package com.illu.demo.ui.home.search

import android.view.inputmethod.EditorInfo
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import com.illu.baselibrary.core.ActivityHelper
import com.illu.baselibrary.ext.hideSoftInput
import com.illu.baselibrary.utils.LogUtil
import com.illu.demo.R
import com.illu.demo.base.BaseVmActivity
import com.illu.demo.ui.home.search.history.HistoryFragment
import com.illu.demo.ui.home.search.result.ResultFragment
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseVmActivity<SearchViewModel>() {

    companion object {

        fun instance(): SearchActivity {
            return SearchActivity()
        }
    }

    private val historyFragment by lazy { HistoryFragment.instance() }
    private val resultFragment by lazy { ResultFragment.instance() }

    override fun viewModelClass(): Class<SearchViewModel> = SearchViewModel::class.java

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun initView() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, historyFragment)
            .add(R.id.container, resultFragment)
            .show(historyFragment)
            .hide(resultFragment)
            .commit()

        imgBack.setOnClickListener {
            if (resultFragment.isVisible) {
                supportFragmentManager.beginTransaction()
                    .hide(resultFragment)
                    .commit()
            } else ActivityHelper.finish(SearchActivity::class.java)
        }
        imgSearch.setOnClickListener {
            val text = etInput.text.toString().trim()
            if (text.isNotEmpty()) {
                resultFragment.doSearch(text)
                supportFragmentManager.beginTransaction()
                    .show(resultFragment)
                    .commit()
                it.hideSoftInput()
                historyFragment.addHistory(text)
            }
        }
        imgClear.setOnClickListener {
            etInput.setText("")
        }
        etInput.run {
            addTextChangedListener(
                onTextChanged = { text, _, _, _ ->
                    imgClear.isGone = text.isNullOrEmpty()
                }
            )
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    imgSearch.performClick()
                    true
                } else false
            }
        }
    }

    fun fillInputText(content: String) {
        etInput.setText("")
        etInput.setText(content)
        etInput.setSelection(content.length)
    }

    override fun onBackPressed() {
        imgBack.performClick()
    }

    override fun onPause() {
        super.onPause()
        LogUtil.d("onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.d("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d("onDestroy")
    }
}