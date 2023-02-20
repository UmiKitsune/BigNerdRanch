package com.example.geoquiz

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Crime(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(), //рандомный уникальный id
    var title: String = "",
    var date: Date = Date(),
    var isSolved: Boolean = false
)