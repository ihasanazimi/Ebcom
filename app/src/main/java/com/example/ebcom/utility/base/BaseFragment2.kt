package com.example.ebcom.utility.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment2<V : ViewDataBinding> : Fragment() {

    private var _binding: V? = null
    val binding get() = _binding!!
    @get:LayoutRes
    abstract val layoutId: Int
    val mainHelper by lazy { (requireActivity()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObservers()
    }


    open fun registerObservers(){}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    open fun onScrollToTop() {}

    open fun onRetrievedTag(retrievedTag: String) {}
}