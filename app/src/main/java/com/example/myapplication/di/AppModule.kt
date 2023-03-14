package com.example.myapplication.di

import android.app.Application
import androidx.room.Room
import com.example.myapplication.database.ProjectDatabase
import com.example.myapplication.database.implementations.ProjectRepositoryImpl
import com.example.myapplication.database.repositories.ProjectRepository
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
    fun provideProjectDatabase(app: Application): ProjectDatabase {
        return Room.databaseBuilder(
            app,
            ProjectDatabase::class.java,
            "project_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProjectRepository(db: ProjectDatabase): ProjectRepository {
        return ProjectRepositoryImpl(db.dao)
    }
}