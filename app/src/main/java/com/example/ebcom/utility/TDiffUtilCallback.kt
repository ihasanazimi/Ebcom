package com.example.ebcom.utility

import androidx.recyclerview.widget.DiffUtil

class TDiffUtilCallback<T>(oldList: List<T>, newList: List<T>) : DiffUtil.Callback() {

    private val mOldList: List<T>
    private val mNewList: List<T>

    init {
        mOldList = oldList
        mNewList = newList
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = mOldList[oldItemPosition]
        val new = mNewList[oldItemPosition]
        if (old is DiffUtilModel && new is DiffUtilModel) return old.areItemsTheSame(new)
        return mOldList[oldItemPosition] === mNewList[newItemPosition]
    }


    override fun getOldListSize(): Int = mOldList.size
    override fun getNewListSize(): Int = mNewList.size


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldList: T = mOldList[oldItemPosition]
        val newList: T = mNewList[newItemPosition]
        if (oldList is DiffUtilModel && newList is DiffUtilModel) return oldList.areContentsTheSame(newList)
        return (oldList === null && newList === null) || (oldList !== null && oldList === newList)
    }


    interface DiffUtilModel {
        fun areItemsTheSame(o: DiffUtilModel): Boolean
        fun areContentsTheSame(o: DiffUtilModel): Boolean
    }
}

