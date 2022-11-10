package com.exlab.healthylife.ui.screens.signin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.exlab.healthylife.R
import com.exlab.healthylife.app.base.ui.BaseFragment
import com.exlab.healthylife.databinding.FragmentSignInBinding
import com.exlab.healthylife.databinding.FragmentSignUpBinding
import com.exlab.healthylife.utils.observeEvent
import com.exlab.healthylife.utils.publishEvent
import com.exlab.healthylife.utils.validators.EmailValidator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    override val viewModel by viewModels<SignInViewModel>()
    private val viewBinding by viewBinding(FragmentSignInBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            onRegisterButtonPressed()
            onSignInPressed()
            observeEmailFocus()
            addBackButtonAction()
            observeState()
            observeClearPasswordEvent()
        }
        observeShowAuthErrorMessageEvent()
        observeNavigateToTabsEvent()
    }

    private fun FragmentSignInBinding.addBackButtonAction() {
        bBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun FragmentSignInBinding.observeEmailFocus() {
        etEmail.onFocusChangeListener = onFocusChangeListenerEmail()
    }

    private fun FragmentSignInBinding.onFocusChangeListenerEmail() =
        View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && !EmailValidator.isValidEmail(etEmail.text.toString())) {
                tilEmail.error =
                    getString(R.string.error_email)
                etEmail.setText(etEmail.text?.trim() ?: "")
            } else tilEmail.error = null
        }

    private fun FragmentSignUpBinding.onFocusChangeListenerEmail() =
        View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && !EmailValidator.isValidEmail(etEmail.text.toString())) {
                tilEmail.error =
                    getString(R.string.error_email)
                etEmail.setText(etEmail.text?.trim() ?: "")
            } else tilEmail.error = null
        }

    private fun FragmentSignInBinding.onSignInPressed() {
        bSignIn.setOnClickListener {
            viewModel.signIn(
                email = etEmail.text.toString(),
                password = etPassword.text.toString()
            )
        }
    }

    private fun FragmentSignInBinding.onRegisterButtonPressed() {
        tvcRegister.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragmentIntro)
        }
    }

    private fun FragmentSignInBinding.observeState() = viewModel.state.observe(viewLifecycleOwner) {
        tilEmail.error = if (it.emptyEmailError) getString(R.string.field_is_empty) else null
        tilPassword.error = if (it.emptyPasswordError) getString(R.string.field_is_empty) else null
        etEmail.isEnabled = it.enableViews
        etPassword.isEnabled = it.enableViews
        progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun FragmentSignInBinding.observeClearPasswordEvent() =
        viewModel.clearPasswordEvent.observeEvent(viewLifecycleOwner) {
            etPassword.text?.clear()
        }

    private fun observeShowAuthErrorMessageEvent() =
        viewModel.showAuthToastEvent.observeEvent(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

    private fun observeNavigateToTabsEvent() =
        viewModel.navigateToTabsEvent.observeEvent(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_signInFragment_to_tabFragment)
        }
}