package com.dvach.lab2.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.dvach.lab2.Category
import com.dvach.lab2.Note

data class CategoryWithNotes(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryName",
        entityColumn = "category"
    )
    val notes: List<Note>


)

