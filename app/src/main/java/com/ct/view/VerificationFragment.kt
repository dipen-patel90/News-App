package com.ct.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricPrompt
import com.ct.R
import com.ct.base.BaseFragment
import com.ct.databinding.FragmentVerificationBinding
import com.ct.model.ToolbarConfig
import com.ct.utils.BiometricUtils

class VerificationFragment : BaseFragment(), BiometricUtils.BiometricAuthListener {

    private var _binding: FragmentVerificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar(ToolbarConfig.Hide)
    }

    override fun onResume() {
        super.onResume()
        launchBiometricVerification()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun initViews() {
        binding.verifyBtn.setOnClickListener {
            launchBiometricVerification()
        }
    }

    override fun collectFlow() {
    }

    override fun onBiometricNotSupported() {
        showMessage(requireView(), getString(R.string.biometric_not_supported))
        navigateToHome()
    }

    override fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult) {
        showMessage(requireView(), getString(R.string.login_successful))
        navigateToHome()
    }

    override fun onBiometricAuthenticationError(errorCode: Int, errorMessage: String) {
        showMessage(requireView(), getString(R.string.login_failed_please_try_again))
    }

    private fun launchBiometricVerification() {
        BiometricUtils.showBiometricPrompt(
            activity = requireActivity(),
            listener = this,
        )
    }

    private fun navigateToHome() {
        navigate(VerificationFragmentDirections.actionVerificationFragmentToNewsHeadlinesFragment())
    }
}