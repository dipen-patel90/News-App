package com.ct.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ct.base.BaseFragment
import com.ct.databinding.FragmentNewsDescriptionBinding
import com.ct.viewmodel.HomeViewModel

class NewsDescriptionFragment : BaseFragment() {

    private var _binding: FragmentNewsDescriptionBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun initViews() {
    }

    override fun collectFlow() {
        collectFlow(homeViewModel.selectedHeadline) {
            binding.newsHeadline = it
        }
    }
}