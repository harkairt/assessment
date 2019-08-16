package com.chain.githubissues.presentation.issueDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.chain.githubissues.Injector
import com.chain.githubissues.databinding.IssueDetailFragmentBinding
import com.chain.githubissues.di.ViewModelFactory
import com.chain.githubissues.domain.entity.Repository
import com.chain.githubissues.presentation.common.BaseFragment
import com.chain.githubissues.presentation.common.IssueIdentifier
import com.chain.githubissues.util.issueIdentifierKey
import javax.inject.Inject

class IssueDetailFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val issueDetailViewModel: IssueDetailViewModel by lazy {
        viewModelFactory.getFragmentScopedViewModel(IssueDetailViewModel::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().injectInto(this)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = IssueDetailFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = issueDetailViewModel

        arguments?.run {
            val issueIdentifier : IssueIdentifier = getParcelable(issueIdentifierKey)
            issueDetailViewModel.requestIssue(issueIdentifier)
        }

        return binding.root
    }
}

