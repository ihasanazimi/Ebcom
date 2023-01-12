package com.example.ebcom.utility.base

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.ebcom.utility.util.localizedContext

abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity(), BaseView {

    override val rootView: ViewGroup? get() = window.decorView.rootView as ViewGroup
    override val viewContext: Context? get() = this
    private var _binding: V? = null
    val binding get() = _binding!!

    @get:LayoutRes
    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,layoutId)
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(localizedContext(context))
    }

    override fun onStart() {
        super.onStart()
        localizedContext(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}