package com.example.praktikum4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_login = findViewById<ImageView>(R.id.button)
        val btn_regis = findViewById<ImageView>(R.id.button2)

        btn_login.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        btn_regis.setOnClickListener {
            startActivity(Intent())

        }
    }
}