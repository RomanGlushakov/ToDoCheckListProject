package com.example.todochecklistproject.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todochecklistproject.R
import com.example.todochecklistproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ToDoItemFragment.OnEditFinishedListeners {

    private lateinit var viewModel: MainViewModel

    private lateinit var toDoAdapter: ToDoListAdapter


    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]




        addItemClickListener()
        initRecyclerView()
        setUpSwipeListener()
        setupLongClickListener()
        editItemClickListener()


        viewModel.toDoItemList.observe(this) {
            toDoAdapter.submitList(it)
        }

    }

    private fun launchFragmentContainer(fragment: ToDoItemFragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction().add(R.id.todo_item_container_main, fragment)
            .addToBackStack(null).commit()
    }

    private fun addItemClickListener() {
        binding.addItemButton.setOnClickListener {
            if (binding.todoItemContainerMain == null) {
                val intent = ToDoItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragmentContainer(ToDoItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun editItemClickListener() {
        toDoAdapter.setOnItemClickListener = {
            if (binding.todoItemContainerMain == null) {
                val intent = ToDoItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragmentContainer(ToDoItemFragment.newInstanceEditItem(it.id))
            }

        }
    }


    private fun initRecyclerView() {
        toDoAdapter = ToDoListAdapter()

        binding.rvTodoItems.adapter = toDoAdapter

        binding.rvTodoItems.recycledViewPool.setMaxRecycledViews(
            ToDoListAdapter.MODE_ENABLED, ToDoListAdapter.POOL_SIZE
        )
        binding.rvTodoItems.recycledViewPool.setMaxRecycledViews(
            ToDoListAdapter.MODE_DISABLED, ToDoListAdapter.POOL_SIZE
        )
    }

    private fun setUpSwipeListener() {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = toDoAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteItem(item)
            }

        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvTodoItems)
    }

    private fun setupLongClickListener() {
        toDoAdapter.setOnLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    override fun onEditFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
    }
}