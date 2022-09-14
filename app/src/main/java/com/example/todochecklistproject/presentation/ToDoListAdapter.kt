package com.example.todochecklistproject.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.todochecklistproject.R
import com.example.todochecklistproject.databinding.ItemTodoDisabledBinding
import com.example.todochecklistproject.databinding.ItemTodoEnabledBinding
import com.example.todochecklistproject.domain.TodoItem

class ToDoListAdapter : ListAdapter<TodoItem, ToDoListViewHolder>(ToDoItemDiffCallback()) {

    var setOnLongClickListener: ((TodoItem) -> Unit)? = null
    var setOnItemClickListener: ((TodoItem) -> Unit)? = null


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ToDoListViewHolder {
        val layout = when (viewType) {
            MODE_ENABLED -> R.layout.item_todo_enabled
            MODE_DISABLED -> R.layout.item_todo_disabled
            else -> throw RuntimeException("Unknown ViewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), layout, parent, false)

        return ToDoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        val item = getItem(position)

        val binding = holder.binding

        binding.root.setOnClickListener {
            setOnItemClickListener?.invoke(item)
        }


        when (binding) {
            is ItemTodoEnabledBinding -> {
                binding.toDoItem = item
                binding.root.setOnLongClickListener {
                    setOnLongClickListener?.invoke(item)
                    Log.d("ToDoListAdapter", it.toString())
                    true
                }

            }
            is ItemTodoDisabledBinding -> {
                binding.toDoItem = item
                binding.root.setOnLongClickListener {
                    setOnLongClickListener?.invoke(item)
                    Log.d("ToDoListAdapter", it.toString())
                    true
                }

            }
        }

    }


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)

        return if (item.enabled) {
            MODE_ENABLED
        }
        else {
            MODE_DISABLED
        }
    }


    companion object {
         const val MODE_ENABLED = 100
         const val MODE_DISABLED = 101
         const val POOL_SIZE = 20

    }
}