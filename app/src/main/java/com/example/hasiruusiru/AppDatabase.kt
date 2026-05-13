package com.example.hasiruusiru

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tree::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun treeDao(): TreeDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "hasiru_usiru_db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}