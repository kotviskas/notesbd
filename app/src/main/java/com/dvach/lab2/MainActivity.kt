package com.dvach.lab2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dvach.lab2.adapter.RecyclerAdapter
import com.dvach.lab2.models.Item
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), RecyclerAdapter.onItemClick, RecyclerAdapter.onCheck {
    lateinit var sPref: SharedPreferences
    lateinit var user:User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        user= intent.getSerializableExtra("user") as User
        floatingActionButton.setOnClickListener {
            val i = Intent(this, NoteActivity::class.java)
            i.putExtra("user",user)
            startActivity(i)
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


        var list = AppDatabase.getDatabase(this).CategoryDao().getCategoriesWithNotes(user.userId)
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

        recyclerView.layoutManager = GridLayoutManager(this, 1)
        var adapter: RecyclerAdapter = RecyclerAdapter(items, this, this)
        recyclerView.adapter = adapter

        val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    var pos:Int = viewHolder.adapterPosition
                    AppDatabase.getDatabase(application).NoteDao().delete(items[pos].note_object as Note)
                    adapter.deleteItem(pos)

                }

            }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)
    }

    override fun noteClick(note: Note) {
        val i: Intent
        i = Intent(this, AboutNoteActivity::class.java)
        i.putExtra("note", note)
        i.putExtra("user", user)
        startActivity(i)

    }

    override fun changeCheck(note: Note) {
        AppDatabase.getDatabase(this).NoteDao().insert(note)
    }
}
