package com.dvach.lab2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about_note.*


class AboutNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_note)
        backImg.setOnClickListener { finish() }

        val intent = intent
        val note = intent.getSerializableExtra("note") as Note
       // var note = AppDatabase.getDatabase(this).NoteDao().getByName(noteName.toString())

        noteNameTextView.text = note.name
        categoryNameTextView.text = note.category
        textTextView.text = note.text
        if (note.check == false){
            checkTextView.text = "Не выполнено"

        }
        else {
            checkTextView.text = "Выполнено"
        }
        prioritetTextView.text = note.prioritet
        changeImageView.setOnClickListener {
            val i: Intent
            i = Intent(this, NoteActivity::class.java)
            i.putExtra("note",note)
            startActivity(i)
        }



    }
}
