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
            childFragmentManager.beginTransaction()
                .replace(it.id, NewsDescriptionFragment())
                .commit()
        }
    }

    override fun initViews() {

        headlineListAdapter = HeadlineListAdapter(arrayListOf(), onItemClick = { selectedHeadline ->
            homeViewModel.selectedHeadline(selectedHeadline)

            if (binding.descriptionFragmentContainer == null) {
                navigateToDescriptionFragment()
            } else {
                homeViewModel.updateList(selectedHeadline)
            }
        })
        binding.newsHeadlineRv.adapter = headlineListAdapter
        binding.newsHeadlineRv.addVerticalIndicator()
        binding.newsHeadlineRv.addHorizontalIndicator()
    }

    override fun collectFlow() {

        collectFlow(homeViewModel.newsHeadlines) {
            when (it) {
                is APIResponse.Loading -> {
                    binding.progressIndicator.show()
                }

                is APIResponse.Failure -> {
                    binding.progressIndicator.hide()
                    showMessage(
                        requireView(),
                        it.error ?: getString(R.string.something_went_wrong)
                    )
                }

                is APIResponse.Success -> {
                    binding.progressIndicator.hide()
                    headlineListAdapter.setData(it.data ?: arrayListOf())
                }
            }
        }
    }

    private fun navigateToDescriptionFragment() {
        navigate(NewsHeadlinesFragmentDirections.actionNewsHeadlinesFragmentToNewsDescriptionFragment())
    }
}