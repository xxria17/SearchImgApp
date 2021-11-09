package com.dohyun.searchimgapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dohyun.searchimgapp.R
import com.dohyun.searchimgapp.databinding.FragmentMainBinding
import com.dohyun.searchimgapp.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
    }
}