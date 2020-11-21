package id.ac.ui.cs.mobileprogramming.razrinn.focus.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.converter.DateConverter
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.dao.*
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Category::class, Session::class, Task::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun sessionDao(): SessionDao
    abstract fun taskDao(): TaskDao

    private class ApplicationDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.categoryDao(), database.sessionDao())
                }
            }
        }

        suspend fun populateDatabase(categoryDao: CategoryDao, sessionDao: SessionDao) {
            // Delete all content here.
            categoryDao.deleteAll()

            // Add sample words.
            var category = Category(name = "Work", description = "Doing session related to work")
            categoryDao.insert(category)
            category = Category(name = "College", description = "Doing session related to college")
            categoryDao.insert(category)
            category = Category(name = "Personal Life", description = "Doing session related to personal life")
            categoryDao.insert(category)
            category = Category(name = "Other", description = "Other category")
            categoryDao.insert(category)
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ApplicationDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    "app_db"
                )
                    .addCallback(ApplicationDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

