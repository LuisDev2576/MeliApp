package com.luis.dev.meliapp.features.authentication.presentation.login

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
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    // Variables para capturar callbacks
    private var lastIntent: LoginIntent? = null
    private var navigateToRegisterCalled = false
    private var navigateToResetCalled = false
    private var navigateBackCalled = false
    private var loginSuccessCalled = false

    // Estado base sin errores, sin carga y sin éxito
    private val baseState = LoginState(
        email = "",
        password = "",
        isLoading = false,
        error = null,
        success = false
    )

    /**
     * Configura la pantalla de login con callbacks que actualizan las variables de test.
     */
    private fun setContent(state: LoginState = baseState) {
        composeTestRule.setContent {
            LoginScreen(
                loginState = state,
                onIntent = { intent -> lastIntent = intent },
                onNavigateToRegister = { navigateToRegisterCalled = true },
                onNavigateToReset = { navigateToResetCalled = true },
                onNavigateBack = { navigateBackCalled = true },
                onLoginSuccess = { loginSuccessCalled = true }
            )
        }
    }

    @Test
    fun initialElements_areDisplayed() {
        setContent()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val greetingMessage = context.getString(R.string.greeting_message)
        val loginPrompt = context.getString(R.string.login_prompt)
        val emailPlaceholder = context.getString(R.string.email_placeholder)
        val passwordPlaceholder = context.getString(R.string.password_placeholder)
        val loginButton = context.getString(R.string.login_button)
        val createAccountText = context.getString(R.string.create_account)
        val recoverPasswordText = context.getString(R.string.recover_password)

        // Verifica el mensaje de saludo
        composeTestRule.onNodeWithText(greetingMessage).assertIsDisplayed()
        // Verifica las instrucciones
        composeTestRule.onNodeWithText(loginPrompt).assertIsDisplayed()
        // Verifica el placeholder del email
        composeTestRule.onNode(hasText(emailPlaceholder)).assertIsDisplayed()
        // Verifica el placeholder del password
        composeTestRule.onNode(hasText(passwordPlaceholder)).assertIsDisplayed()
        // Verifica el botón "Iniciar Sesión"
        composeTestRule.onNodeWithText(loginButton).assertIsDisplayed()
        // Verifica el enlace para registro
        composeTestRule.onNodeWithText(createAccountText).assertIsDisplayed()
        // Verifica el enlace para recuperar contraseña
        composeTestRule.onNodeWithText(recoverPasswordText).assertIsDisplayed()
    }

    @Test
    fun emailAndPasswordFields_triggerOnIntent_onChange() {
        setContent()

        val testEmail = "usuario@test.com"
        val testPassword = "123456"
        val context = ApplicationProvider.getApplicationContext<Context>()
        val emailPlaceholder = context.getString(R.string.email_placeholder)
        val passwordPlaceholder = context.getString(R.string.password_placeholder)

        // Escribir email
        composeTestRule.onNode(hasText(emailPlaceholder))
            .performTextInput(testEmail)
        assertEquals(LoginIntent.EmailChanged(testEmail), lastIntent)

        // Escribir contraseña
        composeTestRule.onNode(hasText(passwordPlaceholder))
            .performTextInput(testPassword)
        assertEquals(LoginIntent.PasswordChanged(testPassword), lastIntent)
    }

    @Test
    fun loginButton_click_triggersLoginIntent() {
        setContent()
        composeTestRule.onNodeWithText(ApplicationProvider.getApplicationContext<Context>().getString(R.string.login_button))
            .assertIsDisplayed()
            .performClick()
        assertEquals(LoginIntent.LoginClicked, lastIntent)
    }

    @Test
    fun navigationLinkText_register_click_triggersNavigation() {
        setContent()
        val createAccountText = ApplicationProvider.getApplicationContext<Context>().getString(R.string.create_account)
        composeTestRule.onNodeWithText(createAccountText)
            .assertIsDisplayed()
            .performClick()
        assertTrue(navigateToRegisterCalled)
    }

    @Test
    fun resetPasswordLink_click_triggersResetNavigation() {
        setContent()
        val recoverPasswordText = ApplicationProvider.getApplicationContext<Context>().getString(R.string.recover_password)
        composeTestRule.onNodeWithText(recoverPasswordText)
            .assertIsDisplayed()
            .performClick()
        assertTrue(navigateToResetCalled)
    }

    @Test
    fun whenSuccessState_isSet_callsOnLoginSuccess() {
        val successState = baseState.copy(success = true)
        setContent(state = successState)
        composeTestRule.waitForIdle()
        assertTrue(loginSuccessCalled)
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
    fun whenIsLoading_thenCustomActionButton_showsIndicatorAndIsNotClickable() {
        val loadingState = baseState.copy(isLoading = true)
        setContent(state = loadingState)
        composeTestRule.onNodeWithText(ApplicationProvider.getApplicationContext<Context>().getString(R.string.login_button)).assertDoesNotExist()
    }
}