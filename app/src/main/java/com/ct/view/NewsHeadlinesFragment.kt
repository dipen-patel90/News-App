package com.ct.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ct.base.BaseFragment
import com.ct.databinding.FragmentNewsHeadlinesBinding

class NewsHeadlinesFragment : BaseFragment() {

    private var _binding: FragmentNewsHeadlinesBinding? = null
    private val binding get() = _binding!!

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
    }

    override fun collectFlow() {
    }
}