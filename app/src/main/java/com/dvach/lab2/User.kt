package com.dvach.lab2

import android.content.Context
import androidx.room.*
import java.security.AccessControlContext


@Entity(indices = arrayOf(Index(value = ["email"], unique = true)))
class User {
    var name: String? = null
    var email: String? = null
    var password: String? = null
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE id = :id")
    fun getById(id: Long): User?

    @Insert
    fun insert(user: User?)

    @Query("SELECT * FROM User WHERE email=:email AND password=:password")
    fun getUser(email: String, password: String): User?
}

