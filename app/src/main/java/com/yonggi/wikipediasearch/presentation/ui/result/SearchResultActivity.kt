package com.yonggi.wikipediasearch.presentation.ui.result

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.yonggi.wikipediasearch.R
import com.yonggi.wikipediasearch.application.injectors.Injector
import com.yonggi.wikipediasearch.application.utils.Utils
import com.yonggi.wikipediasearch.databinding.ActivitySearchResultBinding
import com.yonggi.wikipediasearch.databinding.ViewHeaderBinding
import com.yonggi.wikipediasearch.domain.entity.Media
import com.yonggi.wikipediasearch.presentation.adapter.SearchResultAdapter
import com.yonggi.wikipediasearch.presentation.ui.BaseActivity
import com.yonggi.wikipediasearch.presentation.ui.detail.WebViewActivity

class SearchResultActivity : BaseActivity() {

    private val keyword by lazy { intent.getStringExtra("keyword").orEmpty() }
    private lateinit var uiObserver: SearchResultUiObserver

    private lateinit var binding: ActivitySearchResultBinding
    private lateinit var header: ViewHeaderBinding
    private lateinit var adapter: SearchResultAdapter
    private lateinit var model: SearchResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupViewModel()
        setupView()
        setupListeners()
        uiObserver = SearchResultUiObserver(
            owner = this,
            binding = binding,
            header = header,
            adapter = adapter,
            model = model
        )
        uiObserver.observeAll()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_result)
        header = ViewHeaderBinding.inflate(layoutInflater)
    }

    private fun setupViewModel() {
        val factory = SearchResultViewModelFactory(
            keyword,
            Injector.provideSearchUseCase(),
            Injector.provideRefreshUseCase(),
            Injector.provideBitmapUseCase()
        )
        model = ViewModelProvider(this, factory)[SearchResultViewModel::class.java]
    }

    private fun setupView() {
        binding.toolBar.keyword.text = keyword
        adapter = SearchResultAdapter(Injector.provideBitmapUseCase())
        binding.resultView.adapter = adapter
        binding.resultView.addHeaderView(header.root)
    }

    private fun setupListeners() {
        binding.toolBar.backBtn.setOnClickListener(this::pressedBackButton)
        binding.swipeLayout.setOnRefreshListener { model.swipeRefresh() }
        header.root.setOnClickListener { openWebView(keyword) }
        binding.resultView.setOnItemClickListener { _, _, position, _ -> onMediaItemClicked(position) }
    }

    private fun onMediaItemClicked(position: Int) {
        val media = adapter.getItem(position - 1) as Media
        val newKeyword = Utils.extract(media.caption)

        if (newKeyword == keyword || newKeyword.isEmpty()) {
            Toast.makeText(this, R.string.search_keyword_not_available, Toast.LENGTH_SHORT).show()
            return
        }

        startActivity(Intent(this, SearchResultActivity::class.java).apply {
            putExtra("keyword", newKeyword)
        })
    }

    private fun openWebView(keyword: String) {
        startActivity(Intent(this, WebViewActivity::class.java).apply {
            putExtra("keyword", keyword)
        })
    }
}