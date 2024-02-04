package com.fundamental.nexabeef

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.webkit.WebChromeClient
import android.graphics.Bitmap
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.JsResult
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar


    private var isPageFinished = false
    private var isTouchDisabled = false

    private var isProgressBarVisible = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //check connection
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {

        } else {
            showNoInternetDialog()
        }















        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(true)

        actionBar?.title = "NEXABITH" // Ganti dengan judul yang Anda inginkan



        //progressbar
        progressBar = findViewById(R.id.progressBar)

        //open bottom navigation
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.pasar -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }


                R.id.scan -> {
                    startActivity(Intent(this, BarcodeScannerActivity::class.java))
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
        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true // Enable JavaScript

        webView.loadUrl("https://www.nexabeef.com/market.php")
        // Disable zooming
        webView.settings.setSupportZoom(false)
        webView.settings.builtInZoomControls = false
        webView.settings.displayZoomControls = false
        webView.settings.textZoom = 100 // Set zoom level to 100%
        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false

        webView.setOnCreateContextMenuListener { _, _, _ -> }
        webView.setOnLongClickListener { true }





        webView.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                // Tangani pesan alert di sini
                // Tampilkan pesan menggunakan AlertDialog atau cara lain
                val alertDialog = AlertDialog.Builder(this@MainActivity, com.google.android.material.R.style.Base_Theme_Material3_Dark_Dialog)
                val message1 = "login dulu ya di menu Account"
                alertDialog.setMessage(message1)
                alertDialog.setPositiveButton("OK") { _, _ ->
                    result?.confirm()
                }
                alertDialog.setCancelable(false)
                alertDialog.create().show()
                return true
            }
        }



        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("LOAD DATA..")
        progressDialog.setCancelable(false)
        progressDialog.show()






        // Prevent external browser from opening
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {



                if (url?.startsWith("android-app://com.fundamental.nexabeef/forcompany") == true){
                    val intent = Intent(this@MainActivity, forcompany::class.java)
                    startActivity(intent)
                    return true // Menyatakan bahwa URL telah ditangani

                }else if (url?.startsWith("android-app://com.fundamental.nexabeef/nexadata") == true){
                    val intent = Intent(this@MainActivity, nexadata::class.java)
                    startActivity(intent)
                    return true // Menyatakan bahwa URL telah ditangani

                }else if(url?.startsWith("android-app://com.fundamental.nexabeef/produk") == true){
                    val intent = Intent(this@MainActivity, produk::class.java)
                    startActivity(intent)
                    return true // Menyatakan bahwa URL telah ditangani

                }else{
                    showProgressBar()
                    url?.let { webView.loadUrl(it) }
                    return false // URL lain dimuat dalam WebView
                }
            }



            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // Show ProgressBar when page starts loading
                // Menampilkan dialog pop-up loading



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

                startActivity(Intent(this@MainActivity, failedconnect::class.java))

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


    /*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
     */

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.transaction -> {
                //startActivity(Intent(this, transaksi::class.java))
                finish()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            showExitConfirmationDialog()
        }
    }

    private fun showExitConfirmationDialog() {
        val message = "Apakah Anda yakin ingin keluar?"

        // Buat objek SpannableString untuk mengatur warna teks
        val spannableMessage = SpannableString(message)

        // Tentukan warna hitam (Color.BLACK) untuk seluruh teks dalam pesan
        spannableMessage.setSpan(
            ForegroundColorSpan(Color.BLACK),
            0, // Indeks awal teks (mulai dari karakter pertama)
            message.length, // Indeks akhir teks (sampai karakter terakhir)
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Konfirmasi")
        alertDialog.setMessage(spannableMessage)
        alertDialog.setPositiveButton("Ya") { _, _ ->
            finishAffinity()
            super.onBackPressed()
        }
        alertDialog.setNegativeButton("Tidak") { _, _ ->
            // Tindakan yang diambil jika tombol "Tidak" ditekan
        }
        alertDialog.show()
    }





    private fun showNoInternetDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("No Internet Connection")
            .setMessage("Please check your internet connection and try again.")
            .setPositiveButton("EXIT") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
                finishAffinity()
            }
            .setCancelable(false)
            .create()
            .show()
    }






}