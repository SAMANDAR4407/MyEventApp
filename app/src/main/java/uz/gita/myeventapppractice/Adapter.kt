package uz.gita.myeventapppractice

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.myeventapppractice.databinding.ItemEventBinding

/**
 *    Created by Kamolov Samandar on 11.05.2023 at 18:09
 */

class Adapter : ListAdapter<EventData, Adapter.ViewHolder>(MyDiffUtils) {
    private var listener: ((EventData) -> Unit)? = null

    fun setListener(block: (EventData)-> Unit){
        listener = block
    }

    inner class ViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: EventData) {
            binding.apply {
                switchEvent.text = data.name
                switchEvent.isChecked = data.state
                if (data.state){
                    switchEvent.setTextColor(Color.BLACK)
                } else {
                    switchEvent.setTextColor(Color.GRAY)
                }
                binding.switchEvent.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        data.state = true
                        listener?.invoke(data)
                        switchEvent.setTextColor(Color.BLACK)
                    } else {
                        data.state = false
                        listener?.invoke(data)
                        switchEvent.setTextColor(Color.GRAY)
                    }
                }
            }
        }
    }

    object MyDiffUtils : DiffUtil.ItemCallback<EventData>() {

        override fun areItemsTheSame(oldItem: EventData, newItem: EventData): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: EventData, newItem: EventData): Boolean =
            oldItem.name == newItem.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}