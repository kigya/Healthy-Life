package com.exlab.healthylife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.exlab.healthylife.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import by.kirich1409.viewbindingdelegate.viewBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}