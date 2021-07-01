package com.sample.rss.common

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.sample.rss.R
import com.sample.rss.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null

    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }

    private val viewModel: MainViewModel by viewModels()

    private val binding: ActivityMainBinding
        get() = _binding as ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        viewModel.syncFeed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> viewModel.syncFeed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}