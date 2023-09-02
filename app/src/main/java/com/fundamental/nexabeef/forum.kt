package com.fundamental.nexabeef

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class forum : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var webView: WebView

    private var isPageFinished = false
    private var isTouchDisabled = false

    private var isProgressBarVisible = false
    private lateinit var progressbar: ProgressBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum)




        //progressbar
        progressbar = findViewById(R.id.progressBar)

        //open bottom navigation
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.pasar -> {

                    true
                }
                R.id.tools -> {
                    startActivity(Intent(this, tools::class.java))
                    finish()
                    true
                }
                R.id.deteksi -> {
                    startActivity(Intent(this, deteksi::class.java))
                    finish()
                    true
                }
                R.id.forum -> {
                    startActivity(Intent(this, forum::class.java))
                    finish()
                    true
                }
                R.id.akun -> {
                    startActivity(Intent(this, akun::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
        // close bottom navigation

        //open webview
        // Initialize WebView
        webView = findViewById(R.id.webviewforum)
        webView.settings.javaScriptEnabled = true // Enable JavaScript

        webView.loadUrl("https://herdo226042.000webhostapp.com/forum.php")
        // Disable zooming
        webView.settings.setSupportZoom(false)
        webView.settings.builtInZoomControls = false
        webView.settings.displayZoomControls = false
        webView.settings.textZoom = 100 // Set zoom level to 100%
        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false

        // Prevent external browser from opening
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                showProgressBar()
                url?.let { webView.loadUrl(it) }
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // Show ProgressBar when page starts loading
                isPageFinished = false
                isTouchDisabled = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Hide ProgressBar after page finishes loading
                hideProgressBar()
                if (!isPageFinished) {
                    progressbar.visibility = View.INVISIBLE
                    isPageFinished = true
                    isTouchDisabled = false
                }
            }

            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                // Handle error when WebView fails to load or connect to the server
                startActivity(Intent(this@forum, failedconnect::class.java))
                finish()
            }
        }

        // Disable touch events on WebView during loading
        webView.setOnTouchListener { _, _ ->
            isTouchDisabled
        }


        //close webview




    }

    private fun showProgressBar() {
        progressbar.visibility = View.VISIBLE
        isProgressBarVisible = true
        // Disable touch events on WebView when ProgressBar is visible
        webView.setOnTouchListener { _, _ -> true }
    }

    private fun hideProgressBar() {
        progressbar.visibility = View.INVISIBLE
        isProgressBarVisible = false
        // Enable touch events on WebView again after hiding ProgressBar
        webView.setOnTouchListener(null)
    }
}