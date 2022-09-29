package com.exlab.healthylife.ui.base

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.exlab.healthylife.R
import com.exlab.healthylife.databinding.ActivityThirdFragmentBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class FirstFragment: BaseFragment(R.layout.activity_first_fragment) {
    private val viewBinding by viewBinding(ActivityThirdFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.buttonRegistration.setOnClickListener{

        findNavController().navigate(R.id.action_secondFragment_start)
        }

    }
}