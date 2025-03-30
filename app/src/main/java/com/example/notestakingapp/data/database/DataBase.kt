package com.example.notestakingapp.data.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase
import com.example.notestakingapp.data.entity.NotesData

@Database(entities = [NotesData::class], version = 14, exportSchema = true)
abstract class DataBase: RoomDatabase() {
    abstract fun notesDao(): NotesDao
}