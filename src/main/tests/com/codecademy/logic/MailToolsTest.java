package com.codecademy.logic;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MailToolsTest {
    @Test
    public void validateMailAddressNoMailboxPartReturnsFalse() {
        // Arrange
        String input = "@example.org";

        // Act
        boolean result = MailTools.validateMailAddress(input);

        // Assert
        assertFalse(result);
    }

    @Test
    public void validateMailAddressNoMailboxPartNoAtSignReturnsFalse() {
        // Arrange
        String input = "example.org";

        // Act
        boolean result = MailTools.validateMailAddress(input);

        // Assert
        assertFalse(result);
    }

    @Test
    public void validateMailAddressSubdomainTldNoAtSignReturnsFalse() {
        // Arrange
        String input = "exampleexample.org.org";

        // Act
        boolean result = MailTools.validateMailAddress(input);

        // Assert
        assertFalse(result);
    }

    @Test
    public void validateMailAddressNoSubdomainPartReturnsFalse() {
        // Arrange
        String input = "example@.org";

        // Act
        boolean result = MailTools.validateMailAddress(input);

        // Assert
        assertFalse(result);
    }

    @Test
    public void validateMailAddressNoSubdomainPartNoAtSignReturnsFalse() {
        // Arrange
        String input = ".org";

        // Act
        boolean result = MailTools.validateMailAddress(input);

        // Assert
        assertFalse(result);
    }

    @Test
    public void validateMailAddressNoTldPartReturnsFalse() {
        // Arrange
        String input = "example@example";

        // Act
        boolean result = MailTools.validateMailAddress(input);

        // Assert
        assertFalse(result);
    }

    @Test
    public void validateMailAddressNoTldPartNoAtSignReturnsFalse() {
        // Arrange
        String input = "exampleexample";

        // Act
        boolean result = MailTools.validateMailAddress(input);

        // Assert
        assertFalse(result);
    }

    @Test
    public void validateMailAddressValidEmailReturnsTrue() {
        // Arrange
        String input = "example@example.org";

        // Act
        boolean result = MailTools.validateMailAddress(input);

        // Assert
        assertTrue(result);
    }


}
