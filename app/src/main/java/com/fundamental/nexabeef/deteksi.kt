package com.fundamental.nexabeef

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class deteksi : AppCompatActivity() {


    private lateinit var bottomNavigationView: BottomNavigationView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deteksi)




        //open bottom navigation
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.pasar -> {
                    startActivity(Intent(this, MainActivity::class.java))
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



    }
}