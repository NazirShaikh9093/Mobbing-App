package com.example.myapplication.data.daos

import androidx.room.*
import com.example.myapplication.data.models.Project
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: Project)

    @Delete
    suspend fun deleteProject(project: Project)

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getProjectById(id: Int): Project?

    @Query("SELECT * FROM todo")
    fun getProjects(): Flow<List<Project>>
}