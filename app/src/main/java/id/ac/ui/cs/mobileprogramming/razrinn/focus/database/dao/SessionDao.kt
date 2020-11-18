package id.ac.ui.cs.mobileprogramming.razrinn.focus.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Session

@Dao
interface SessionDao {
    @Query("SELECT * FROM session_table")
    fun getAllSession(): LiveData<List<Session>>

    @Query("SELECT * FROM session_table WHERE id= :sessionId")
    fun getSessionById(sessionId: Long): LiveData<Session>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(session: Session): LiveData<Session>

    @Update
    suspend fun updateSession(session: Session): LiveData<Session>

    @Query("DELETE FROM session_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteSingleSession(session: Session)
}