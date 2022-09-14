package com.example.todochecklistproject.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todochecklistproject.databinding.FragmentTodoItemBinding
import com.example.todochecklistproject.domain.TodoItem

class ToDoItemFragment : Fragment() {

    private lateinit var viewModel: ToDoItemViewModel

    private var screenMode = UNKNOWN_MODE
    private var toDoItemId = TodoItem.UNDEFINED_ID

    private var _binding: FragmentTodoItemBinding? = null
    private val binding: FragmentTodoItemBinding
        get() = _binding ?: throw RuntimeException("FragmentTodoItemBinding == null")


    private lateinit var onEditFinishedListeners: OnEditFinishedListeners

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditFinishedListeners) {
            onEditFinishedListeners = context
        } else {
            throw RuntimeException("Activity doesn't implement OnEditFinishedListeners")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParam()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ToDoItemViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        launchScreenMode()
        textChangedListener()
        observeViewModel()
    }


    private fun textChangedListener() {

        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetNameError()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun observeViewModel() {
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditFinishedListeners.onEditFinished()
        }
    }


    private fun launchScreenMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditItemMode()
            MODE_ADD -> launchAddItemMode()
        }
    }

    private fun launchAddItemMode() {
        binding.buttonSave.setOnClickListener {
            val name = binding.etName.text?.toString()
            viewModel.addToDoItem(name)
        }
    }

    private fun launchEditItemMode() {
        viewModel.getToDoItem(toDoItemId)
        binding.buttonSave.setOnClickListener {
            val name = binding.etName.text.toString()
            viewModel.editToDoItem(name)
        }
    }

    private fun parseParam() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Screen Mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)

        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode: $mode")
        }
        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(ITEM_ID)) {
                throw RuntimeException("Unknown screen mode: $mode")
            }
            toDoItemId = args.getInt(ITEM_ID, TodoItem.UNDEFINED_ID)
        }
    }


    interface OnEditFinishedListeners {
        fun onEditFinished()
    }


    companion object {

        fun newInstanceAddItem(): ToDoItemFragment {
            return ToDoItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(toDoItemId: Int): ToDoItemFragment {
            return ToDoItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(ITEM_ID, toDoItemId)
                }
            }
        }


        private const val SCREEN_MODE = "screen mode"
        private const val MODE_ADD = "add"
        private const val MODE_EDIT = "edit"
        private const val ITEM_ID = "shopItemId"
        private const val UNKNOWN_MODE = ""


    }

}