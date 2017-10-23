package org.dudar.validation;

import org.dudar.utils.Validator;
import org.junit.Test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PhoneNumberValidationTest {
    @Test
    public void testPhoneNumberWithZeros() {
        String phoneNumber = "+000(00)000-00-00";
        boolean isValid = Validator.getInstance().validatePhoneNumber(phoneNumber);
        assertTrue(isValid);
    }
    @Test
    public void testPhoneNumberWithMoreZeros() {
        String phoneNumber = "+000(00)000-00-000";
        boolean isValid = Validator.getInstance().validatePhoneNumber(phoneNumber);
        assertFalse(isValid);
    }
}
