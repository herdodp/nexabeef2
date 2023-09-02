package com.fundamental.nexabeef

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

@Suppress("DEPRECATION")
class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Toast.makeText(this@splashscreen, "Checking connection", Toast.LENGTH_SHORT).show()

        //hide
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        //check connection
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo



        if (networkInfo != null && networkInfo.isConnected) {
            //if (email != null && token != null) {
            // Proses splashscreen berpindah ke halaman lain setelah 2 detik
            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 3000)
        } else {
            showNoInternetDialog()
        }



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