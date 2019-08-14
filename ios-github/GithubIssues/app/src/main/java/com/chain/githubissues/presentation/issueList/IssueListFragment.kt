package com.chain.githubissues.presentation.issueList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chain.githubissues.Injector
import com.chain.githubissues.R
import com.chain.githubissues.databinding.IssueListFragmentBinding
import com.chain.githubissues.di.ViewModelFactory
import com.chain.githubissues.domain.entity.IssueState
import com.chain.githubissues.domain.entity.Repository
import com.chain.githubissues.presentation.common.BaseFragment
import com.jakewharton.rxbinding3.recyclerview.scrollEvents
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class IssueListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val issueListViewModel: IssueListViewModel by lazy {
        viewModelFactory.getFragmentScopedViewModel(IssueListViewModel::class)
    }

    private val retrofit = Repository("square", "retrofit")
    private lateinit var listIssueStateSwitchMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().injectInto(this)
        setHasOptionsMenu(true)

        issueListViewModel.requestIssuesOf(retrofit)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.issue_list_menu, menu)
        listIssueStateSwitchMenuItem = menu.findItem(R.id.issueState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.issueState -> switchIssueStateFilter(item)
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
                    Toast.makeText(context, "${issue.title}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun switchIssueStateFilter(item: MenuItem): Boolean {
        val currentIssueState = IssueState.from(item.title)
        val newIssueState = currentIssueState.flip()

        issueListViewModel.requestIssuesOf(retrofit, newIssueState)
        listIssueStateSwitchMenuItem.title = newIssueState.toString()

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
        .filter {
            val time = it.time()

            time > 700
        }
        .subscribe { callback() }
}












