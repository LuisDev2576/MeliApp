package com.luis.dev.meliapp.features.authentication.presentation.register

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
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
class RegisterScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    // Variables para capturar callbacks
    private var lastIntent: RegisterIntent? = null
    private var navigateToLoginCalled = false
    private var registrationSuccessCalled = false

    // Estado base para la pantalla de registro
    private val baseState = RegisterState(
        fullName = "",
        email = "",
        password = "",
        confirmPassword = "",
        isLoading = false,
        error = null,
        registeredSuccess = false
    )

    /**
     * Función helper para inyectar RegisterScreen con callbacks que actualizan las variables de test.
     */
    private fun setContent(state: RegisterState = baseState) {
        composeTestRule.setContent {
            RegisterScreen(
                state = state,
                onIntent = { intent -> lastIntent = intent },
                onNavigateToLogin = { navigateToLoginCalled = true },
                onRegistrationSuccess = { registrationSuccessCalled = true }
            )
        }
    }

    @Test
    fun initialElements_areDisplayed() {
        setContent()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val createAccountTitle = context.getString(R.string.create_account_title)
        val namePlaceholder = context.getString(R.string.name_placeholder)
        val emailPlaceholder = context.getString(R.string.email_placeholder)
        val passwordPlaceholder = context.getString(R.string.password_placeholder)
        val confirmPasswordPlaceholder = context.getString(R.string.confirm_password_placeholder)
        val registerButtonLabel = context.getString(R.string.create_account)
        val alreadyHaveAccount = context.getString(R.string.already_have_account)
        val loginButtonText = context.getString(R.string.login_button)

        // Verifica que se muestre el título
        composeTestRule.onNodeWithText(createAccountTitle).assertIsDisplayed()
        // Verifica que se muestren los placeholders de los campos
        composeTestRule.onNode(hasText(namePlaceholder)).assertIsDisplayed()
        composeTestRule.onNode(hasText(emailPlaceholder)).assertIsDisplayed()
        composeTestRule.onNode(hasText(passwordPlaceholder)).assertIsDisplayed()
        composeTestRule.onNode(hasText(confirmPasswordPlaceholder)).assertIsDisplayed()
        // Verifica que se muestre el botón "Crear cuenta"
        composeTestRule.onNodeWithText(registerButtonLabel).assertIsDisplayed()
        // Verifica que se muestren los textos del enlace para ir a login
        composeTestRule.onNodeWithText(alreadyHaveAccount).assertIsDisplayed()
        composeTestRule.onNodeWithText(loginButtonText).assertIsDisplayed()
    }

    @Test
    fun nameField_onTextInput_triggersFullNameChangedIntent() {
        setContent()
        val testName = "Test Name"
        val context = ApplicationProvider.getApplicationContext<Context>()
        val namePlaceholder = context.getString(R.string.name_placeholder)
        composeTestRule.onNode(hasText(namePlaceholder))
            .performTextInput(testName)
        assertEquals(RegisterIntent.FullNameChanged(testName), lastIntent)
    }

    @Test
    fun emailField_onTextInput_triggersEmailChangedIntent() {
        setContent()
        val testEmail = "test@example.com"
        val context = ApplicationProvider.getApplicationContext<Context>()
        val emailPlaceholder = context.getString(R.string.email_placeholder)
        composeTestRule.onNode(hasText(emailPlaceholder))
            .performTextInput(testEmail)
        assertEquals(RegisterIntent.EmailChanged(testEmail), lastIntent)
    }

    @Test
    fun passwordField_onTextInput_triggersPasswordChangedIntent() {
        setContent()
        val testPassword = "123456"
        val context = ApplicationProvider.getApplicationContext<Context>()
        val passwordPlaceholder = context.getString(R.string.password_placeholder)
        composeTestRule.onNode(hasText(passwordPlaceholder))
            .performTextInput(testPassword)
        assertEquals(RegisterIntent.PasswordChanged(testPassword), lastIntent)
    }

    @Test
    fun confirmPasswordField_onTextInput_triggersConfirmPasswordChangedIntent() {
        setContent()
        val testConfirmPassword = "123456"
        val context = ApplicationProvider.getApplicationContext<Context>()
        val confirmPasswordPlaceholder = context.getString(R.string.confirm_password_placeholder)
        composeTestRule.onNode(hasText(confirmPasswordPlaceholder))
            .performTextInput(testConfirmPassword)
        assertEquals(RegisterIntent.ConfirmPasswordChanged(testConfirmPassword), lastIntent)
    }

    @Test
    fun confirmPasswordField_onDone_triggersRegisterClickedIntent() {
        setContent()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val confirmPasswordPlaceholder = context.getString(R.string.confirm_password_placeholder)
        // Simula la acción IME "Done" en el campo de confirmación
        composeTestRule.onNode(hasText(confirmPasswordPlaceholder))
            .performImeAction()
        // Se espera que se dispare la intención RegisterClicked
        assertEquals(RegisterIntent.RegisterClicked, lastIntent)
    }

    @Test
    fun registerButton_click_triggersRegisterClickedIntent() {
        setContent()
        val context = ApplicationProvider.getApplicationContext<Context>()
        // Se utiliza el string definido en el recurso para el botón
        val registerButtonLabel = context.getString(R.string.create_account)
        composeTestRule.onNodeWithText(registerButtonLabel)
            .assertIsDisplayed()
            .performClick()
        assertEquals(RegisterIntent.RegisterClicked, lastIntent)
    }

    @Test
    fun navigationLink_click_triggersOnNavigateToLogin() {
        setContent()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val loginButtonText = context.getString(R.string.login_button)
        composeTestRule.onNodeWithText(loginButtonText)
            .assertIsDisplayed()
            .performClick()
        assertTrue(navigateToLoginCalled)
    }

    @Test
    fun backPress_triggersOnNavigateToLogin() {
        setContent()
        composeTestRule.runOnUiThread {
            composeTestRule.activity.onBackPressedDispatcher.onBackPressed()
        }
        composeTestRule.waitForIdle()
        assertTrue(navigateToLoginCalled)
    }

    @Test
    fun whenRegisteredSuccess_state_callsOnRegistrationSuccess() {
        val successState = baseState.copy(registeredSuccess = true)
        setContent(state = successState)
        composeTestRule.waitForIdle()
        assertTrue(registrationSuccessCalled)
    }

    @Test
    fun whenIsLoading_thenCustomActionButton_showsIndicatorAndIsNotClickable() {
        val loadingState = baseState.copy(isLoading = true)
        setContent(state = loadingState)
        // Se espera que en estado de carga no se muestre el botón (ya que se reemplaza por un indicador)
        val context = ApplicationProvider.getApplicationContext<Context>()
        val registerButtonLabel = context.getString(R.string.create_account)
        composeTestRule.onNodeWithText(registerButtonLabel).assertDoesNotExist()
        // Si se asigna un testTag al progress indicator, se podría verificar su existencia.
    }
}