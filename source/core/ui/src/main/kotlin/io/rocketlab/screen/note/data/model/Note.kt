package io.rocketlab.screen.note.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var text: String = ""
) {

    override fun toString(): String {
        return "Note(text='$text', id=$id)"
    }
}