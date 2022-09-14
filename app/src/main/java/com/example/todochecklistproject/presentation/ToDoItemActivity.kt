package com.example.todochecklistproject.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todochecklistproject.R
import com.example.todochecklistproject.domain.TodoItem

class ToDoItemActivity : AppCompatActivity(), ToDoItemFragment.OnEditFinishedListeners {

    private var screenMode = UNKNOWN_MODE
    private var toDoItemId = TodoItem.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_item)
        parseIntent()

        if (savedInstanceState == null) {
            launchScreenMode()
        }

    }


    private fun launchScreenMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> ToDoItemFragment.newInstanceEditItem(toDoItemId)
            MODE_ADD -> ToDoItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }

        supportFragmentManager.beginTransaction().replace(R.id.todo_item_container, fragment)
            .commit()

    }


    private fun parseIntent() {
        if (!intent.hasExtra(SCREEN_MODE)) {
            throw RuntimeException("Screen Mode is absent")
        }
        val mode = intent.getStringExtra(SCREEN_MODE)

        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode: $mode")
        }
        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(ITEM_ID)) {
                throw RuntimeException("Unknown screen mode: $mode")
            }
            toDoItemId = intent.getIntExtra(ITEM_ID, TodoItem.UNDEFINED_ID)
        }
    }


    companion object {

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ToDoItemActivity::class.java)
            intent.putExtra(SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, itemId: Int): Intent {
            val intent = Intent(context, ToDoItemActivity::class.java)
            intent.putExtra(SCREEN_MODE, MODE_EDIT)
            intent.putExtra(ITEM_ID, itemId)
            return intent
        }

        private const val SCREEN_MODE = "screen mode"
        private const val MODE_ADD = "add"
        private const val MODE_EDIT = "edit"
        private const val ITEM_ID = "shopItemId"

        private const val UNKNOWN_MODE = ""


    }

    override fun onEditFinished() {
        finish()
    }
}