package com.dvach.lab2

import android.content.Context
import androidx.room.*
import java.io.Serializable
import java.security.AccessControlContext


@Entity(indices = arrayOf(Index(value = ["email"], unique = true)))
class User : Serializable {
    var userName: String? = null
    var email: String? = null
    var password: String? = null
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0
}

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE userId = :id")
    fun getById(id: Long): User?

    @Insert
    fun insert(user: User?)

    @Query("SELECT * FROM User WHERE email=:email AND password=:password")
    fun getUser(email: String, password: String): User

    @Query("SELECT * FROM User WHERE email=:email")
    fun getUserByEmail(email: String): User?
}

