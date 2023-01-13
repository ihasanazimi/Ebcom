package com.example.ebcom.ui.fragments.restaurants.details

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.ebcom.R
import com.example.ebcom.databinding.FragmentRestaurantDetailsBinding
import com.example.ebcom.model.Restaurant
import com.example.ebcom.model.seald.SealdRestaurantStatus
import com.example.ebcom.ui.fragments.restaurants.RestaurantsVM
import com.example.ebcom.utility.base.BaseFragment
import com.example.ebcom.utility.customViews.ToggleImageView
import com.example.ebcom.utility.extentions.hide
import com.example.ebcom.utility.extentions.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestaurantsDetailsFragment: BaseFragment<FragmentRestaurantDetailsBinding, RestaurantsVM>() {

    override val layoutId: Int get() = R.layout.fragment_restaurant_details
    override val viewModel : RestaurantsVM by viewModel()

    companion object{
        const val RESTAURANT_DETAILS = "RESTAURANT_DETAILS"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get restaurant model from arguments
        val restaurant = requireArguments().getSerializable(RESTAURANT_DETAILS) as Restaurant
        // pass restaurant data into layout (data binding)
        binding.data = restaurant

        // update ui
        favoriteIconState(restaurant)
        restaurantOpenClosAheadUiState(restaurant)
        loadRestaurantCover(restaurant)

        // favorite events
        binding.favoriteToggleBtn.addStateListener(object : ToggleImageView.OnStateChangedListener{
            override fun onChecked() {
                restaurant.favorite = 1
                viewModel.updateOnLiveData(restaurant)
                viewModel.updateFavorite(restaurant)
            }
            override fun onUnchecked() {
                restaurant.favorite = 0
                viewModel.updateOnLiveData(restaurant)
                viewModel.updateFavorite(restaurant)
            }
        })

    }

    private fun loadRestaurantCover(model: Restaurant) {
        Glide.with(binding.ivRestaurantCover.context)
            .load(model.cover)
            .timeout(30000)
            .error(R.drawable.ic_baseline_error)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.ic_place_holder)
            .into(binding.ivRestaurantCover)
    }

    private fun restaurantOpenClosAheadUiState(model: Restaurant) {
        when(model.status){
            SealdRestaurantStatus.OPEN.status -> {
                binding.tvRestaurantOpenOrClosedState.setTextColor(binding.root.context.resources.getColor(R.color.green))
                binding.closedIv.hide()
                binding.deliveryCostsContainer.show()
                binding.root.setBackgroundColor(resources.getColor(R.color.green_transparently,null))
                binding.restaurantMessage.apply {
                    show()
                    text = requireContext().getString(R.string.open_message)
                }
            }
            SealdRestaurantStatus.CLOSED.status -> {
                binding.tvRestaurantOpenOrClosedState.setTextColor(binding.root.context.resources.getColor(R.color.red))
                binding.closedIv.show()
                binding.deliveryCostsContainer.hide()
                binding.root.setBackgroundColor(resources.getColor(R.color.red_transparently,null))
                binding.restaurantMessage.apply {
                    show()
                    text = requireContext().getString(R.string.close_message)
                }
            }
            SealdRestaurantStatus.AHEAD.status -> {
                binding.tvRestaurantOpenOrClosedState.setTextColor(binding.root.context.resources.getColor(R.color.gray_very_dark))
                binding.closedIv.hide()
                binding.deliveryCostsContainer.show()
                binding.root.setBackgroundColor(resources.getColor(R.color.gray_transparently,null))
                binding.tvRestaurantOpenOrClosedState.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_access_time_24, 0, 0, 0)
                binding.restaurantMessage.apply {
                    show()
                    text = requireContext().getString(R.string.order_ahead_message)
                }
            }
            else -> binding.tvRestaurantOpenOrClosedState.setTextColor(binding.root.context.resources.getColor(R.color.gray))
        }
    }

    private fun favoriteIconState(restaurant: Restaurant){
        when (restaurant.favorite){
            1 -> binding.favoriteToggleBtn.setChecked()
            0 -> binding.favoriteToggleBtn.setUnchecked()
        }
    }
}