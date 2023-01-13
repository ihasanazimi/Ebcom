package com.example.ebcom.ui.fragments.restaurants.list

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.ebcom.R
import com.example.ebcom.databinding.FragmentRestaurantsListBinding
import com.example.ebcom.model.Restaurant
import com.example.ebcom.model.seald.SealdSortValues
import com.example.ebcom.ui.fragments.restaurants.RestaurantsVM
import com.example.ebcom.ui.fragments.restaurants.details.RestaurantsDetailsFragment
import com.example.ebcom.utility.base.BaseFragment
import com.example.ebcom.utility.extentions.hide
import com.google.android.material.snackbar.Snackbar
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

        binding.btnSort.setOnClickListener {
            val popup = PopupMenu(requireContext(),it).apply {
                menuInflater.inflate(R.menu.menu, this.menu)
                setOnMenuItemClickListener { menuItem ->
                    when(menuItem.itemId){
                        R.id.bestMatch -> {
                            viewModel.getSortRestaurantsBy(SealdSortValues.BestMatch.sortKey)
                            binding.btnSortText.text = SealdSortValues.BestMatch.sortKey
                        }
                        R.id.newest -> {
                            viewModel.getSortRestaurantsBy(SealdSortValues.NEWEST.sortKey)
                            binding.btnSortText.text = SealdSortValues.NEWEST.sortKey
                        }
                        R.id.ratingAverage -> {
                            viewModel.getSortRestaurantsBy(SealdSortValues.RatingAverage.sortKey)
                            binding.btnSortText.text = SealdSortValues.RatingAverage.sortKey
                        }
                        R.id.distance -> {
                            viewModel.getSortRestaurantsBy(SealdSortValues.Distance.sortKey)
                            binding.btnSortText.text = SealdSortValues.Distance.sortKey
                        }
                        R.id.popularity -> {
                            viewModel.getSortRestaurantsBy(SealdSortValues.POPULARITY.sortKey)
                            binding.btnSortText.text = SealdSortValues.POPULARITY.sortKey
                        }
                        R.id.averageProductPrice -> {
                            viewModel.getSortRestaurantsBy(SealdSortValues.AverageProductPrice.sortKey)
                            binding.btnSortText.text = SealdSortValues.AverageProductPrice.sortKey
                        }
                        R.id.deliveryCosts -> {
                            viewModel.getSortRestaurantsBy(SealdSortValues.DeliveryCosts.sortKey)
                            binding.btnSortText.text = SealdSortValues.DeliveryCosts.sortKey
                        }
                        R.id.minCost -> {
                            viewModel.getSortRestaurantsBy(SealdSortValues.MinCost.sortKey)
                            binding.btnSortText.text = SealdSortValues.MinCost.sortKey
                        }
                        else -> {
                            viewModel.getSortRestaurantsBy(SealdSortValues.BestMatch.sortKey)
                            binding.btnSortText.text = SealdSortValues.BestMatch.sortKey
                        } // default sorting
                    }
                    return@setOnMenuItemClickListener false
                }
            }

            when(binding.btnSortText.text.toString()){
                SealdSortValues.BestMatch.sortKey -> popup.menu.getItem(0).isChecked = true
                SealdSortValues.NEWEST.sortKey -> popup.menu.getItem(1).isChecked = true
                SealdSortValues.RatingAverage.sortKey -> popup.menu.getItem(2).isChecked = true
                SealdSortValues.Distance.sortKey -> popup.menu.getItem(3).isChecked = true
                SealdSortValues.POPULARITY.sortKey -> popup.menu.getItem(4).isChecked = true
                SealdSortValues.AverageProductPrice.sortKey -> popup.menu.getItem(5).isChecked = true
                SealdSortValues.DeliveryCosts.sortKey -> popup.menu.getItem(6).isChecked = true
                SealdSortValues.MinCost.sortKey -> popup.menu.getItem(7).isChecked = true
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
            if (it.isNotEmpty()) {
                binding.progressBar.hide()
                Snackbar.make(binding.root,it[0],Snackbar.LENGTH_INDEFINITE).setAction("try") { viewModel.getAllRestaurants() }.show()
            }
        }

    }

    override fun onRestaurantClick(restaurant: Restaurant) {
        findNavController().navigate(R.id.action_restaurantsListFragment_to_restaurantsDetailsFragment, bundleOf(Pair(RestaurantsDetailsFragment.RESTAURANT_DETAILS,restaurant)))
    }

    override fun onLikeRestaurant(restaurant: Restaurant) {
        viewModel.updateFavorite(restaurant)
    }
}