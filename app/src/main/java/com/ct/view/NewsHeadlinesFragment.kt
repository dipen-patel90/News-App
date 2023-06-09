package com.ct.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ct.R
import com.ct.adapter.HeadlineListAdapter
import com.ct.api.APIResponse
import com.ct.base.BaseFragment
import com.ct.databinding.FragmentNewsHeadlinesBinding
import com.ct.extention.addHorizontalIndicator
import com.ct.extention.addVerticalIndicator
import com.ct.viewmodel.HomeViewModel

/**
* Fragment to display news headlines in the list
* */
class NewsHeadlinesFragment : BaseFragment() {

    private var _binding: FragmentNewsHeadlinesBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()

    private lateinit var headlineListAdapter: HeadlineListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsHeadlinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        // "descriptionFragmentContainer" available in large screens only
        binding.descriptionFragmentContainer?.let {
            // For large screen add NewsDescriptionFragment and show news detail in right pane
            childFragmentManager.beginTransaction()
                .replace(it.id, NewsDescriptionFragment())
                .commit()
        }
    }

    override fun initViews() {
        binding.apply {
            headlineListAdapter = HeadlineListAdapter(
                isLargeScreen = resources.getBoolean(R.bool.isLargeScreen),
                onItemClick = { selectedHeadline ->
                    // Update the selected item in list and description screen
                    homeViewModel.updateListSelection(selectedHeadline)

                    if (descriptionFragmentContainer == null) {
                        // If description screen is not available in current fragment then launch new screen
                        navigateToDescriptionFragment()
                    }
                })
            newsHeadlineRv.adapter = headlineListAdapter
            newsHeadlineRv.addVerticalIndicator()
            newsHeadlineRv.addHorizontalIndicator()

            // Implemented pull to refresh
            srl.setOnRefreshListener {
                homeViewModel.getTopHeadlines()
            }
        }
    }

    override fun collectFlow() {

        collectFlow(homeViewModel.newsHeadlines) {
            when (it) {
                is APIResponse.Loading -> {
                    binding.srl.isRefreshing = true
                }

                is APIResponse.Failure -> {
                    binding.srl.isRefreshing = false
                    showMessage(
                        requireView(),
                        it.error ?: getString(R.string.something_went_wrong)
                    )
                }

                is APIResponse.Success -> {
                    binding.srl.isRefreshing = false
                    headlineListAdapter.setData(it.data ?: arrayListOf())
                }
            }
        }
    }

    private fun navigateToDescriptionFragment() {
        navigate(NewsHeadlinesFragmentDirections.actionNewsHeadlinesFragmentToNewsDescriptionFragment())
    }
}