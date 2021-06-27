package com.sample.rss.screens.details

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.sample.rss.R
import com.sample.rss.common.base.BaseFragment
import com.sample.rss.common.base.nav
import com.sample.rss.common.ext.openUrl
import com.sample.rss.databinding.DetailsFragmentBinding
import com.sample.rss.room.view.RssItemView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : BaseFragment<DetailsFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DetailsFragmentBinding
        get() = DetailsFragmentBinding::inflate

    override val includeMenu: Boolean
        get() = true

    private val link: String get() = DetailsFragmentArgs.fromBundle(args).link

    override val viewModel: DetailsViewModel by viewModels()

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
        if (item.isViewed.not()) {
            viewModel.setViewed()
        }
    }

    private val onDeletedObserver = Observer<Boolean> { wasDeleted ->
        if (wasDeleted) {
            nav.popBackStack()
        } else {
            showDeleteErrorAlert()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState).also {
            viewModel.item.observe(viewLifecycleOwner, itemObserver)
            viewModel.onDeleted.observe(viewLifecycleOwner, onDeletedObserver)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setLink(link)
        binding.tvLink.setOnClickListener {
            viewModel.item.value?.link?.let { activity?.openUrl(it) }
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

    private fun showDeleteErrorAlert() {
        AlertDialog.Builder(activity)
            .setTitle(R.string.delete)
            .setMessage(R.string.delete_error)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(
                android.R.string.ok
            ) { _, _ -> viewModel.deleteNews() }
            .setNegativeButton(android.R.string.cancel, null).show()
    }
}