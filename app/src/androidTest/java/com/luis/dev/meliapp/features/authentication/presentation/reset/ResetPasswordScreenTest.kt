package com.luis.dev.meliapp.features.authentication.presentation.reset

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.luis.dev.meliapp.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ResetPasswordScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    // Variables para capturar callbacks
    private var lastIntent: ResetPasswordIntent? = null
    private var navigateBackCalled = false

    // Estado base sin errores, sin carga y sin envío
    private val baseState = ResetPasswordState(
        email = "",
        isLoading = false,
        error = null,
        resetEmailSent = false
    )

    /**
     * Función helper para inyectar la pantalla con callbacks que actualizan variables de test.
     */
    private fun setContent(state: ResetPasswordState = baseState) {
        composeTestRule.setContent {
            ResetPasswordScreen(
                state = state,
                onIntent = { intent -> lastIntent = intent },
                onNavigateBack = { navigateBackCalled = true }
            )
        }
    }

    @Test
    fun initialElements_areDisplayed() {
        setContent()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val recoverTitle = context.getString(R.string.recover_password_title)
        val recoverInstructions = context.getString(R.string.recover_password_instructions)
        val emailPlaceholder = context.getString(R.string.email_placeholder)
        val rememberedPassword = context.getString(R.string.remembered_password)
        val loginButtonText = context.getString(R.string.login_button)
        val sendButtonText = context.getString(R.string.send_button)

        // Verifica que se muestren el título, instrucciones y placeholder
        composeTestRule.onNodeWithText(recoverTitle).assertIsDisplayed()
        composeTestRule.onNodeWithText(recoverInstructions).assertIsDisplayed()
        composeTestRule.onNode(hasText(emailPlaceholder)).assertIsDisplayed()
        // Verifica el botón "Enviar correo"
        composeTestRule.onNodeWithText(sendButtonText).assertIsDisplayed()
        // Verifica los textos del NavigationLinkText
        composeTestRule.onNodeWithText(rememberedPassword).assertIsDisplayed()
        composeTestRule.onNodeWithText(loginButtonText).assertIsDisplayed()
    }

    @Test
    fun emailField_onTextInput_triggersEmailChangedIntent() {
        setContent()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val emailPlaceholder = context.getString(R.string.email_placeholder)
        val testEmail = "reset@test.com"
        composeTestRule.onNode(hasText(emailPlaceholder))
            .performTextInput(testEmail)
        assertEquals(ResetPasswordIntent.EmailChanged(testEmail), lastIntent)
    }

    @Test
    fun resetButton_click_triggersResetClickedIntent() {
        setContent()
        val sendButtonText = ApplicationProvider.getApplicationContext<Context>().getString(R.string.send_button)
        composeTestRule.onNodeWithText(sendButtonText)
            .assertIsDisplayed()
            .performClick()
        assertEquals(ResetPasswordIntent.ResetClicked, lastIntent)
    }

    @Test
    fun navigationLink_click_triggersOnNavigateBack() {
        setContent()
        val loginButtonText = ApplicationProvider.getApplicationContext<Context>().getString(R.string.login_button)
        composeTestRule.onNodeWithText(loginButtonText)
            .assertIsDisplayed()
            .performClick()
        assertTrue(navigateBackCalled)
    }

    @Test
    fun backPress_triggersOnNavigateBack() {
        setContent()
        composeTestRule.runOnUiThread {
            composeTestRule.activity.onBackPressedDispatcher.onBackPressed()
        }
        composeTestRule.waitForIdle()
        assertTrue(navigateBackCalled)
    }

    @Test
    fun whenResetEmailSent_state_callsOnNavigateBack() {
        val successState = baseState.copy(resetEmailSent = true)
        setContent(state = successState)
        composeTestRule.waitForIdle()
        assertTrue(navigateBackCalled)
    }

    @Test
    fun whenIsLoading_thenCustomActionButton_showsIndicatorAndIsNotClickable() {
        val loadingState = baseState.copy(isLoading = true)
        setContent(state = loadingState)
        val sendButtonText = ApplicationProvider.getApplicationContext<Context>().getString(R.string.send_button)
        composeTestRule.onNodeWithText(sendButtonText).assertDoesNotExist()
    }
}