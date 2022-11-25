package io.rocketlab.storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.rocketlab.storage.database.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Query("SELECT COUNT(*) + 1 FROM Note")
    suspend fun getNewNoteId(): Int

    @Query("SELECT * FROM Note")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE id=:id")
    suspend fun getNoteById(id: Int): Note?

    @Query("DELETE FROM Note WHERE id=:id")
    suspend fun deleteNoteById(id: Int)
}