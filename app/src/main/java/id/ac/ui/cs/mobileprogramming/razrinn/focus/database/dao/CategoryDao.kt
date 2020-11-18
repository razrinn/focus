package id.ac.ui.cs.mobileprogramming.razrinn.focus.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Category

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category_table ORDER BY name ASC")
    fun getSortedCategories(): LiveData<List<Category>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: Category): LiveData<Category>

    @Query("DELETE FROM category_table")
    suspend fun deleteAll()
}