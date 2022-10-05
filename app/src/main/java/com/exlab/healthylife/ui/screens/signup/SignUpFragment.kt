package com.exlab.healthylife.ui.screens.signup

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.exlab.healthylife.R
import com.exlab.healthylife.app.base.ui.BaseFragment
import com.exlab.healthylife.databinding.FragmentSignUpBinding
import com.exlab.healthylife.models.Account
import com.exlab.healthylife.utils.observeEvent
import com.exlab.healthylife.utils.validators.EmailValidator
import com.exlab.healthylife.utils.validators.PasswordValidator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    override val viewModel by viewModels<SignUpViewModel>()
    private val viewBinding by viewBinding(FragmentSignUpBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeGoBackEvent()
        observeShowSuccessSignUpMessageEvent()

        with(viewBinding) {
            observeState()
            addBackButtonAction()
            observeEmail()
            observeEmailFocus()
            observePassword()
            observeDataProcessing()
            observeAgreePolicy()
            observeTripleValid()
            onCreateAccountPressed()
        }
    }

    private fun FragmentSignUpBinding.onCreateAccountPressed() {
        bCreateAccount.setOnClickListener {
            val accountData = Account(
                email = etEmail.text.toString(),
                password = etPassword.text.toString()
            )
            viewModel.signUp(accountData)
        }
    }

    private fun FragmentSignUpBinding.addBackButtonAction() {
        bBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun FragmentSignUpBinding.observeAgreePolicy() {
        cbAgreePolicy.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setAgreePolicyActivated(isChecked)
        }
    }

    private fun FragmentSignUpBinding.observeEmailFocus() {
        etEmail.onFocusChangeListener = onFocusChangeListener()
    }

    private fun FragmentSignUpBinding.onFocusChangeListener() =
        View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && !EmailValidator.isValidEmail(etEmail.text.toString())) tilEmail.error =
                getString(R.string.error_email) else tilEmail.error = null
        }

    private fun FragmentSignUpBinding.observeEmail() {
        etEmail.doOnTextChanged { text, _, _, _ ->
            val isRegexMatchesPattern = EmailValidator.isValidEmail(text)
            viewModel.setEmailValid(isRegexMatchesPattern)
        }
    }

    private fun FragmentSignUpBinding.observeDataProcessing() {
        cbAgreeDataProcessing.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setAgreeDataProcessingActivated(isChecked)
        }
    }

    private fun FragmentSignUpBinding.observePassword() {
        etPassword.doOnTextChanged { text, _, _, _ ->
            val (uppercaseValid, lowercaseValid, digitValid, lengthValid) = listOf(
                PasswordValidator.isContainsUppercaseLetter(text.toString()),
                PasswordValidator.isContainsLowercaseLetter(text.toString()),
                PasswordValidator.isContainsDigit(text.toString()),
                PasswordValidator.isValidLength(text.toString())
            )
            tvCapitalLetterRequirement.apply { if (uppercaseValid) setValidDrawable() else setInvalidDrawable() }
            tvLowercaseLetterRequirement.apply { if (lowercaseValid) setValidDrawable() else setInvalidDrawable() }
            tvDigitRequirement.apply { if (digitValid) setValidDrawable() else setInvalidDrawable() }
            tvCharsAmountRequirement.apply { if (lengthValid) setValidDrawable() else setInvalidDrawable() }

            viewModel.setPasswordValid(uppercaseValid && lowercaseValid && digitValid && lengthValid)
        }
    }

    private fun FragmentSignUpBinding.observeTripleValid() {
        viewModel.fieldsTripleValid.observe(viewLifecycleOwner) {
            bCreateAccount.apply {
                isClickable =
                    if (it.first?.first == true && it.first?.second == true && it.second == true && it.third == true) {
                        setBackgroundColor(requireContext().getColor(R.color.blue_base))
                        true
                    } else {
                        setBackgroundColor(requireContext().getColor(R.color.dark_gray))
                        false
                    }
            }
        }
    }

    private fun TextView.setInvalidDrawable() {
        setTextColor(requireContext().getColor(R.color.dark_gray))
        setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.round_icon, 0, 0, 0
        )
    }

    private fun TextView.setValidDrawable() {
        setTextColor(requireContext().getColor(R.color.blue_base))
        setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.tick_icon, 0, 0, 0
        )
    }

    private fun FragmentSignUpBinding.observeState() = viewModel.state.observe(viewLifecycleOwner) { state ->
        tilEmail.isEnabled = state.enableViews
        tilPassword.isEnabled = state.enableViews

        progressBar.visibility =
            if (state.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun observeShowSuccessSignUpMessageEvent() =
        viewModel.showToastEvent.observeEvent(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

    private fun observeGoBackEvent() = viewModel.goBackEvent.observeEvent(viewLifecycleOwner) {
        findNavController().popBackStack()
    }

}

