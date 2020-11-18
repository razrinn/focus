package id.ac.ui.cs.mobileprogramming.razrinn.focus.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_table")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE session_id= :sessionId")
    fun getTasksBySessionId(sessionId: Long): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task): LiveData<Task>

    @Update
    suspend fun updateTask(task: Task): LiveData<Task>

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteSingleTask(task: Task)
}