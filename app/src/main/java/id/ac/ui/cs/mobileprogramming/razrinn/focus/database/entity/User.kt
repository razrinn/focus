package id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "job_title") val jobTitle: String,
    @ColumnInfo(name = "picture_path") val picturePath: String
)