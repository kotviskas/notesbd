package com.dvach.lab2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dvach.lab2.adapter.RecyclerAdapter
import com.dvach.lab2.models.Item
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecyclerAdapter.onItemClick, RecyclerAdapter.onCheck {
    lateinit var sPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        floatingActionButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    NoteActivity::class.java
                )
            )
        }
        exitImage.setOnClickListener {

            sPref = getSharedPreferences("kek", Context.MODE_PRIVATE)
            sPref.edit().remove("userEmail").apply();
            sPref.edit().remove("userPassword").apply()
            startActivity(
                Intent(
                    this,
                    SplashActivity::class.java
                )
            )
        }


    }

    override fun onResume() {
        super.onResume()
        var items = ArrayList<Item>()


        var list = AppDatabase.getDatabase(this).CategoryDao().getCategoriesWithNotes()
        list.forEach {

            items.add(Item(0, it.category))
            it.notes.forEach {
                items.add(Item(1, it))
            }

        }

        if (items.size != 0) {

            imageView.visibility = View.INVISIBLE
            textView.visibility = View.INVISIBLE
            recyclerView.visibility = View.VISIBLE
        } else {
            imageView.visibility = View.VISIBLE
            textView.visibility = View.VISIBLE
            recyclerView.visibility = View.INVISIBLE
        }

        //var item=Item(1, note)
        // items.add(item)
        recyclerView.adapter = RecyclerAdapter(items, this, this)
    }

    override fun noteClick(note: Note) {
        val i: Intent
        i = Intent(this, AboutNoteActivity::class.java)
        i.putExtra("note", note)
        startActivity(i)

    }

    override fun changeCheck(note: Note) {
        AppDatabase.getDatabase(this).NoteDao().insert(note)
    }
}
