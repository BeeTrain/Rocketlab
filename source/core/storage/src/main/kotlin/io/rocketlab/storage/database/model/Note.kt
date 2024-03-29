package io.rocketlab.storage.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var text: String = "",

    var status: NoteStatus = NoteStatus.TODO,

    var createdOn: Long = System.currentTimeMillis(),

    var updatedOn: Long = System.currentTimeMillis()
)