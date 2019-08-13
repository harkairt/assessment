package com.chain.githubissues.presentation.issueList

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chain.githubissues.Injector
import com.chain.githubissues.R
import com.chain.githubissues.databinding.IssueListFragmentBinding
import com.chain.githubissues.di.ViewModelFactory
import com.chain.githubissues.domain.entity.IssueState
import com.chain.githubissues.presentation.common.BaseFragment
import javax.inject.Inject

class IssueListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val issueListViewModel: IssueListViewModel by lazy {
        viewModelFactory.getFragmentScopedViewModel(IssueListViewModel::class)
    }

    lateinit var listIssueStateSwitchMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().injectInto(this)
        setHasOptionsMenu(true)
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

        setupRecyclerView(binding.root)

        return binding.root
    }

    private fun setupRecyclerView(root: View) {
        val animation =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

        root.findViewById<RecyclerView>(R.id.issuesRecyclerView).run {
            setHasFixedSize(false)
            layoutAnimation = animation
            layoutManager = LinearLayoutManager(this@IssueListFragment.context)
            adapter = IssueListAdapter {
                onClickAction = { issue ->
                    Toast.makeText(
                        root.context,
                        "${issue.title}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun switchIssueStateFilter(item: MenuItem): Boolean {
        val currentIssueState = IssueState.from(item.title)
        val newIssueState = currentIssueState.flip()

        issueListViewModel.requestIssueList(newIssueState)
        listIssueStateSwitchMenuItem.title = newIssueState.toString()

        return true
    }

}