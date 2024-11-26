package com.ashish.cleanjetpackhilt.data.database// AppDatabase.kt
import androidx.room.Database
import androidx.room.RoomDatabase
import com.ashish.cleanjetpackhilt.data.model.MovieDetail

@Database(entities = [MovieDetail::class], version = 1, exportSchema = false)
abstract class AppDatabase constructor() : RoomDatabase() {
    abstract fun movieDetailDao(): MovieDetailDao
}

