package io.rocketlab.storage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.rocketlab.storage.database.model.Note
import io.rocketlab.storage.database.dao.NoteDao

private const val DATABASE_NAME = "app_database"

@Database(
    version = 1,
    entities = [Note::class],
    autoMigrations = []
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    object Provider {

        fun provide(context: Context): AppDatabase {
            return Room
                .databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "${context.packageName}-$DATABASE_NAME"
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}