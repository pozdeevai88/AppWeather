package ru.geekbrains.appweather.ui.slideshow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_favorites_recycler_item.view.*
import kotlinx.android.synthetic.main.fragment_history_recycler_item.view.*
import ru.geekbrains.appweather.R

class FavoritesAdapter :
    RecyclerView.Adapter<FavoritesAdapter.RecyclerItemViewHolder>() {
    private var data: List<String> = arrayListOf()

    fun setData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.fragment_favorites_recycler_item, parent,
                    false
                ) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        fun bind(city: String) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.favoritesFragmentRecyclerviewItem.text = city
                itemView.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "on click: $city",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }
}