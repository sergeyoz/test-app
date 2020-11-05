package com.example.vkfeed.presentation.view.main

import android.os.Bundle
import android.os.PersistableBundle
import com.example.vkfeed.R
import com.example.vkfeed.databinding.ActivityMainBinding
import com.example.vkfeed.di.DependencyContainer
import com.example.vkfeed.di.DependencyProvider
import com.example.vkfeed.di.provide
import com.example.vkfeed.domain.AppDataSource
import com.example.vkfeed.domain.usecase.IsAuthorizedUseCase
import com.example.vkfeed.presentation.base.BindingActivity
import com.example.vkfeed.presentation.navigation.NavigationManager
import com.example.vkfeed.presentation.navigation.NavigationStrategy
import com.example.vkfeed.presentation.view.newsList.NewsListFragment
import com.example.vkfeed.presentation.view.login.LoginFragment

class MainActivity : BindingActivity<ActivityMainBinding>() {

    override fun inflateBinding() = ActivityMainBinding.inflate(layoutInflater)
    private val navigationManager: NavigationManager by provide()
    private val isAuthorized: IsAuthorizedUseCase by provide()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationManager.route(R.id.navigation_root, supportFragmentManager)
        if (savedInstanceState == null) {
            if (isAuthorized()) {
                navigationManager.navigate(NavigationStrategy.Replace(NewsListFragment::class.java))
            } else {
                navigationManager.navigate(NavigationStrategy.Replace(LoginFragment::class.java))
            }
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count <= 1) {
            finish()
        } else {
            navigationManager.navigate(NavigationStrategy.Back)
        }
    }

    override fun initContainer(parentContainer: DependencyContainer?): DependencyContainer? {
        return MainContainer(parentContainer)
    }

    override fun onDestroy() {
        navigationManager.unRoute()
        super.onDestroy()
    }


}