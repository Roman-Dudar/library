package org.dudar.validation;

import org.dudar.utils.Validator;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NameValidationTest {

    @Test
    public void testNameSuccess() {
        String name = "Alex";
        boolean isValid = Validator.getInstance().validateName(name);
        assertTrue(isValid);
    }
    @Test
    public void testMixedAlphabetNameFailure() {
        String name = "Alекс";
        boolean isValid = Validator.getInstance().validateName(name);
        assertFalse(isValid);
    }
    @Test
    public void testCyrillicNameStartingWithSmallLetterFailure() {
        String name = "алекс";
        boolean isValid = Validator.getInstance().validateName(name);
        assertFalse(isValid);
    }
    @Test
    public void testNameStartingWithSmallLetterFailure() {
        String name = "alex";
        boolean isValid = Validator.getInstance().validateName(name);
        assertFalse(isValid);
    }
    @Test
    public void tesUkrainianNameSuccess() {
        String name = "В'ячеслав";
        boolean isValid = Validator.getInstance().validateName(name);
        assertTrue(isValid);
    }


}
