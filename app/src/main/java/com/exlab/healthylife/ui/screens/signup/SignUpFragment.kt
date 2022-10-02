package com.exlab.healthylife.ui.screens.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.exlab.healthylife.R
import com.exlab.healthylife.app.base.ui.BaseFragment
import com.exlab.healthylife.databinding.FragmentSignUpBinding
import com.exlab.healthylife.models.Account
import com.exlab.healthylife.utils.observeEvent
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    override val viewModel by viewModels<SignUpViewModel>()
    private val viewBinding by viewBinding(FragmentSignUpBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        observeGoBackEvent()
        observeShowSuccessSignUpMessageEvent()

        with(viewBinding) {
            bBack.setOnClickListener { findNavController().popBackStack() }

            // (?=.*[a-z]) - at least one lowercase letter
            // (?=.*\\d) - at least one numeric value.
            // (?=.*[A-Z]) - at least one uppercase letter
            // text.length >= 8 - at least 8 characters

            etPassword.doOnTextChanged { text, start, before, count ->
                groupPasswordHints.isVisible = !text.isNullOrEmpty()
                val (uppercaseValid, lowercaseValid, digitValid, lengthValid) = listOf(
                    text?.contains(Regex("(?=.*[A-Z])")) ?: false,
                    text?.contains(Regex("(?=.*[a-z])")) ?: false,
                    text?.contains(Regex("(?=.*\\d)")) ?: false,
                    (text?.length ?: 0) >= 8
                )
                if (uppercaseValid) {
                    tvCapitalLetterRequirement.apply {
                        setTextColor(requireContext().getColor(R.color.blue_base))
                        setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.tick_icon, 0, 0, 0
                        )
                    }
                } else {
                    tvCapitalLetterRequirement.apply {
                        setTextColor(requireContext().getColor(R.color.dark_gray))
                        setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.round_icon, 0, 0, 0
                        )
                    }
                }
            }
        }


    }

    private fun onCreateAccountButtonPressed() {
        val account = Account(
            email = viewBinding.etEmail.text.toString(),
            password = viewBinding.etPassword.text.toString(),
        )
        viewModel.signUp(account)
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) { state ->
        viewBinding.tilEmail.isEnabled = state.enableViews
        viewBinding.tilPassword.isEnabled = state.enableViews

        fillError(viewBinding.tilEmail, state.emailErrorMessageRes)
        fillError(viewBinding.tilPassword, state.passwordErrorMessageRes)

        viewBinding.progressBar.visibility =
            if (state.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun observeShowSuccessSignUpMessageEvent() =
        viewModel.showToastEvent.observeEvent(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

    private fun fillError(input: TextInputLayout, @StringRes stringRes: Int) {
        if (stringRes == SignUpViewModel.NO_ERROR_MESSAGE) {
            input.error = null
            input.isErrorEnabled = false
        } else {
            input.error = getString(stringRes)
            input.isErrorEnabled = true
        }
    }

    private fun observeGoBackEvent() = viewModel.goBackEvent.observeEvent(viewLifecycleOwner) {
        findNavController().popBackStack()
    }

}