package io.rocketlab.screen.note.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var text: String = "",

    var createdOn: Long = System.currentTimeMillis(),

    var updatedOn: Long = System.currentTimeMillis()
)