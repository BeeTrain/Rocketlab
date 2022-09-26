package io.rocketlab.screen.note.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    var text: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString(): String {
        return "Note(text='$text', id=$id)"
    }
}