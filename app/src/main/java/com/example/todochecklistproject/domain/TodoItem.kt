package com.example.todochecklistproject.domain

data class TodoItem

    (
    val name: String,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID
    ) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}