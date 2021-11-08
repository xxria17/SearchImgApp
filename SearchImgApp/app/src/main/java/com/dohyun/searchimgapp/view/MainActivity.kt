package com.dohyun.searchimgapp.view

import android.os.Bundle
import androidx.fragment.app.commitNow
import com.dohyun.searchimgapp.R
import com.dohyun.searchimgapp.databinding.ActivityMainBinding
import com.dohyun.searchimgapp.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.commitNow(true) {
            val mainFragment = MainFragment()
            replace(R.id.main_content, mainFragment)
            setPrimaryNavigationFragment(mainFragment)
        }
    }
}