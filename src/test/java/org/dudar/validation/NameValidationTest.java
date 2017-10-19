package org.dudar.validation;

import org.dudar.utils.Validator;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NameValidationTest {

    @Test
    public void testNameSuccess() {
        String password = "Alex";
        boolean isValid = Validator.getInstance().validatePassword(password);
        assertTrue(isValid);
    }
    @Test
    public void testMixedAlphabetNameFailure() {
        String password = "Alекс";
        boolean isValid = Validator.getInstance().validatePassword(password);
        assertFalse(isValid);
    }
    @Test
    public void testCyrillicNameStartingWithSmallLetterFailure() {
        String password = "алекс";
        boolean isValid = Validator.getInstance().validatePassword(password);
        assertFalse(isValid);
    }
    @Test
    public void testNameStartingWithSmallLetterFailure() {
        String password = "alex";
        boolean isValid = Validator.getInstance().validatePassword(password);
        assertFalse(isValid);
    }


}
