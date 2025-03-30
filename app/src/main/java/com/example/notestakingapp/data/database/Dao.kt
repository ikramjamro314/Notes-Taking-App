package com.example.notestakingapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.notestakingapp.data.entity.NotesData
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao{
    @Upsert
    suspend fun upsert(noteData: NotesData)

    @Query("SELECT * FROM NotesData")
     fun getAllNotes():Flow<List<NotesData>>

     @Delete
    suspend fun delete(noteData: NotesData)

}