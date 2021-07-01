package com.sample.rss.screens.details

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.sample.rss.R
import com.sample.rss.common.ActionResult
import com.sample.rss.common.ActionResultDone
import com.sample.rss.common.ActionResultFailed
import com.sample.rss.common.base.BaseFragment
import com.sample.rss.common.base.nav
import com.sample.rss.common.ext.openUrl
import com.sample.rss.coroutines.launchWhetStarted
import com.sample.rss.databinding.DetailsFragmentBinding
import com.sample.rss.room.view.RssItemView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import kotlin.math.roundToInt

@AndroidEntryPoint
class DetailsFragment : BaseFragment<DetailsFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DetailsFragmentBinding
        get() = DetailsFragmentBinding::inflate

    override val includeMenu: Boolean
        get() = true

    private val guid: String get() = DetailsFragmentArgs.fromBundle(args).guid

    override val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.countdown.timerStateFlow.onEach {
            binding.percent = it.progressPercentage.roundToInt()
        }.launchWhetStarted(lifecycleScope)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> deleteWithConfirmation()
        }
        return super.onOptionsItemSelected(item)
    }

    private val itemObserver = Observer<RssItemView> { item ->
        binding.item = item
        if(!item.isViewed) { viewModel.startViewedCountDown() }
    }

    private val onDeletedObserver = Observer<ActionResult> { result ->
        when (result) {
            is ActionResultDone -> nav.popBackStack()
            is ActionResultFailed -> showDeleteErrorAlert(result.throwable)
        }
    }

    private val onViewedObserver = Observer<ActionResult> { result ->
        when (result) {
            is ActionResultDone -> {
                // @TODO news marked as viewed
            }
            is ActionResultFailed -> showDeleteErrorAlert(result.throwable)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState).also {
            viewModel.setGuid(guid)
            viewModel.rssItem.observe(viewLifecycleOwner, itemObserver)
            viewModel.onViewed.observe(viewLifecycleOwner, onViewedObserver)
            viewModel.onDeleted.observe(viewLifecycleOwner, onDeletedObserver)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvLink.setOnClickListener {
            viewModel.rssItem.value?.link?.let { activity?.openUrl(it) }
        }
    }

    private fun deleteWithConfirmation() {
        AlertDialog.Builder(activity)
            .setTitle(R.string.delete)
            .setMessage(R.string.delete_confirmation)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(
                android.R.string.ok
            ) { _, _ -> viewModel.deleteNews() }
            .setNegativeButton(android.R.string.cancel, null).show()
    }

    private fun showDeleteErrorAlert(throwable: Throwable) {
        AlertDialog.Builder(activity)
            .setTitle(R.string.error)
            .setMessage(throwable.localizedMessage)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.ok) { d, _ -> d.dismiss() }
            .show()
    }
}