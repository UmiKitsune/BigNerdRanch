package com.example.geoquiz

import java.util.*

data class Crime(
    val id: UUID = UUID.randomUUID(), //рандомный уникальный id
    var title: String = "",
    var date: Date = Date(),
    var isSolved: Boolean = false
)