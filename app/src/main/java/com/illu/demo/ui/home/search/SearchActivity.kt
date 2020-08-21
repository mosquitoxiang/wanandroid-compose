package com.illu.demo.ui.home.search

import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import com.illu.demo.R
import com.illu.demo.base.BaseVmActivity
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseVmActivity<SearchViewModel>() {

    companion object {

        fun instance(): SearchActivity {
            return SearchActivity()
        }
    }

    override fun viewModelClass(): Class<SearchViewModel> = SearchViewModel::class.java

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun initView() {
        imgBack.setOnClickListener {
            finish()
        }
        imgSearch.setOnClickListener {

        }
        imgClear.setOnClickListener {

        }
        etInput.addTextChangedListener(
            onTextChanged = { text, _, _, _ ->
                imgClear.isGone = text.isNullOrEmpty()
            }
        )
    }

}