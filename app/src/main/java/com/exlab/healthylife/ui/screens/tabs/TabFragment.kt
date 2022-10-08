package com.exlab.healthylife.ui.screens.tabs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.exlab.healthylife.R
import com.exlab.healthylife.app.base.ui.BaseFragment
import com.exlab.healthylife.databinding.FragmentSignUpBinding
import com.exlab.healthylife.databinding.FragmentTabBinding
import com.exlab.healthylife.ui.screens.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabFragment : BaseFragment(R.layout.fragment_tab) {

    override val viewModel by viewModels<TabViewModel>()
    private val viewBinding by viewBinding(FragmentTabBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            tvEmail.text = viewModel.getCurrentUser()
        }
    }
}