package com.dvach.lab2


import android.content.Context
import androidx.room.*
import com.dvach.lab2.models.CategoryWithNotes
import java.security.AccessControlContext


@Entity
class Category {
    @PrimaryKey
    var categoryName: String = ""
    @Embedded
    lateinit var categoryUser: User
    //  @PrimaryKey(autoGenerate = true)
    //   var id: Long = 0
}

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category WHERE categoryName = :name")
    fun getByName(name: String): Category?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category?)

    @Query("SELECT categoryName FROM Category WHERE userId =:userId")
    fun getAllNames(userId: Long): List<String>

    @Transaction
    @Query("SELECT * FROM Category WHERE userId =:userId")
    fun getCategoriesWithNotes(userId: Long): List<CategoryWithNotes>

    @Delete
    fun delete(category: Category)
}
