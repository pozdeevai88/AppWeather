package ru.geekbrains.appweather.ui.slideshow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_favorites_recycler_item.view.*
import ru.geekbrains.appweather.R
import ru.geekbrains.appweather.model.Weather
import ru.geekbrains.appweather.repository.Repository
import ru.geekbrains.appweather.repository.RepositoryImpl
import ru.geekbrains.appweather.ui.home.HomeFragment

class FavoritesAdapter(private var onItemViewClickListener: SlideshowFragment.OnItemViewClickListener?) :
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

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(city: String) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.favoritesFragmentRecyclerviewItem.text = city
                itemView.setOnClickListener {
                    lateinit var weather: Weather
                    val repositoryImpl: Repository = RepositoryImpl()
                    val rusCities = repositoryImpl.getWeatherFromLocalStorageRus()
                    val wldCities = repositoryImpl.getWeatherFromLocalStorageWorld()

                    for (item in rusCities) {
                        if (item.city.city == city) weather = item
                    }
                    for (item in wldCities) {
                        if (item.city.city == city) weather = item
                    }
                    onItemViewClickListener?.onItemViewClick(weather)
                }
            }
        }
    }
}