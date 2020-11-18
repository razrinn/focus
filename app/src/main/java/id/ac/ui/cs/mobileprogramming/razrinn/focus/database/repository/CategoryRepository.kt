package id.ac.ui.cs.mobileprogramming.razrinn.focus.database.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.dao.CategoryDao
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Category

class CategoryRepository(private val categoryDao: CategoryDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allCategories: LiveData<List<Category>> = categoryDao.getSortedCategories()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(category: Category) {
        categoryDao.insert(category)
    }
}