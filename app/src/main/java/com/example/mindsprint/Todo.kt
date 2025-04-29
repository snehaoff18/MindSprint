package com.example.mindsprint

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Todo : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonClear: Button
    private lateinit var adapter: TodoAdapter
    private val todoList = mutableListOf<TodoItem>()
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        editText = findViewById(R.id.editTextTask)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonClear = findViewById(R.id.buttonclear)
        recyclerView = findViewById(R.id.recyclerView)
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        adapter = TodoAdapter(todoList)
        recyclerView.layoutManager = LinearLayoutManager(this@Todo)
        recyclerView.adapter = adapter

        buttonAdd.setOnClickListener {
            val task = editText.text.toString()
            if (task.isNotBlank()) {
                todoList.add(TodoItem(task))
                adapter.notifyItemInserted(todoList.size - 1)
                editText.text.clear()
            }
        }

        buttonClear.setOnClickListener {
            clearAllNotes()
        }

        // ✅ BottomNavigationView logic added here
        bottomNavigationView.selectedItemId = R.id.nav_todo // Set current tab

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
                    // Already here
                    true
                }
                R.id.nav_notes -> {
                    startActivity(Intent(this, Notes::class.java)) // Adjust if needed
                    true
                }
                else -> false
            }
        }
    }

    private fun clearAllNotes() {
        todoList.clear()
        adapter.notifyDataSetChanged()

        val sharedPreferences = getSharedPreferences("notes", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
