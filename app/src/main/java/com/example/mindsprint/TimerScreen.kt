package com.example.mindsprint

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class TimerScreen : AppCompatActivity() {

    private lateinit var timerInput: EditText
    private lateinit var timerDisplay: TextView
    private lateinit var startButton: Button
    private lateinit var resetButton: Button

    private var originalTimeInMillis: Long = 0
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        timerInput = findViewById(R.id.timerInput)
        timerDisplay = findViewById(R.id.timerDisplay)
        startButton = findViewById(R.id.startButton)
        resetButton = findViewById(R.id.resetButton)

        startButton.setOnClickListener {
            val timeText = timerInput.text.toString()
            originalTimeInMillis = parseTimeToMillis(timeText)
            if (originalTimeInMillis > 0) {
                startTimer(originalTimeInMillis)
            } else {
                Toast.makeText(this, "Enter a valid time!", Toast.LENGTH_SHORT).show()
            }
        }

        resetButton.setOnClickListener {
            timer?.cancel()
            timerDisplay.text = formatMillisToTime(originalTimeInMillis)
        }

        // 👉 Add BottomNavigation setup
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.nav_timer


        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeScreen::class.java))
                    true
                }
                R.id.nav_timer -> {
                    // Already in TimerScreen
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

    private fun startTimer(duration: Long) {
        timer?.cancel()
        timer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerDisplay.text = formatMillisToTime(millisUntilFinished)
            }

            override fun onFinish() {
                timerDisplay.text = "00:00:00"
                val intent = Intent(this@TimerScreen, BackToWork2::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }

    private fun parseTimeToMillis(time: String): Long {
        val parts = time.split(":")
        return try {
            val hours = parts.getOrNull(0)?.toIntOrNull() ?: 0
            val minutes = parts.getOrNull(1)?.toIntOrNull() ?: 0
            val seconds = parts.getOrNull(2)?.toIntOrNull() ?: 0
            ((hours * 3600 + minutes * 60 + seconds) * 1000).toLong()
        } catch (e: Exception) {
            0
        }
    }

    private fun formatMillisToTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
