package com.sample.rss.common

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.sample.rss.R
import com.sample.rss.databinding.ActivityMainBinding
import com.sample.rss.rss.RssAggregator
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null

    private val compositeDisposable = CompositeDisposable()

    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }

    private val binding: ActivityMainBinding
        get() = _binding as ActivityMainBinding

    @Inject
    lateinit var rssAggregator: RssAggregator

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
        syncFeed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> syncFeed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun syncFeed() {
        rssAggregator.startSync()
            .subscribeBy(
                { it.printStackTrace() },
                { /* All OK */ }
            ).addTo(compositeDisposable)
    }

    override fun onDestroy() {
        _binding = null
        compositeDisposable.dispose()
        super.onDestroy()
    }
}