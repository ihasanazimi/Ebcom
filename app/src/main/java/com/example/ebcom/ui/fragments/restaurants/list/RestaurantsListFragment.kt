package com.example.ebcom.ui.fragments.restaurants.list

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ebcom.R
import com.example.ebcom.databinding.FragmentRestaurantsListBinding
import com.example.ebcom.model.Restaurant
import com.example.ebcom.model.seald.SortValues
import com.example.ebcom.ui.fragments.restaurants.RestaurantsVM
import com.example.ebcom.ui.fragments.restaurants.details.RestaurantsDetailsFragment
import com.example.ebcom.utility.base.BaseFragment
import com.example.ebcom.utility.extentions.changeStatusBarColor
import com.example.ebcom.utility.extentions.hide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestaurantsListFragment : BaseFragment<FragmentRestaurantsListBinding,RestaurantsVM>(), RestaurantAdapter.RestaurantClickEvent {
    override val layoutId: Int get() = R.layout.fragment_restaurants_list
    override val viewModel : RestaurantsVM by viewModel()

    private lateinit var adapter : RestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllRestaurants()
        adapter = RestaurantAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter

        binding.sortContainer.setOnClickListener {
            val popup = PopupMenu(requireContext(),it).apply {
                menuInflater.inflate(R.menu.menu, this.menu)
                setOnMenuItemClickListener { menuItem ->
                    when(menuItem.itemId){
                        R.id.bestMatch -> {
                            viewModel.getSortRestaurantsBy(SortValues.BestMatch.sortKey)
                            binding.btnSort.text = SortValues.BestMatch.sortKey
                        }
                        R.id.newest -> {
                            viewModel.getSortRestaurantsBy(SortValues.NEWEST.sortKey)
                            binding.btnSort.text = SortValues.NEWEST.sortKey
                        }
                        R.id.ratingAverage -> {
                            viewModel.getSortRestaurantsBy(SortValues.RatingAverage.sortKey)
                            binding.btnSort.text = SortValues.RatingAverage.sortKey
                        }
                        R.id.distance -> {
                            viewModel.getSortRestaurantsBy(SortValues.Distance.sortKey)
                            binding.btnSort.text = SortValues.Distance.sortKey
                        }
                        R.id.popularity -> {
                            viewModel.getSortRestaurantsBy(SortValues.POPULARITY.sortKey)
                            binding.btnSort.text = SortValues.POPULARITY.sortKey
                        }
                        R.id.averageProductPrice -> {
                            viewModel.getSortRestaurantsBy(SortValues.AverageProductPrice.sortKey)
                            binding.btnSort.text = SortValues.AverageProductPrice.sortKey
                        }
                        R.id.deliveryCosts -> {
                            viewModel.getSortRestaurantsBy(SortValues.DeliveryCosts.sortKey)
                            binding.btnSort.text = SortValues.DeliveryCosts.sortKey
                        }
                        R.id.minCost -> {
                            viewModel.getSortRestaurantsBy(SortValues.MinCost.sortKey)
                            binding.btnSort.text = SortValues.MinCost.sortKey
                        }
                        else -> {
                            viewModel.getSortRestaurantsBy(SortValues.BestMatch.sortKey)
                            binding.btnSort.text = SortValues.BestMatch.sortKey
                        } // default sorting
                    }
                    return@setOnMenuItemClickListener false
                }
            }

            when(binding.btnSort.text.toString()){
                SortValues.BestMatch.sortKey -> popup.menu.getItem(0).isChecked = true
                SortValues.NEWEST.sortKey -> popup.menu.getItem(1).isChecked = true
                SortValues.RatingAverage.sortKey -> popup.menu.getItem(2).isChecked = true
                SortValues.Distance.sortKey -> popup.menu.getItem(3).isChecked = true
                SortValues.POPULARITY.sortKey -> popup.menu.getItem(4).isChecked = true
                SortValues.AverageProductPrice.sortKey -> popup.menu.getItem(5).isChecked = true
                SortValues.DeliveryCosts.sortKey -> popup.menu.getItem(6).isChecked = true
                SortValues.MinCost.sortKey -> popup.menu.getItem(7).isChecked = true
            }
            popup.show()
        }
    }


    override fun registerObservers() {
        super.registerObservers()

        viewModel.restaurants.observe(viewLifecycleOwner){
            if (it.restaurants.isNotEmpty()){
                adapter.setItemByDiffUtil(it.restaurants)
                viewModel.addRestaurantsList(it)
            }
        }

        viewModel.isDone.observe(viewLifecycleOwner){
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner){
            if (it.isNotEmpty()) binding.progressBar.hide()
        }

    }

    override fun onRestaurantClick(restaurant: Restaurant) {
        findNavController().navigate(R.id.action_restaurantsListFragment_to_restaurantsDetailsFragment, bundleOf(Pair(RestaurantsDetailsFragment.RESTAURANT_DETAILS,restaurant)))
    }

    override fun onLikeRestaurant(restaurant: Restaurant) {
        viewModel.updateFavorite(restaurant)
    }
}