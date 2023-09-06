package com.fundamental.nexabeef

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class akun : AppCompatActivity() {


    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    private var isPageFinished = false
    private var isTouchDisabled = false

    private var isProgressBarVisible = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_akun)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profil"

        val upArrow = ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //progressbar
        progressBar = findViewById(R.id.progressBar)



        //open webview
        // Initialize WebView
        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true // Enable JavaScript

        webView.loadUrl("https://nexabeef.000webhostapp.com/profil.php")
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
                    progressBar.visibility = View.INVISIBLE
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
                startActivity(Intent(this@akun, failedconnect::class.java))
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
        progressBar.visibility = View.VISIBLE
        isProgressBarVisible = true
        // Disable touch events on WebView when ProgressBar is visible
        webView.setOnTouchListener { _, _ -> true }
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
        isProgressBarVisible = false
        // Enable touch events on WebView again after hiding ProgressBar
        webView.setOnTouchListener(null)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this, MainActivity::class.java))
                return true
            }
            // Item menu lainnya jika ada
            else -> return super.onOptionsItemSelected(item)
        }
    }




}