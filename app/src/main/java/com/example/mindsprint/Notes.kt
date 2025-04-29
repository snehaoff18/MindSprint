package com.example.mindsprint

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Notes : AppCompatActivity() {

    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var noteInput: EditText
    private lateinit var addNoteButton: Button
    private lateinit var clearNotesButton: Button
    private lateinit var bottomNavigationView: BottomNavigationView

    private val notesList = mutableListOf<Note>()
    private lateinit var notesAdapter: NotesAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        // Find views
        notesRecyclerView = findViewById(R.id.notesRecyclerView)
        noteInput = findViewById(R.id.noteInput)
        addNoteButton = findViewById(R.id.addNoteButton)
        clearNotesButton = findViewById(R.id.clear_text)
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // Initialize RecyclerView
        notesAdapter = NotesAdapter(notesList)
        notesRecyclerView.layoutManager = LinearLayoutManager(this)
        notesRecyclerView.adapter = notesAdapter

        // Load stored notes
        loadNotes()

        // Handle Add note button
        addNoteButton.setOnClickListener {
            val noteContent = noteInput.text.toString().trim()
            if (noteContent.isNotBlank()) {
                val note = Note.createNote(noteContent, notesList.size + 1)
                addNewNote(note)
                noteInput.text.clear()
            }
        }

        // Handle Clear notes button
        clearNotesButton.setOnClickListener {
            clearAllNotes()
        }

        // 🧭 Handle Bottom Navigation
        bottomNavigationView.selectedItemId = R.id.nav_notes // Set currently selected
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeScreen::class.java))
                    true
                }
                R.id.nav_timer -> {
                    startActivity(Intent(this, TimerScreen::class.java)) // (Fix here if wrong)
                    true
                }
                R.id.nav_todo -> {
                    startActivity(Intent(this, Todo::class.java))
                    true
                }
                R.id.nav_notes -> {
                    // Already here
                    true
                }

                else -> false
            }
        }
    }

    private fun addNewNote(note: Note) {
        notesList.add(note)
        notesAdapter.notifyItemInserted(notesList.size - 1)
        saveNotes()
    }

    private fun saveNotes() {
        val sharedPreferences = getSharedPreferences("notes", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val notesJson = Gson().toJson(notesList)
        editor.putString("notesList", notesJson)
        editor.apply()
    }

    private fun loadNotes() {
        val sharedPreferences = getSharedPreferences("notes", MODE_PRIVATE)
        val notesJson = sharedPreferences.getString("notesList", null)

        if (notesJson != null) {
            val notesType = object : TypeToken<List<Note>>() {}.type
            val loadedNotes = Gson().fromJson<List<Note>>(notesJson, notesType)
            notesList.addAll(loadedNotes)
            notesAdapter.notifyDataSetChanged()
        }
    }

    private fun clearAllNotes() {
        notesList.clear()
        notesAdapter.notifyDataSetChanged()

        val sharedPreferences = getSharedPreferences("notes", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
    }
}