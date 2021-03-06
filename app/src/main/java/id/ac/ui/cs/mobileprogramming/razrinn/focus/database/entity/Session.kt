package id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "session_table")
data class Session(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "goal") val goal: String,
    @ColumnInfo(name = "review") var review: String = "",
    @ColumnInfo(name = "rating") var rating: Int = 0,
    @ColumnInfo(name = "total_time") var totalTime: Int = 0,
    @ColumnInfo(name = "is_finished") var isFinished: Boolean = false,
    @ColumnInfo(name = "created_at") val createdAt: Date = Date(),
    @ColumnInfo(name = "category_id") val categoryId: Int
)