package com.dvach.lab2


import android.content.Context
import androidx.room.*
import java.security.AccessControlContext


@Entity(indices = arrayOf(Index(value = ["name"], unique = true)))
class Note {
    @PrimaryKey
    var name: String? = null
    var text: String? = null
    var date: String? = null
    var category: String? = null
  //  @PrimaryKey(autoGenerate = true)
 //   var id: Long = 0
}

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note WHERE name = :name")
    fun getByName(name: String): Note?

    @Insert
    fun insert(note: Note?)

}

