package com.exlab.healthylife.ui.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.exlab.healthylife.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import by.kirich1409.viewbindingdelegate.viewBinding
import com.exlab.healthylife.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivityMainBinding::bind)
    private var navController: NavController? = null
    private val topLevelDestinations = setOf(getAuthDestination(), getTabsDestination())
    private val viewModel: MainViewModel by viewModels()


    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            // don't forget to add Main Screen Fragment
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            if (f is NavHostFragment) return
            onNavControllerActivated(f.findNavController())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSplashScreen()
        installSplashScreen()
        setContentView(R.layout.activity_main)
        val navController = getRootNavController()
        prepareRootNavController(viewModel.isSignedIn(), navController)
        onNavControllerActivated(navController)
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, true)
        onBackPressedActivate(navController)
    }

    private fun setupSplashScreen() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
    }

    private fun onBackPressedActivate(navController: NavController) {
        onBackPressed(true) {
            if (navController.currentDestination?.id == getAuthDestination()) finish()
            else navController.popBackStack()
        }
    }

    private fun AppCompatActivity.onBackPressed(isEnabled: Boolean, callback: () -> Unit) {
        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(isEnabled) {
                override fun handleOnBackPressed() {
                    callback()
                }
            })
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
        navController = null
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean =
        (navController?.navigateUp() ?: false) || super.onSupportNavigateUp()

    private fun prepareRootNavController(isSignedIn: Boolean, navController: NavController) {
        val graph = navController.navInflater.inflate(getMainNavigationGraphId())
        graph.setStartDestination(
            if (isSignedIn) {
                getTabsDestination()
            } else {
                getAuthDestination()
            }
        )
        navController.graph = graph
    }

    private fun onNavControllerActivated(navController: NavController) {
        if (this.navController == navController) return
        this.navController?.removeOnDestinationChangedListener(destinationListener)
        navController.addOnDestinationChangedListener(destinationListener)
        this.navController = navController
    }

    private fun getRootNavController(): NavController {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        return navHost.navController
    }

    private val destinationListener =
        NavController.OnDestinationChangedListener { _, destination, arguments ->
            supportActionBar?.setDisplayHomeAsUpEnabled(!isStartDestination(destination))
        }

    private fun isStartDestination(destination: NavDestination?): Boolean {
        if (destination == null) return false
        val graph = destination.parent ?: return false
        val startDestinations = topLevelDestinations + graph.startDestinationId
        return startDestinations.contains(destination.id)
    }

    private fun getMainNavigationGraphId(): Int = R.navigation.main_graph

    private fun getAuthDestination(): Int = R.id.signUpFragmentIntro

    private fun getTabsDestination(): Int = R.id.tabFragment
}