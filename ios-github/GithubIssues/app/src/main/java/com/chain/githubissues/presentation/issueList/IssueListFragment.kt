package com.chain.githubissues.presentation.issueList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chain.githubissues.Injector
import com.chain.githubissues.R
import com.chain.githubissues.databinding.IssueListFragmentBinding
import com.chain.githubissues.di.ViewModelFactory
import com.chain.githubissues.domain.entity.Issue
import com.chain.githubissues.domain.entity.IssueState
import com.chain.githubissues.domain.entity.Repository
import com.chain.githubissues.presentation.common.BaseFragment
import com.chain.githubissues.presentation.common.issueIdentifier
import com.chain.githubissues.util.issueIdentifierKey
import com.jakewharton.rxbinding3.recyclerview.scrollEvents
import javax.inject.Inject

class IssueListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val issueListViewModel: IssueListViewModel by lazy {
        viewModelFactory.getFragmentScopedViewModel(IssueListViewModel::class)
    }

    private val retrofit = Repository("square", "retrofit")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().injectInto(this)
        setHasOptionsMenu(true)

        issueListViewModel.requestIssuesOf(retrofit)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.issue_list_menu, menu)

        issueListViewModel.activeIssueState.observe(viewLifecycleOwner, Observer {
            menu.findItem(R.id.issueState)?.let {menuItem ->
                menuItem.title = it.toString()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.issueState -> requestIssuesWithState(IssueState.from(item.title).flip())
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = IssueListFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = issueListViewModel

        val recyclerView = binding.root.findViewById<RecyclerView>(R.id.issuesRecyclerView)
        setupRecyclerView(recyclerView)

        return binding.root
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.run {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(this@IssueListFragment.context)
            onBottomReached { issueListViewModel.requestAdditionalIssues() }
            adapter = IssueListAdapter {
                onClickAction = { issue ->
                    navigateToDetailView(issue)
                }
            }
        }
    }

    private fun navigateToDetailView(issue: Issue) {
        findNavController().navigate(R.id.issueListToDetailAction, Bundle().apply {
            putParcelable(issueIdentifierKey, issue.issueIdentifier)
        })
    }

    private fun requestIssuesWithState(issueState: IssueState): Boolean {
        issueListViewModel.requestIssuesOf(retrofit, issueState)

        return true
    }
}

@SuppressLint("CheckResult")
fun RecyclerView.onBottomReached(offset: Int = 15, callback: () -> Unit) {
    val linearLayoutManager = this.layoutManager as LinearLayoutManager

    this.scrollEvents().filter {
        val totalItemCount = it.view.layoutManager!!.itemCount
        val lastVisibleIndex = linearLayoutManager.findLastVisibleItemPosition()

        totalItemCount <= lastVisibleIndex + offset
    }
        .timeInterval()
        .filter { it.time() > 700 }
        .subscribe { callback() }
}












