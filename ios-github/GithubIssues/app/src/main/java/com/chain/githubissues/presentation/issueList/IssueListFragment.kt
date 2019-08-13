package com.chain.githubissues.presentation.issueList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chain.githubissues.Injector
import com.chain.githubissues.R
import com.chain.githubissues.databinding.IssueListFragmentBinding
import com.chain.githubissues.di.ViewModelFactory
import com.chain.githubissues.presentation.common.BaseFragment
import javax.inject.Inject

class IssueListFragment : BaseFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val issueListViewModel: IssueListViewModel by lazy {
        viewModelFactory.getFragmentScopedViewModel(IssueListViewModel::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().injectInto(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = IssueListFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = issueListViewModel

        setupRecyclerView(binding.root)

        return binding.root
    }

    private fun setupRecyclerView(root: View) {
        val animation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

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
}