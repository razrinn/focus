package id.ac.ui.cs.mobileprogramming.razrinn.focus.database.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.dao.CategoryDao
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.dao.SessionDao
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.dao.TaskDao
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Category
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Session
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.SessionWithTasks
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Task

class FocusRepository(
    private val sessionDao: SessionDao,
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao
    ) {
    val allSessions: LiveData<List<SessionWithTasks>> = sessionDao.getAllSession()
    val allCategories: LiveData<List<Category>> = categoryDao.getSortedCategories()
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertSession(session: Session) {
        sessionDao.insert(session)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getSession(sessionId: Int): LiveData<SessionWithTasks> {
        return sessionDao.getSessionById(sessionId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertCategory(category: Category) {
        categoryDao.insert(category)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertTask(task: Task) {
        taskDao.insert(task)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getTaskBySessionId(sessionId: Int): LiveData<List<Task>> {
        return taskDao.getTasksBySessionId(sessionId)
    }
}