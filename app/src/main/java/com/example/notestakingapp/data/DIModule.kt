package com.example.notestakingapp.data

import android.app.Application
import androidx.room.Room
import com.example.notestakingapp.MIGRATION_13_14
import com.example.notestakingapp.NotesRepository
import com.example.notestakingapp.data.database.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DIModule {


    @Provides
    @Singleton
    fun provideDataBase(application: Application) : DataBase{
        return Room.databaseBuilder(
            application,
            DataBase::class.java,
            "Notes_DataBase"
        ).addMigrations(MIGRATION_13_14).build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: DataBase) : NotesRepository {
        return NotesRepository(db.notesDao())
    }

}