package id.ac.ui.cs.mobileprogramming.razrinn.focus.database.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.dao.TaskDao
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Task

class TaskRepository(private val taskDao: TaskDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allSessions: LiveData<List<Task>> = taskDao.getAllTasks()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(task: Task) {
        taskDao.updateTask(task)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getBySessionId(sessionId: Long) {
        taskDao.getTasksBySessionId(sessionId)
    }
}