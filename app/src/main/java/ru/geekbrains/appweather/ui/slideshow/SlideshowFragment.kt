package ru.geekbrains.appweather.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_slideshow.*
import ru.geekbrains.appweather.R
import ru.geekbrains.appweather.databinding.FragmentSlideshowBinding
import ru.geekbrains.appweather.model.Weather
import ru.geekbrains.appweather.ui.home.DetailsFragment
import ru.geekbrains.appweather.ui.home.HomeFragment
import ru.geekbrains.appweather.ui.home.HomeFragmentAdapter
import ru.geekbrains.appweather.viewmodel.FavoritesViewModel

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoritesViewModel by lazy {
        ViewModelProvider(this).get(
            FavoritesViewModel::class.java
        )
    }

    //    private val adapter: FavoritesAdapter by lazy { FavoritesAdapter() }
    private val adapter = FavoritesAdapter(object : SlideshowFragment.OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .replace(R.id.nav_host_fragment, DetailsFragment.newInstance(Bundle().apply {
                        putParcelable(DetailsFragment.BUNDLE_EXTRA, weather)
                    }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesFragmentRecyclerview.adapter = adapter
        viewModel.favoritesLiveData.observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getAllFavorites()
    }

    private fun renderData(favorites: List<String>) {
        adapter.setData(favorites)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SlideshowFragment()
    }

}