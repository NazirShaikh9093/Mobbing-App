package com.example.mobbingapp.di

import android.app.Application
import androidx.room.Room
import com.example.mobbingapp.database.ProjectDatabase
import com.example.mobbingapp.database.implementations.ProjectRepositoryImpl
import com.example.mobbingapp.database.repositories.ProjectRepository
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