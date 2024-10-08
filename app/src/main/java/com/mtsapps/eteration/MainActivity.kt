package com.mtsapps.eteration

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mtsapps.eteration.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                getColor(R.color.myBlue),
                getColor(R.color.myBlue)
            )
        )
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
        val navView: BottomNavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)

        observeBadgeCount()
        observeFavCount()
    }
    private fun observeBadgeCount(){
         lifecycleScope.launch {
            viewModel.badgeCount.collect {
                if (it != 0) {
                    binding.navView.getOrCreateBadge(R.id.navigation_cart).number = it
                }else{
                    binding.navView.removeBadge(R.id.navigation_cart)

                }
            }
        }
    }
    private fun observeFavCount(){
        lifecycleScope.launch {
            viewModel.favcount.collect {
                if (it != 0) {
                    binding.navView.getOrCreateBadge(R.id.navigation_favourites).number = it
                }else{
                    binding.navView.removeBadge(R.id.navigation_favourites)

                }
            }
        }
    }

}