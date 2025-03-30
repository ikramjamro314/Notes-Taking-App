package com.example.notestakingapp

import androidx.room.Database
import com.example.notestakingapp.data.database.NotesDao
import com.example.notestakingapp.data.entity.NotesData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class NotesRepository(private val notesDao: NotesDao) {
   suspend fun insert(noteData: NotesData) = notesDao.upsert(noteData)
   fun getAllNotes(): Flow<List<NotesData>> = notesDao.getAllNotes().onEach { note -> }
   suspend fun deleteNode(note: NotesData) = notesDao.delete(note)
 }