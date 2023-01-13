package com.example.ebcom.ui.fragments.restaurants.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.ebcom.ApplicationLoader
import com.example.ebcom.R
import com.example.ebcom.databinding.ItemRestaurantBinding
import com.example.ebcom.model.Restaurant
import com.example.ebcom.model.seald.RestaurantOrderState
import com.example.ebcom.utility.TDiffUtilCallback
import com.example.ebcom.utility.customViews.ToggleImageView
import com.example.ebcom.utility.extentions.hide
import com.example.ebcom.utility.extentions.show

class RestaurantAdapter(val callBack : RestaurantClickEvent) : RecyclerView.Adapter<RestaurantAdapter.PostViewHolder>() {

    private var items = arrayListOf<Restaurant>()

    interface RestaurantClickEvent{
        fun onRestaurantClick(restaurant : Restaurant)
        fun onLikeRestaurant(restaurant : Restaurant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        holder.bind(items[position])

        holder.itemView.setOnClickListener{
            callBack.onRestaurantClick(items[holder.adapterPosition])
        }

        holder.binding.favoriteToggleBtn.addStateListener(object : ToggleImageView.OnStateChangedListener{
            override fun onChecked() {
                items[holder.adapterPosition].favorite = 1
                callBack.onLikeRestaurant(items[holder.adapterPosition])
            }
            override fun onUnchecked() {
                items[holder.adapterPosition].favorite = 0
                callBack.onLikeRestaurant(items[holder.adapterPosition])
            }
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun removeItem(model: Restaurant){
        val index = items.indexOf(model)
        items.removeAt(index)
        notifyItemRemoved(index)
        ApplicationLoader.applicationHandler.postDelayed({notifyDataSetChanged()},200)
    }

    fun addItem(model: Restaurant){
        items.add(model)
        notifyItemInserted(itemCount)
        ApplicationLoader.applicationHandler.postDelayed({notifyDataSetChanged()},200)
    }

    fun setItemByDiffUtil(list: List<Restaurant>){
        val diffCallback = TDiffUtilCallback(this.items, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items.clear()
        this.items.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun addItems(items: ArrayList<Restaurant>) {
        this.items.addAll(items)
        val startPos = this.items.size - items.size
        notifyItemRangeInserted(startPos, items.size)
    }

    fun updatedItem(updatedRestaurant: Restaurant) {
        val targetRestaurantBeforeChanged = items.find { it.id == updatedRestaurant.id }
        val index = items.indexOf(targetRestaurantBeforeChanged)
        this.items[index] = updatedRestaurant
        notifyDataSetChanged()
    }


    fun clearList(){
        items.clear()
        ApplicationLoader.applicationHandler.postDelayed({notifyDataSetChanged()},200)
    }




    inner class PostViewHolder(val binding: ItemRestaurantBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(model : Restaurant){

            binding.data = model
            restaurantOpenClosAheadUiState(model)
            favoriteIconState(model)
            restaurantBackgroundColor(model)

            // for beautifully
            Glide.with(binding.ivRestaurantCover.context)
                .load(binding.ivRestaurantCover.context.getDrawable(R.drawable.p3))
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.ic_baseline_downloading_24)
                .into(binding.ivRestaurantCover)

        }


        private fun restaurantOpenClosAheadUiState(model: Restaurant) {
            when(model.status){
                RestaurantOrderState.OPEN.status -> {
                    binding.tvRestaurantOpenOrClosedState.setTextColor(binding.root.context.resources.getColor(R.color.green))
                    binding.closedIv.hide()
                    binding.deliveryCostsContainer.show()
                    binding.restaurantMessage.apply {
                        show()
                        text = binding.root.context.getString(R.string.open_message)
                    }
                    binding.tvRestaurantOpenOrClosedState.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                }
                RestaurantOrderState.CLOSED.status -> {
                    binding.tvRestaurantOpenOrClosedState.setTextColor(binding.root.context.resources.getColor(R.color.red))
                    binding.closedIv.show()
                    binding.deliveryCostsContainer.hide()
                    binding.restaurantMessage.apply {
                        show()
                        text = binding.root.context.getString(R.string.close_message)
                    }
                    binding.tvRestaurantOpenOrClosedState.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                }
                RestaurantOrderState.AHEAD.status -> {
                    binding.tvRestaurantOpenOrClosedState.setTextColor(binding.root.context.resources.getColor(R.color.gray_very_dark))
                    binding.closedIv.hide()
                    binding.deliveryCostsContainer.show()
                    binding.restaurantMessage.apply {
                        show()
                        text = binding.root.context.getString(R.string.order_ahead_message)
                    }
                    binding.tvRestaurantOpenOrClosedState.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_access_time_24, 0, 0, 0)
                }
                else -> binding.tvRestaurantOpenOrClosedState.setTextColor(binding.root.context.resources.getColor(R.color.gray))
            }
        }

        private fun restaurantBackgroundColor(model: Restaurant){
            when(model.status){
                RestaurantOrderState.OPEN.status -> binding.cardViewBg.setCardBackgroundColor(binding.root.context.resources.getColor(R.color.green_transparently))
                RestaurantOrderState.CLOSED.status -> binding.cardViewBg.setCardBackgroundColor(binding.root.context.resources.getColor(R.color.red_transparently))
                RestaurantOrderState.AHEAD.status -> binding.cardViewBg.setCardBackgroundColor(binding.root.context.resources.getColor(R.color.gray_transparently))
                else -> binding.cardViewBg.setCardBackgroundColor(binding.root.context.resources.getColor(R.color.white_background))
            }
        }

        private fun favoriteIconState(restaurant: Restaurant){
            when (restaurant.favorite){
                1 -> binding.favoriteToggleBtn.setChecked()
                0 -> binding.favoriteToggleBtn.setUnchecked()
            }
        }
    }

}