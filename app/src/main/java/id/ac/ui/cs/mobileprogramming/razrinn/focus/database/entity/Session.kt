package id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "session_table")
data class Session(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "goal") val goal: String,
    @ColumnInfo(name = "review") val review: String = "",
    @ColumnInfo(name = "rating") val rating: Int = 0,
    @ColumnInfo(name = "total_time") val totalTime: Int = 0,
    @ColumnInfo(name = "is_finished") val isFinished: Boolean = false,
    @ColumnInfo(name = "created_at") val createdAt: Date = Date(),
    @ColumnInfo(name = "category_id") val categoryId: Int
)