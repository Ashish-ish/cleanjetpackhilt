package com.ashish.cleanjetpackhilt.di

import android.content.Context
import androidx.room.Room
import com.ashish.cleanjetpackhilt.data.database.AppDatabase
import com.ashish.cleanjetpackhilt.data.database.AppDatabaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
    }

    @Provides
    @Singleton
    fun provideAppDatabaseImpl(database: AppDatabase): AppDatabaseImpl {
        return AppDatabaseImpl(database)
    }
}