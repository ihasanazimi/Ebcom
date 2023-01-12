package com.example.ebcom.ui

import android.os.Bundle
import com.example.ebcom.R
import com.example.ebcom.databinding.ActivityMainBinding
import com.example.ebcom.utility.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int get() = R.layout.activity_main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}