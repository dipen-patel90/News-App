package com.ct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ct.databinding.ItemNewsHeadlineBinding
import com.ct.model.vo.UINewsHeadline

class HeadlineListAdapter(
    private val uiNewsHeadlines: ArrayList<UINewsHeadline>,
    private val onItemClick: (selectedHeadline: UINewsHeadline) -> Unit,
) : RecyclerView.Adapter<HeadlineListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemNewsHeadlineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(headline: UINewsHeadline) {
            binding.newsHeadline = headline
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemNewsHeadlineBinding
            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.apply {
            uiNewsHeadlines[position].let { headline ->
                viewHolder.bind(headline)

                itemView.setOnClickListener {
                    onItemClick.invoke(headline)
                }
            }
        }
    }

    override fun getItemCount() = uiNewsHeadlines.size

    fun setData(newEvents: List<UINewsHeadline>) {
        val diffCallback = DiffCallback(uiNewsHeadlines, newEvents)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        uiNewsHeadlines.clear()
        uiNewsHeadlines.addAll(newEvents)
        diffResult.dispatchUpdatesTo(this)
    }

    private class DiffCallback(
        private val oldList: List<UINewsHeadline>,
        private val newList: List<UINewsHeadline>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].title == newList[newItemPosition].title
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }
    }
}