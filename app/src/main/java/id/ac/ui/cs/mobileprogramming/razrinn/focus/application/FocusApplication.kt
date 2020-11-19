package id.ac.ui.cs.mobileprogramming.razrinn.focus.application

import android.app.Application
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.ApplicationDatabase
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.repository.FocusRepository
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FocusApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    private val applicationScope = CoroutineScope(SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { ApplicationDatabase.getDatabase(this, applicationScope) }
    val userRepository by lazy { UserRepository(database.userDao()) }
    val focusRepository by lazy {
        FocusRepository(
            database.sessionDao(),
            database.taskDao(),
            database.categoryDao()
        )
    }
}
