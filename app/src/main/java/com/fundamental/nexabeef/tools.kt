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