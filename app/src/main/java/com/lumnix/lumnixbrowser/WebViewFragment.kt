package com.lumnix.lumnixbrowser

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.victor.loading.rotate.RotateLoading
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.web_view_fragment.*


class WebViewFragment:Fragment() {

    companion object{
        fun newInstance():WebViewFragment {
            return WebViewFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.web_view_fragment,container,false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val urlBar = activity?.urlBar
        val url = urlBar!!.text.toString()
        val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        webHolder.webViewClient = WebHolderClass(urlBar, progressBar)
        webHolder.settings.javaScriptEnabled = true
        webHolder.canGoBack()
        webHolder.settings.allowContentAccess = true

        if (url != "about:blank" || url != ""){
            when {
                url.startsWith("https://",false) -> {
                    webHolder.loadUrl(url)
                }
                url.startsWith("www.",false) -> {
                    val searchUrl = "https://${url}"
                    webHolder.loadUrl(searchUrl)
                }
                else -> {
                    val searchUrl = "https://www.google.com/search?q=${url}"
                    webHolder.loadUrl(searchUrl)
                }
            }
        }

        webHolder.requestFocus()
        imm.hideSoftInputFromWindow(view!!.windowToken,0) // this will hide keyboard if open
    }

    private  class WebHolderClass(searchField : EditText,progress:RotateLoading): WebViewClient(){
        var searchFieldUpdater = searchField
        var progressBar = progress

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            view?.loadUrl(request?.url.toString())
            searchFieldUpdater.setText(request?.url.toString())
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            searchFieldUpdater.setText(url)
            progressBar.start()
        }
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar.stop()
        }
    }
}