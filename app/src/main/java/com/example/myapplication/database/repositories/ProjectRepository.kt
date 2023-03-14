package com.example.myapplication.database.repositories

import com.example.myapplication.database.models.Project
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {

    suspend fun insertOrReplaceProject(project: Project)

    suspend fun deleteProject(project: Project)

    suspend fun getProjectById(id: Int): Project?

    fun getProjects(): Flow<List<Project>>
}