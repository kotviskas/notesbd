package com.dvach.lab2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about_note.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.activity_note.backImg
import kotlinx.android.synthetic.main.dialog_layout.view.*
import java.util.*
import kotlin.collections.ArrayList


class NoteActivity : AppCompatActivity() {
    var note = Note()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        var kaef: Boolean = false
        var list2 = ArrayList<String>(Arrays.asList(*resources.getStringArray(R.array.prioritet)))
        var category = Category()
        var categoryList: ArrayList<String> =
            AppDatabase.getDatabase(this).CategoryDao().getAllNames() as ArrayList<String>


        val intent = intent
        if (intent.hasExtra("note")) {
            note = intent.getSerializableExtra("note") as Note
            nameEditText.setText(note.name)
            noteTextEditText.setText(note.text)
            kaef = true
            list2.add(0, note.prioritet)
            categoryList.add(0,note.category)
        }


        // categorySpinner.selectedItem = note.category




        var spinner: Spinner = findViewById(R.id.prioritetSpinner)
        var adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, list2
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinner.adapter = adapter

        var spinner2: Spinner = findViewById(R.id.categorySpinner)

        val adapter2: ArrayAdapter<String> = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, categoryList
        )

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinner2.adapter = adapter2

        if (kaef == true) {
            var list2: ArrayList<String>

        }

        addCategory.setOnClickListener {
            val dialog = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null)

            val builder = AlertDialog.Builder(this)
                .setView(dialog)
            val alertDialog = builder.show()

            dialog.saveTextView.setOnClickListener {
                alertDialog.dismiss()
                category.categoryName = dialog.addCategoryEditText.text.toString()
                AppDatabase.getDatabase(this).CategoryDao().insert(category)
                categoryList.add(category.categoryName)
                adapter2.notifyDataSetChanged();
            }

            dialog.cancelTextView.setOnClickListener {
                alertDialog.dismiss()
            }
        }

        backImg.setOnClickListener { finish() }

        saveNoteBtn.setOnClickListener {
            note.name = nameEditText.text.toString()
            note.text = noteTextEditText.text.toString()
            note.date = dateEditText.text.toString()
            note.prioritet = spinner.selectedItem.toString()
            note.category = spinner2.selectedItem.toString()
            if (note.prioritet == "Срочно") {
                note.color = resources.getColor(R.color.redCard)
            }
            if (note.prioritet == "Важно") {
                note.color = resources.getColor(R.color.yellowCard)
            }
            if (note.prioritet == "Нужно") {
                note.color = resources.getColor(R.color.greenCard)
            }
            if (note.prioritet == "Пофиг") {
                note.color = resources.getColor(R.color.blueCard)
            }

            AppDatabase.getDatabase(this).NoteDao().insert(note)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


    }

}
