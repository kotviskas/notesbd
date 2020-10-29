package com.dvach.lab2


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.room.*
import java.io.Serializable
import java.security.AccessControlContext


@Entity
class Note : Serializable{

    var name: String = ""
    var text: String = ""
    var date: String = ""
    var category: String = ""
    var prioritet: String = ""
    var check: Boolean = false
    var color: Int = R.color.colorAccent
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note WHERE name = :name")
    fun getByName(name: String): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

}

