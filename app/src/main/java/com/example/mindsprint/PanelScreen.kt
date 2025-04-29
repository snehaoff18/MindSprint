package com.example.mindsprint

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity


class PanelScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)  // Correct layout here!

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeScreen::class.java))
                    true
                }
                R.id.nav_timer -> {
                    startActivity(Intent(this, TimerScreen::class.java))
                    true
                }
                R.id.nav_todo -> {
                    startActivity(Intent(this, Todo::class.java))
                    true
                }
                R.id.nav_notes -> {
                    startActivity(Intent(this, Notes::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
