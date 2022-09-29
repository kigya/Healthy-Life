package com.exlab.healthylife.ui.base

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.exlab.healthylife.R
import com.exlab.healthylife.databinding.ActivityThirdFragmentBinding

class SecondFragment: BaseFragment(R.layout.activity_second_fragment) {
    private val viewBinding by viewBinding(ActivityThirdFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.buttonRegistration.setOnClickListener {

            findNavController().navigate(R.id.secondFragment)

        }
    }
}