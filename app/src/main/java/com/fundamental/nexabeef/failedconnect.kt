package com.fundamental.nexabeef


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class failedconnect : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var tryagain1 : Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_failedconnect)


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

        tryagain1 = findViewById(R.id.tryagain)
        tryagain1.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


    }
}