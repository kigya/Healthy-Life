package com.exlab.healthylife.ui.screens.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.exlab.healthylife.R
import com.exlab.healthylife.app.base.ui.BaseFragment
import com.exlab.healthylife.app.base.ui.BaseViewModel
import com.exlab.healthylife.databinding.FragmentSignUpIntroBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragmentIntro : BaseFragment(R.layout.fragment_sign_up_intro) {
    override val viewModel: BaseViewModel by viewModels<SignUpViewModel>()
    private val viewBinding by viewBinding(FragmentSignUpIntroBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragmentIntro_to_signUpFragment)
        }
    }
}
