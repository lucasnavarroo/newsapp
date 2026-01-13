package com.example.core.biometric

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

class BiometricAuthenticator(
    private val activity: FragmentActivity
) {
    fun authenticateIfAvailable(
        title: String,
        subtitle: String? = null,
        negativeButtonText: String,
        onAuthenticated: () -> Unit,
        onUserCancelled: () -> Unit = {},
    ) {
        val biometricManager = BiometricManager.from(activity)

        val canAuthenticate = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        )

        when (canAuthenticate) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                showBiometricPrompt(
                    title = title,
                    subtitle = subtitle,
                    negativeButtonText = negativeButtonText,
                    onAuthenticated = onAuthenticated,
                    onUserCancelled = onUserCancelled
                )
            }

            else -> {
                onAuthenticated()
            }
        }
    }

    private fun showBiometricPrompt(
        title: String,
        subtitle: String?,
        negativeButtonText: String,
        onAuthenticated: () -> Unit,
        onUserCancelled: () -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(activity)

        val biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    onAuthenticated()
                }

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)

                    if (
                        errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON ||
                        errorCode == BiometricPrompt.ERROR_USER_CANCELED
                    ) {
                        onUserCancelled()
                    } else {
                        onAuthenticated()
                    }
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .apply {
                subtitle?.let { setSubtitle(it) }
            }
            .setNegativeButtonText(negativeButtonText)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}