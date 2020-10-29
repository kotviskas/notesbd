package com.dvach.lab2


import android.content.Context
import androidx.room.*
import com.dvach.lab2.models.CategoryWithNotes
import java.security.AccessControlContext


@Entity
class Category {
    @PrimaryKey
    var categoryName: String = ""
    //  @PrimaryKey(autoGenerate = true)
    //   var id: Long = 0
}

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category WHERE categoryName = :name")
    fun getByName(name: String): Category?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category?)

    @Query("SELECT * FROM Category")
    fun getAllNames(): List<String>

    @Transaction
    @Query("SELECT * FROM Category")
    fun getCategoriesWithNotes(): List<CategoryWithNotes>
}
