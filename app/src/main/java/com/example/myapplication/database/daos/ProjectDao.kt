package com.example.myapplication.database.daos

import androidx.room.*
import com.example.myapplication.database.models.Project
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceProject(project: Project)

    @Delete
    suspend fun deleteProject(project: Project)

    @Query("SELECT * FROM project WHERE id = :id")
    suspend fun getProjectById(id: Int): Project?

    @Query("SELECT * FROM project")
    fun getProjects(): Flow<List<Project>>
}