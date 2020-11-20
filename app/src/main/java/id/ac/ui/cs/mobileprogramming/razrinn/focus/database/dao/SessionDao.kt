package id.ac.ui.cs.mobileprogramming.razrinn.focus.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Session
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.SessionWithTasks

@Dao
interface SessionDao {
    @Query("SELECT * FROM session_table ORDER BY  is_finished ASC, created_at DESC")
    fun getAllSession(): LiveData<List<SessionWithTasks>>

    @Query("SELECT * FROM session_table WHERE id= :sessionId")
    fun getSessionById(sessionId: Int): LiveData<SessionWithTasks>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(session: Session): Long

    @Update
    suspend fun updateSession(session: Session): Int

    @Query("DELETE FROM session_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteSingleSession(session: Session)
}