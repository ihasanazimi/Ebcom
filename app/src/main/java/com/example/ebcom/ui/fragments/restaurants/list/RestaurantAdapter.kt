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
import kotlin.random.Random

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

        val fakeImages = arrayListOf<Int>(
            R.drawable.p2,
            R.drawable.p3,
            R.drawable.p4,
            R.drawable.p5,
            R.drawable.p6,
            R.drawable.p8,
            R.drawable.p9,
            R.drawable.p10,
            R.drawable.p11,
        )

        fun bind(model : Restaurant){

            binding.data = model
            statusTextColor(model)
            favoriteStateChanging(model)

            // for beautifully
            Glide.with(binding.ivRestaurantCover.context)
                .load(binding.ivRestaurantCover.context.getDrawable(fakeImages[Random.nextInt(0,fakeImages.size-1)]))
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.ic_baseline_downloading_24)
                .into(binding.ivRestaurantCover)

        }


        private fun statusTextColor(model: Restaurant) {
            when(model.status){
                RestaurantOrderState.OPEN.status -> binding.tvRestaurantOpenOrClosedState.setTextColor(binding.root.context.resources.getColor(R.color.teal_200))
                RestaurantOrderState.CLOSED.status -> binding.tvRestaurantOpenOrClosedState.setTextColor(binding.root.context.resources.getColor(R.color.red))
                RestaurantOrderState.AHEAD.status -> binding.tvRestaurantOpenOrClosedState.setTextColor(binding.root.context.resources.getColor(R.color.gray))
                else -> binding.tvRestaurantOpenOrClosedState.setTextColor(binding.root.context.resources.getColor(R.color.gray))
            }
        }

        private fun favoriteStateChanging(restaurant: Restaurant){
            when (restaurant.favorite){
                1 -> binding.favoriteToggleBtn.setChecked()
                0 -> binding.favoriteToggleBtn.setUnchecked()
            }
        }
    }

}