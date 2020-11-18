package id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithSessions (
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val sessions: List<Session>
)