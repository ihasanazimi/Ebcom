package com.example.ebcom.ui.fragments.restaurants.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.ebcom.R
import com.example.ebcom.databinding.FragmentRestaurantDetailsBinding
import com.example.ebcom.model.Restaurant
import com.example.ebcom.ui.fragments.restaurants.RestaurantsVM
import com.example.ebcom.utility.base.BaseFragment2
import com.example.ebcom.utility.extentions.showToast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestaurantsDetailsFragment: BaseFragment2<FragmentRestaurantDetailsBinding>() {
    override val layoutId: Int get() = R.layout.fragment_restaurant_details
    val viewModel : RestaurantsVM by  viewModel()

    companion object{
        const val RESTAURANT_DETAILS = "RESTAURANT_DETAILS"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val restaurant = requireArguments().getSerializable(RESTAURANT_DETAILS) as Restaurant
        showToast(requireContext(),restaurant.name)

    }
}