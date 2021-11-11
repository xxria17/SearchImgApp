package com.dohyun.searchimgapp.view

import android.os.Bundle
import com.dohyun.searchimgapp.R
import com.dohyun.searchimgapp.databinding.ActivityMainBinding
import com.dohyun.searchimgapp.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}