package org.dudar.validation;

import org.dudar.utils.Validator;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TitileValidationTest {

    @Test
    public void validateTitleSuccess() {
        String title = "just Book";
        boolean isValid = Validator.getInstance().validateTitle(title);
        assertTrue(isValid);
    }

    @Test
    public void validateTitle() {
        String title = "";
        boolean isValid = Validator.getInstance().validateTitle(title);
        assertFalse(isValid);
    }

}
