package com.metehanbolat.firebaseperformapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.metehanbolat.firebaseperformapp.databinding.ListItemBinding

class ItemRecyclerAdapter(val itemArray: ArrayList<String>): RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.item.text = itemArray[position]
    }

    override fun getItemCount() = itemArray.size
}