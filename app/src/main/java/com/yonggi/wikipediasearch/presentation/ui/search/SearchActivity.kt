package com.yonggi.wikipediasearch.presentation.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.yonggi.wikipediasearch.R
import com.yonggi.wikipediasearch.databinding.ActivitySearchBinding
import com.yonggi.wikipediasearch.presentation.ui.BaseActivity
import com.yonggi.wikipediasearch.presentation.ui.result.SearchResultActivity
import java.net.URLEncoder


class SearchActivity: BaseActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        init()
    }

    private fun init() {
        binding.searchEt.setOnEditorActionListener(this::editorActionListener)
        binding.searchBtn.setOnClickListener(this::pressedSearchButton)
    }

    /**
     * 검색어가 URL 의 Keyword에 사용되므로 관련 보안 사항 지켜야함
     */
    private fun pressedSearchButton(view: View) {
        val keyword = binding.searchEt.text.toString().trim()
        val regex_keyword = keyword.replace(Regex("[^\\w\\s]"),"")
        val safeKeyword = URLEncoder.encode(regex_keyword, "UTF-8")

        var intent = Intent(this, SearchResultActivity::class.java)
        intent.putExtra("keyword", safeKeyword)
        startActivity(intent)
    }


    private fun editorActionListener(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
            actionId == EditorInfo.IME_ACTION_DONE ||
            (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
            pressedSearchButton(v)
            return true
        }
        return false
    }

}