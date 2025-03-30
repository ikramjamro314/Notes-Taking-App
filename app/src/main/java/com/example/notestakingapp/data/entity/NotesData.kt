package com.example.notestakingapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity
data class NotesData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String = "untitled",
    val note: String,
    val noteDate: String = "${
        LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }"
)
