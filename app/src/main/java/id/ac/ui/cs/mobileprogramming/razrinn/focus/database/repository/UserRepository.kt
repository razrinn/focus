package id.ac.ui.cs.mobileprogramming.razrinn.focus.database.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.dao.UserDao
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.User

class UserRepository(private val userDao: UserDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val user: LiveData<User> = userDao.getUser()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(user: User) {
        userDao.updateUser(user)
    }
}