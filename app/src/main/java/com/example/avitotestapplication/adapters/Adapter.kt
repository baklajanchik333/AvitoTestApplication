package com.example.avitotestapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.avitotestapplication.R
import java.io.Serializable

class Adapter : RecyclerView.Adapter<Adapter.RecyclerViewHolder>(), Serializable {
    private val itemList = mutableListOf<Int>()
    private val removedItemList = mutableListOf<Pair<Int, Int>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder =
        RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val holderPosition = this.itemList[position]

        holder.removeButton.setOnClickListener {
            it.isClickable = false
            removeItem(holder.adapterPosition, holderPosition)
        }
        holder.textView.text = holderPosition.toString()
    }

    override fun getItemCount(): Int = this.itemList.size

    fun addItem() {
        if (this.removedItemList.isEmpty()) {
            this.itemList.add(itemCount)
            notifyItemInserted(itemCount)
        } else {
            val lastRemovedItem = this.removedItemList.removeLast()

            this.itemList.add(lastRemovedItem.second, lastRemovedItem.first)
            notifyItemInserted(lastRemovedItem.second)
        }
    }

    private fun removeItem(actualPosition: Int, initPosition: Int) {
        this.itemList.removeAt(actualPosition)
        this.removedItemList.add(Pair(initPosition, actualPosition))

        notifyItemRemoved(actualPosition)
    }

    class RecyclerViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val removeButton: ImageView = item.findViewById(R.id.delete)
        val textView: TextView = item.findViewById(R.id.textNumber)
    }
}