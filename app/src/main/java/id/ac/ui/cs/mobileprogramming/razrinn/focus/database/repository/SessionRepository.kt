package id.ac.ui.cs.mobileprogramming.razrinn.focus.database.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.dao.SessionDao
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Category
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Session

class SessionRepository(private val sessionDao: SessionDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allSessions: LiveData<List<Session>> = sessionDao.getAllSession()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(session: Session) {
        sessionDao.insert(session)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(session: Session) {
        sessionDao.updateSession(session)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun get(sessionId: Long) {
        sessionDao.getSessionById(sessionId)
    }
}