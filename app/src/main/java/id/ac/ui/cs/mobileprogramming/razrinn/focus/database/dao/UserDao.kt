package id.ac.ui.cs.mobileprogramming.razrinn.focus.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    fun getUser(): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User): Long

    @Update
    suspend fun updateUser(user: User): Int
}