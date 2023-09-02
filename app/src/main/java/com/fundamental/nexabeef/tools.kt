package com.fundamental.nexabeef


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class tools : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tools)


        //open bottom navigation
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.pasar -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }

                R.id.tools -> {

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


        val buttonkerugianberat = findViewById<Button>(R.id.kerugianberat)
        buttonkerugianberat.setOnClickListener {
            startActivity(Intent(this, toolskerugianberat::class.java))
        }

        val buttonmasasimpan = findViewById<Button>(R.id.masasimpan)
        buttonmasasimpan.setOnClickListener {
            Toast.makeText(this@tools, "Masih dalam pengembangan", Toast.LENGTH_SHORT).show()
        }


    }
}