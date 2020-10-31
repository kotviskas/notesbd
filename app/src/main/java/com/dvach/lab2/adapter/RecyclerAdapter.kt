package com.dvach.lab2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dvach.lab2.AppDatabase
import com.dvach.lab2.Category
import com.dvach.lab2.Note
import com.dvach.lab2.R
import com.dvach.lab2.models.Item
import kotlinx.android.synthetic.main.add_task.view.*

private const val POST_TYPE_HEAD: Int = 0
private const val POST_TYPE_NOTE: Int = 1

class RecyclerAdapter(
    var listItems: ArrayList<Item>,
    var listener: onItemClick,
    var listener2: onCheck
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface onItemClick {
        fun noteClick(note: Note)
    }

    interface onCheck {
        fun changeCheck(note: Note)
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var headText = itemView.findViewById<TextView>(R.id.mainTextView)
        var textText = itemView.findViewById<TextView>(R.id.textTextView);
        var checkBox = itemView.findViewById<CheckBox>(R.id.checkBox);


        fun SetNote(note: Note) {
            headText.setText(note.name);
            textText.setText(note.category);
            checkBox.isChecked = note.check

            itemView.cardView.setCardBackgroundColor(note.color)

        }


    }

    class HeadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var headText = itemView.findViewById<TextView>(R.id.mainTextView2)

        fun SetText(category: Category) {
            headText.setText(category.categoryName);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == POST_TYPE_HEAD) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.add_category_name, parent, false)
            return HeadViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.add_task, parent, false)
            return NoteViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun deleteItem(position: Int) {
        listItems.removeAt(position)

        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (listItems[position].type == 0) {
            POST_TYPE_HEAD
        } else {
            POST_TYPE_NOTE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == POST_TYPE_HEAD) {
            (holder as HeadViewHolder).SetText(listItems[position].note_object as Category)
        } else {
            (holder as NoteViewHolder).SetNote(listItems[position].note_object as Note)
            holder.itemView.setOnClickListener {
                listener.noteClick(listItems[position].note_object as Note)

            }
            holder.itemView.checkBox.setOnClickListener {
                (listItems[position].note_object as Note).check =
                    !(listItems[position].note_object as Note).check
                listener2.changeCheck((listItems[position].note_object as Note))
            }
        }
    }
}