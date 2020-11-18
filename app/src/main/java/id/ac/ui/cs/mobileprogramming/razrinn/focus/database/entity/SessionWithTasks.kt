package id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SessionWithTasks (
    @Embedded val session: Session,
    @Relation(
        parentColumn = "id",
        entityColumn = "session_id"
    )
    val tasks: List<Task>
)