package com.fundamental.nexabeef

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

@Suppress("DEPRECATION")
class forcompany : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    private var isPageFinished = false
    private var isTouchDisabled = false

    private var isProgressBarVisible = false
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forcompany)



        val progressDialog = ProgressDialog(this@forcompany)
        progressDialog.setMessage("LOAD DATA..")
        progressDialog.setCancelable(false)
        progressDialog.show()







        //progressbar
        progressBar = findViewById(R.id.progressBar)



        //open webview
        // Initialize WebView
        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true // Enable JavaScript

        webView.loadUrl("https://www.nexabeef.com/forcompany.php")
        // Disable zooming
        webView.settings.setSupportZoom(false)
        webView.settings.builtInZoomControls = false
        webView.settings.displayZoomControls = false
        webView.settings.textZoom = 100 // Set zoom level to 100%
        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false

        webView.setOnLongClickListener { false }

        // Prevent external browser from opening
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {


                if (url?.startsWith("android-app://com.fundamental.nexabeef/forcompany?") == true){
                    val info1 = Uri.parse(url).getQueryParameter("info1")

                    val phoneNumber = "$info1"
                    val uri = Uri.parse("https://api.whatsapp.com/send/?phone=$phoneNumber")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                    return true // Menyatakan bahwa URL telah ditangani
                }else if(url?.startsWith("android-app://com.fundamental.nexabeef/back") == true){

                    val intent = Intent(this@forcompany, MainActivity::class.java)
                    startActivity(intent)
                    return true // Menyatakan bahwa URL telah ditangani
                }else{
                    showProgressBar()
                    url?.let { webView.loadUrl(it) }
                    return false
                }

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
                progressDialog.dismiss()
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
                startActivity(Intent(this@forcompany, failedconnect::class.java))
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




    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }



}