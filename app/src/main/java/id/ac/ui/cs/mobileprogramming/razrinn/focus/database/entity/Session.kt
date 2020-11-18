package id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "session_table")
data class Session(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "goal") val goal: String,
    @ColumnInfo(name = "review") val review: String,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "is_finished") val isFinished: Boolean,
    @ColumnInfo(name = "created_at") val createdAt: Date,
    @ColumnInfo(name = "category_id") val categoryId: Long
)