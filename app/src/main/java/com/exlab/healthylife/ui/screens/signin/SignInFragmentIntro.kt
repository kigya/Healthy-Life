package com.exlab.healthylife.ui.screens.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.exlab.healthylife.R
import com.exlab.healthylife.app.base.ui.BaseFragment
import com.exlab.healthylife.app.base.ui.BaseViewModel
import com.exlab.healthylife.databinding.FragmentSignInIntroBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragmentIntro : BaseFragment(R.layout.fragment_sign_in_intro) {
    override val viewModel: BaseViewModel by viewModels<SignInViewModel>()
    private val viewBinding by viewBinding(FragmentSignInIntroBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            onLogInPressed()
            onRegisterPressed()
        }
    }

    private fun FragmentSignInIntroBinding.onRegisterPressed() {
        tvcRegister.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragmentIntro_to_signUpFragmentIntro)
        }
    }

    private fun FragmentSignInIntroBinding.onLogInPressed() {
        btnLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragmentIntro_to_signInFragment)
        }
    }
}