package com.dvach.lab2


import androidx.room.*
import java.io.Serializable


@Entity
class Note : Serializable{

    var name: String = ""
    var text: String = ""
    var date: String = ""
    var category: String = ""
    var prioritet: String = ""
    var check: Boolean = false
    var color: Int = R.color.colorAccent
    @Embedded lateinit var user: User
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note WHERE userName = :name")
    fun getByName(name: String): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

}

