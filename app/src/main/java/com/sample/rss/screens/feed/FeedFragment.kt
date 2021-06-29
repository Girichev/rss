package com.sample.rss.screens.feed

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.rss.R
import com.sample.rss.common.base.BaseFragment
import com.sample.rss.common.base.nav
import com.sample.rss.databinding.FeedFragmentBinding
import com.sample.rss.room.view.RssItemView
import com.sample.rss.support.recyclerview.RecyclerItemClickListener
import com.sample.rss.support.recyclerview.addOnItemClickListener
import com.sample.rss.support.recyclerview.decorator.EdgeDecoration
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FeedFragment : BaseFragment<FeedFragmentBinding>(),
    RecyclerItemClickListener.OnItemClickListener, SearchView.OnQueryTextListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FeedFragmentBinding
        get() = FeedFragmentBinding::inflate

    override val viewModel: FeedViewModel by viewModels()

    override val includeMenu: Boolean
        get() = true

    private val feedAdapter = FeedAdapter()

    private lateinit var touchListener: RecyclerView.OnItemTouchListener

    private val listObserver = Observer<List<RssItemView>> { data ->
        feedAdapter.swapData(data)
    }

    private val loadingObserver = Observer<Boolean> { isLoading ->
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadingStatus.observe(viewLifecycleOwner, loadingObserver)
        viewModel.feed.observe(viewLifecycleOwner, listObserver)
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(EdgeDecoration(requireActivity(), R.dimen.rv_spacing_large))
            touchListener = addOnItemClickListener(requireContext(), this@FeedFragment)
            adapter = feedAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.feed_menu, menu)

        val item = menu.findItem(R.id.search)
        val sv = SearchView(actionBar.themedContext)
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
        sv.setOnQueryTextListener(this)
        sv.setIconifiedByDefault(false)
        item.actionView = sv
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        binding.rvItems.removeOnItemTouchListener(touchListener)
        super.onDestroyView()
    }

    override fun onItemClick(view: View, position: Int) {
        feedAdapter.getItem(position)?.let {
            nav.navigate(FeedFragmentDirections.actionFeedFragmentToDetailsFragment(it.guid))
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.search(newText)
        return true
    }
}