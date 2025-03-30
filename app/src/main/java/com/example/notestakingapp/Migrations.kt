package com.example.notestakingapp

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val MIGRATION_9_10 = object : Migration(9, 10) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // 1. Create new table with the correct schema
        database.execSQL("""
            CREATE TABLE NotesData_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                title TEXT NOT NULL DEFAULT 'Untitled', 
                note TEXT NOT NULL
            );
        """.trimIndent())

        // 2. Copy data from old table to new table
        database.execSQL("""
            INSERT INTO NotesData_new (id, title, note)
            SELECT id, COALESCE(title, 'Untitled'), note FROM NotesData;
        """.trimIndent())

        // 3. Remove the old table
        database.execSQL("DROP TABLE NotesData;")

        // 4. Rename new table to match the original table name
        database.execSQL("ALTER TABLE NotesData_new RENAME TO NotesData;")
    }
}

val MIGRATION_13_14 = object : Migration(13, 14 ){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL( "UPDATE NotesData SET noteDate = '${
            LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}' " )
    }
}
