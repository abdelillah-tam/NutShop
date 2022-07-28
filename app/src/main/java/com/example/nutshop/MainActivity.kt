package com.example.nutshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.nutshop.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHost: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.navigationBarColor = resources.getColor(R.color.blue_900)
        navHost =
            supportFragmentManager.findFragmentById(R.id.fragment_container_for_fragments) as NavHostFragment


        val controllerInsets = WindowCompat.getInsetsController(window, window.decorView)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_page -> {
                    navHost.findNavController().navigate(R.id.home_page)
                    true
                }
                R.id.cart_page -> {
                    navHost.findNavController().navigate(R.id.cart_page)
                    true
                }
                R.id.favorite_page -> {
                    navHost.findNavController().navigate(R.id.favoriteFragment)
                    true
                }
                R.id.account_page -> {
                    if (Firebase.auth.currentUser != null) {
                        navHost.findNavController().navigate(R.id.profileFragment)
                    } else {
                        navHost.findNavController().navigate(R.id.loginFragment)
                    }
                    true
                }
                else -> false
            }
        }

        navHost.findNavController()
            .addOnDestinationChangedListener(NavController.OnDestinationChangedListener { _, destination, _ ->
                if(destination.id == R.id.home_page){
                    window.statusBarColor = resources.getColor(R.color.snow)
                    controllerInsets.isAppearanceLightStatusBars = true
                    binding.bottomNavigationView.visibility = View.VISIBLE
                    window.navigationBarColor = resources.getColor(R.color.blue_900)
                }
                else if (destination.id == R.id.searchFragment || destination.id == R.id.productDetailFragment) {
                    if(destination.id == R.id.productDetailFragment){
                        window.statusBarColor = resources.getColor(R.color.blue_900)
                        controllerInsets.isAppearanceLightStatusBars = false
                    }else{
                        window.statusBarColor = resources.getColor(R.color.snow)
                        controllerInsets.isAppearanceLightStatusBars = true
                    }
                    binding.bottomNavigationView.visibility = View.GONE
                } else binding.bottomNavigationView.visibility = View.VISIBLE
            })


    }
    override fun onSupportNavigateUp(): Boolean {
        return navHost.navController.navigateUp() || super.onNavigateUp()
    }

    override fun onBackPressed() {
        navHost.findNavController().navigateUp()
        super.onBackPressed()
    }


}