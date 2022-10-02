package com.exlab.healthylife.app.base.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.exlab.healthylife.R
import com.exlab.healthylife.app.base.*
import com.exlab.healthylife.app.base.ui.BaseFragment
import com.exlab.healthylife.databinding.ErrorViewBinding
import com.exlab.healthylife.utils.AuthException
import com.exlab.healthylife.utils.BackendException
import com.exlab.healthylife.utils.ConnectionException

/**
 * Display progress-bar for [Pending] result, error message and try again button
 * for [Error] result and nothing else for [Empty] and [Success] results
 */
class ResultView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
)
: ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ErrorViewBinding
    private var tryAgainAction: (() -> Unit)? = null

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.error_view, this, true)
        binding = ErrorViewBinding.bind(this)
    }

    /**
     * Assign an action for 'Try Again' button.
     */
    fun setTryAgainAction(action: () -> Unit) {
        this.tryAgainAction = action
    }

    /**
     * Set the current result to be displayed to the user.
     */
    fun <T> setResult(fragment: BaseFragment, result: Result<T>) {
        binding.tvPossibleError.isVisible = result is Error<*>
        binding.btnError.isVisible = result is Error<*>
        binding.progressBar.isVisible = result is Pending<*>
        if (result is Error) {
            Log.e(javaClass.simpleName, "Error", result.error)
            val message = when (result.error) {
                is ConnectionException -> context.getString(R.string.connection_error)
                is AuthException -> context.getString(R.string.auth_error)
                is BackendException -> result.error.message
                else -> context.getString(R.string.internal_error)
            }
            binding.tvPossibleError.text = message
            if (result.error is AuthException) {
                renderLogoutButton(fragment)
            } else {
                renderTryAgainButton()
            }
        }
    }

    private fun renderLogoutButton(fragment: BaseFragment) {
        binding.btnError.setOnClickListener {
            fragment.logout()
        }
        binding.btnError.setText(R.string.action_logout)
    }

    private fun renderTryAgainButton() {
        binding.btnError.setOnClickListener { tryAgainAction?.invoke() }
        binding.btnError.setText(R.string.try_again)
    }

}