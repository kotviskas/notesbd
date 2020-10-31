package com.dvach.lab2.adapter

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dvach.lab2.AppDatabase


class RecyclerItemTouchHelper(var adapter: RecyclerAdapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        var pos: Int = viewHolder.adapterPosition
        adapter.deleteItem(pos)

    }

}