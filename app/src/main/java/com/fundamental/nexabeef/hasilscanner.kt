package com.fundamental.nexabeef

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast

class hasilscanner : AppCompatActivity() {

    private lateinit var webjson: WebView
    private lateinit var progressBar: ProgressBar


    private var isPageFinished = false
    private var isTouchDisabled = false

    private var isProgressBarVisible = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasilscanner)

        //progressbar
        progressBar = findViewById(R.id.progressBar)

        val progressDialog = ProgressDialog(this@hasilscanner)
        progressDialog.setMessage("LOAD DATA..")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val intent = intent
        val barcodeResult = intent.getStringExtra("barcode_result")
        val countview = intent.getStringExtra("countview")




        //open webjson
        // Initialize WebView
        webjson = findViewById(R.id.webjson)
        webjson.settings.javaScriptEnabled = true // Enable JavaScript

        webjson.loadUrl("https://nexabeef.com/productjson.php?kodeproduk=$barcodeResult&countview=$countview")
        // Disable zooming
        webjson.settings.setSupportZoom(false)
        webjson.settings.builtInZoomControls = false
        webjson.settings.displayZoomControls = false
        webjson.settings.textZoom = 100 // Set zoom level to 100%
        webjson.isVerticalScrollBarEnabled = false
        webjson.isHorizontalScrollBarEnabled = false
        webjson.setOnLongClickListener { false }

        // Prevent external browser from opening
        webjson.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

                if (url?.startsWith("android-app://com.fundamental.nexabeef/nodata") == true) {
                    // Buka aktivitas baru dengan informasi yang diterima
                    val intent = Intent(this@hasilscanner, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@hasilscanner, "Data Produk tidak ditemukan", Toast.LENGTH_SHORT).show()
                    return true

                }else if(url?.startsWith("android-app://com.fundamental.nexabeef/back") == true){
                    val intent = Intent(this@hasilscanner, MainActivity::class.java)
                    startActivity(intent)
                    return true

                }else{
                    showProgressBar()
                    url?.let { webjson.loadUrl(it) }
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
                startActivity(Intent(this@hasilscanner, failedconnect::class.java))
                finish()
            }
        }

        // Disable touch events on WebView during loading
        webjson.setOnTouchListener { _, _ ->
            isTouchDisabled
        }
        //close webjson






















    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        isProgressBarVisible = true
        // Disable touch events on WebView when ProgressBar is visible
        webjson.setOnTouchListener { _, _ -> true }
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
        isProgressBarVisible = false
        // Enable touch events on WebView again after hiding ProgressBar
        webjson.setOnTouchListener(null)
    }


    override fun onBackPressed() {
        if (webjson.canGoBack()) {
            webjson.goBack()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}
