package com.example.shoppingapp.di

import android.app.Application
import androidx.room.Room
import com.example.shoppingapp.Database
import com.example.shoppingapp.data.note_item.NoteDao
import com.example.shoppingapp.data.task_item.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(app: Application): Database {
        return Room.databaseBuilder(
            context = app,
            klass = Database::class.java,
            name = "shoppingApp_db"
        ).build()
    }

    @Provides
    fun provideTaskDao(db: Database): TaskDao{
        return db.taskDao
    }

    @Provides
    fun provideNoteDao(db: Database): NoteDao{
        return db.noteDao
    }


}