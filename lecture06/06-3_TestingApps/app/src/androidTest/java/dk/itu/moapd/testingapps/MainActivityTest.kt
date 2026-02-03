/*
 * MIT License
 *
 * Copyright (c) 2026 Fabricio Batista Narcizo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package dk.itu.moapd.testingapps

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule
import androidx.activity.ComponentActivity
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.AnnotatedString

import dk.itu.moapd.testingapps.ui.main.MainScreen
import dk.itu.moapd.testingapps.ui.main.MainTestTags
import dk.itu.moapd.testingapps.ui.theme.TestingAppsTheme

/**
 * This class contains instrumentation tests for MainActivity. It tests various functionalities and
 * UI interactions of the MainActivity.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    /**
     * A JUnit Test Rule that provides a test activity.
     */
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    /**
     * Sets the content of the activity under test.
     */
    private fun setContent() {
        composeRule.setContent {
            TestingAppsTheme {
                MainScreen()
            }
        }
    }

    /**
     * Custom matcher to check if an EditText has a specific text.
     *
     * @param expected The expected text to match.
     *
     * @return A [SemanticsMatcher] that matches an EditText with a specific text.
     */
    private fun hasEditableTextExactly(expected: String): SemanticsMatcher {
        return SemanticsMatcher.expectValue(
            SemanticsProperties.EditableText,
            AnnotatedString(expected)
        )
    }

    /**
     * Custom matcher to check if an EditText has an empty text.
     *
     * @return A [SemanticsMatcher] that matches an EditText with an empty text.
     */
    private fun hasEmptyEditableText(): SemanticsMatcher {
        return hasEditableTextExactly("")
    }

    /**
     * Test to read the content of the name EditText.
     */
    @Test
    fun mainActivityTest_readNameEditText() {
        setContent()
        composeRule.onNodeWithTag(MainTestTags.NameField)
            .assert(hasEmptyEditableText())
    }

    /**
     * Test to read the content of the email EditText.
     */
    @Test
    fun mainActivityTest_readEmailEditText() {
        setContent()
        composeRule.onNodeWithTag(MainTestTags.EmailField)
            .assert(hasEmptyEditableText())
    }

    /**
     * Test to read the content of the password EditText.
     */
    @Test
    fun mainActivityTest_readPasswordEditText() {
        setContent()
        composeRule.onNodeWithTag(MainTestTags.PasswordField)
            .assert(hasEmptyEditableText())
    }

    /**
     * Test to verify the behavior when the Send button is clicked. This test checks the validation
     * of the input fields.
     *
     * IMPORTANT: I have included an error in the code. Can you find it?
     */
    @Test
    fun mainActivityTest_clickSendButton() {
        setContent()
        val errorText = composeRule.activity.getString(R.string.error)

        // Evaluate the name EditText.
        composeRule.onNodeWithText(errorText)
            .assertIsNotDisplayed()
        composeRule.onNodeWithTag(MainTestTags.SendButton)
            .performClick()
        composeRule.onAllNodesWithText(errorText)
            .assertCountEquals(3)

        // Enter valid text in the name EditText and re-evaluate.
        composeRule.onNodeWithTag(MainTestTags.NameField)
            .performTextInput("Name")
        composeRule.onNodeWithTag(MainTestTags.SendButton)
            .performClick()
        composeRule.onAllNodesWithText(errorText)
            .assertCountEquals(2)

        composeRule.onNodeWithTag(MainTestTags.EmailField)
            .performTextInput("name@itu.dk")
        composeRule.onNodeWithTag(MainTestTags.SendButton)
            .performClick()
        composeRule.onAllNodesWithText(errorText)
            .assertCountEquals(1)

        composeRule.onNodeWithTag(MainTestTags.PasswordField)
            .performTextInput("ABC123")
        composeRule.onNodeWithTag(MainTestTags.SendButton)
            .performClick()
        composeRule.onNodeWithText(errorText)
            .assertIsNotDisplayed()

        composeRule.onNodeWithTag(MainTestTags.NameField)
            .assert(hasEmptyEditableText())
        composeRule.onNodeWithTag(MainTestTags.EmailField)
            .assert(hasEmptyEditableText())
        composeRule.onNodeWithTag(MainTestTags.PasswordField)
            .assert(hasEmptyEditableText())
    }

    /**
     * Test to verify the behavior when the Reverse button is clicked. This test checks if the input
     * fields are cleared.
     *
     * IMPORTANT: I have included an error in the code. Can you find it?
     */
    @Test
    fun mainActivityTest_clickReverseButton() {
        setContent()

        // Insert data into the EditTexts.
        composeRule.onNodeWithTag(MainTestTags.NameField)
            .performTextInput("Name")
        composeRule.onNodeWithTag(MainTestTags.EmailField)
            .performTextInput("name@itu.dk")
        composeRule.onNodeWithTag(MainTestTags.PasswordField)
            .performTextInput("ABC123")

        // Click on Revert button.
        composeRule.onNodeWithTag(MainTestTags.RevertButton)
            .performClick()

        // All EditTexts are empty.
        composeRule.onNodeWithTag(MainTestTags.NameField)
            .assert(hasEmptyEditableText())
        composeRule.onNodeWithTag(MainTestTags.EmailField)
            .assert(hasEmptyEditableText())
        composeRule.onNodeWithTag(MainTestTags.PasswordField)
            .assert(hasEmptyEditableText())
    }

}
