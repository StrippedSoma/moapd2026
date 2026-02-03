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

import dk.itu.moapd.testingapps.domain.validation.InputValidator
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test

/**
 * Unit tests for validating text input using the InputValidator class. The tests cover various
 * scenarios for validating names, emails, and passwords.
 */
class InputValidatorTest {

    @Test
    fun inputValidator_CorrectNameSimple_ReturnsTrue() {
        assertThat(InputValidator.InputType.NAME.isValid("Name"), `is`(true))
    }

    @Test
    fun inputValidator_CorrectFullName_ReturnsTrue() {
        assertThat(InputValidator.InputType.NAME.isValid("Name Surname"), `is`(true))
    }

    @Test
    fun inputValidator_CorrectShortName_ReturnsTrue() {
        assertThat(InputValidator.InputType.NAME.isValid("Ram"), `is`(true))
    }

    @Test
    fun inputValidator_InvalidNameInitialSpace_ReturnsFalse() {
        assertThat(InputValidator.InputType.NAME.isValid(" Name"), `is`(false))
    }

    @Test
    fun inputValidator_InvalidNameFinalSpace_ReturnsFalse() {
        assertThat(InputValidator.InputType.NAME.isValid("Name "), `is`(false))
    }

    @Test
    fun inputValidator_InvalidNameLowerCase_ReturnsFalse() {
        assertThat(InputValidator.InputType.NAME.isValid("name"), `is`(false))
    }

    @Test
    fun inputValidator_InvalidShortName_ReturnsFalse() {
        assertThat(InputValidator.InputType.NAME.isValid("Jo"), `is`(false))
    }

    @Test
    fun inputValidator_EmptyNameString_ReturnsFalse() {
        assertThat(InputValidator.InputType.NAME.isValid(""), `is`(false))
    }

    @Test
    fun inputValidator_CorrectEmailSimple_ReturnsTrue() {
        assertThat(InputValidator.InputType.EMAIL.isValid("name@itu.dk"), `is`(true))
    }

    @Test
    fun inputValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertThat(InputValidator.InputType.EMAIL.isValid("name@email.co.uk"), `is`(true))
    }

    @Test
    fun inputValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertThat(InputValidator.InputType.EMAIL.isValid("name@itu"), `is`(false))
    }

    @Test
    fun inputValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertThat(InputValidator.InputType.EMAIL.isValid("name@itu..dk"), `is`(false))
    }

    @Test
    fun inputValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertThat(InputValidator.InputType.EMAIL.isValid("@itu.dk"), `is`(false))
    }

    @Test
    fun inputValidator_InvalidEmailNoDomain_ReturnsFalse() {
        assertThat(InputValidator.InputType.EMAIL.isValid("name@"), `is`(false))
    }

    @Test
    fun inputValidator_EmptyEmailString_ReturnsFalse() {
        assertThat(InputValidator.InputType.EMAIL.isValid(""), `is`(false))
    }

    @Test
    fun inputValidator_CorrectPasswordSixChars_ReturnsTrue() {
        assertThat(InputValidator.InputType.PASSWORD.isValid("ABC123"), `is`(true))
    }

    @Test
    fun inputValidator_InvalidPasswordFiveChars_ReturnsFalse() {
        assertThat(InputValidator.InputType.PASSWORD.isValid("ABC12"), `is`(false))
    }

    @Test
    fun inputValidator_InvalidPasswordFourChars_ReturnsFalse() {
        assertThat(InputValidator.InputType.PASSWORD.isValid("AB12"), `is`(false))
    }

    @Test
    fun inputValidator_InvalidPasswordThreeChars_ReturnsFalse() {
        assertThat(InputValidator.InputType.PASSWORD.isValid("AB1"), `is`(false))
    }

    @Test
    fun inputValidator_InvalidPasswordTwoChars_ReturnsFalse() {
        assertThat(InputValidator.InputType.PASSWORD.isValid("A1"), `is`(false))
    }

    @Test
    fun inputValidator_EmptyPasswordString_ReturnsFalse() {
        assertThat(InputValidator.InputType.PASSWORD.isValid(""), `is`(false))
    }

}
