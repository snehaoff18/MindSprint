package com.example.mindsprint

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Calendar
import kotlin.random.Random

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class BoxView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#44AB44")
        style = Paint.Style.FILL
        strokeWidth = 8f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val left = 100f
        val top = 200f
        val right = 1000f
        val bottom = 600f

        canvas.drawRect(left, top, right, bottom, paint)
    }
}

class HomeScreen : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var quoteTextView: TextView

    private var originalTimeInMillis: Long = 0
    private var timer: CountDownTimer? = null

    // List of quotes
    private val quotes = listOf(
        "Believe you can and you're halfway there.",
        "Don't watch the clock; do what it does. Keep going.",
        "Push yourself, because no one else is going to do it for you.",
        "Success is not final, failure is not fatal: it is the courage to continue that counts.",
        "Dream bigger. Do bigger.",
        "Work hard in silence, let success make the noise."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize calendar and quote text view
        calendarView = findViewById(R.id.calendarView)
        quoteTextView = findViewById(R.id.quotes)

        // Set current date on calendar view
        val currentDate = Calendar.getInstance().timeInMillis
        calendarView.setDate(currentDate, false, true)

        // Show random quote
        showRandomQuote()

        // Setup bottom navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.nav_home

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Already in home, maybe refresh or do nothing
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

    // Function to show a random quote
    private fun showRandomQuote() {
        val randomIndex = Random.nextInt(quotes.size)
        val randomQuote = quotes[randomIndex]
        quoteTextView.text = randomQuote
    }
}
