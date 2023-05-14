package ru.kekulta.rvpercentage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kekulta.rvpercentage.databinding.RecyclerItemBinding

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    var list: List<Item> = listOf()
        @SuppressLint("NotifyDataSetChanged") set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(item: Item) {
            binding.tv.text = "id: ${item.id}"
            binding.tvBuffer.text = "A".repeat(item.id * 100 % 100)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind(list[position])
}

data class Item(val id: Int)