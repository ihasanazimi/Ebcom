package com.example.ebcom.ui.fragments.restaurants.list

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ebcom.R
import com.example.ebcom.databinding.FragmentRestaurantsListBinding
import com.example.ebcom.model.Restaurant
import com.example.ebcom.ui.fragments.restaurants.RestaurantsVM
import com.example.ebcom.ui.fragments.restaurants.details.RestaurantsDetailsFragment
import com.example.ebcom.utility.base.BaseFragment
import com.example.ebcom.utility.extentions.changeStatusBarColor
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

    }

    override fun onRestaurantClick(restaurant: Restaurant) {
        findNavController().navigate(R.id.action_restaurantsListFragment_to_restaurantsDetailsFragment, bundleOf(Pair(RestaurantsDetailsFragment.RESTAURANT_DETAILS,restaurant)))
    }

    override fun onLikeRestaurant(restaurant: Restaurant) {
        viewModel.updateFavorite(restaurant)
    }
}