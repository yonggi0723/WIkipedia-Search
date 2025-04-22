package com.yonggi.wikipediasearch.presentation.ui.detail

import android.os.Bundle
import android.text.Html
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.yonggi.wikipediasearch.R
import com.yonggi.wikipediasearch.databinding.ActivityWebviewBinding
import com.yonggi.wikipediasearch.presentation.ui.BaseActivity

class WebViewActivity: BaseActivity() {

    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview)
        setContentView(binding.root)

        val keyword = intent.getStringExtra("keyword") ?: return
        binding.toolBar.keyword.text = keyword

        webViewSetting(keyword =  keyword)
        binding.toolBar.backBtn.setOnClickListener(this::pressedBackButton)
    }

    // 해당 코드 XSS, DriverByDownload 등 보안 취약 관련 방어 필요
    private fun webViewSetting(keyword: String) {
        binding.webView.webViewClient = client
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.allowFileAccess = false
        binding.webView.settings.allowContentAccess = false
        WebView.setWebContentsDebuggingEnabled(false)
        binding.webView.settings.javaScriptCanOpenWindowsAutomatically = false
        binding.webView.loadUrl(Html.escapeHtml("https://en.wikipedia.org/api/rest_v1/page/html/$keyword"))
    }

    val client = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            view?.loadUrl(request?.url.toString())
            // 새창을 띄우는 걸 허락 하지 않음
            return true
        }
    }

}