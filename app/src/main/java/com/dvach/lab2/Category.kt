package com.dvach.lab2


import android.content.Context
import androidx.room.*
import java.security.AccessControlContext


@Entity(indices = arrayOf(Index(value = ["name"], unique = true)))
class Category {
    @PrimaryKey
    var name: String? = null
    //  @PrimaryKey(autoGenerate = true)
    //   var id: Long = 0
}

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Note WHERE name = :name")
    fun getByName(name: String): Category?

    @Insert
    fun insert(category: Category?)
}
