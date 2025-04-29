package com.example.mindsprint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
data class TodoItem(var task: String, var isDone: Boolean = false)


class TodoAdapter(private val items: MutableList<TodoItem>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.checkboxTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = items[position]
        holder.checkBox.text = item.task
        holder.checkBox.isChecked = item.isDone

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isDone = isChecked
        }
    }

    override fun getItemCount(): Int = items.size
}
