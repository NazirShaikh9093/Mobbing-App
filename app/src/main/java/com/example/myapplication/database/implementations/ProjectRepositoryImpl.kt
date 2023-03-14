package com.example.myapplication.database.implementations

import com.example.myapplication.database.daos.ProjectDao
import com.example.myapplication.database.models.Project
import com.example.myapplication.database.repositories.ProjectRepository
import kotlinx.coroutines.flow.Flow

class ProjectRepositoryImpl(
    private val dao: ProjectDao
): ProjectRepository {

    override suspend fun insertOrReplaceProject(project: Project) {
        dao.insertOrReplaceProject(project)
    }

    override suspend fun deleteProject(project: Project) {
        dao.deleteProject(project)
    }

    override suspend fun getProjectById(id: Int): Project? {
        return dao.getProjectById(id)
    }

    override fun getProjects(): Flow<List<Project>> {
        return dao.getProjects()
    }

}