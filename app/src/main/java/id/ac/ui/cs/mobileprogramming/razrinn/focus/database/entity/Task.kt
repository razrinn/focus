package id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "priority") val priority: Int,
    @ColumnInfo(name = "is_finished") var isFinished: Boolean = false,
    @ColumnInfo(name = "session_id") val sessionId: Int
){
    override fun toString(): String {
        return this.name + " " + this.sessionId.toString()
    }
}