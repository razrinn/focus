package id.ac.ui.cs.mobileprogramming.razrinn.focus.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.dao.*
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.*

@Database(
    entities = [Category::class, Session::class, Task::class, CategoryWithSessions::class, SessionWithTasks::class],
    version = 1,
    exportSchema = false
)
public abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun sessionDao(): SessionDao
    abstract fun taskDao(): TaskDao
    abstract fun userDao(): UserDao


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null

        fun getDatabase(context: Context): ApplicationDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    "app_db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}