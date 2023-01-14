package com.codecademy.logic;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringToolsTest {
    @Test
    public void isNotNullEmptyOrWhitespaceNullReturnsFalse() {
        // Arrange
        String input = null;

        // Act
        boolean result = StringTools.isNotNullEmptyOrWhitespace(input);

        // Assert
        assertFalse(result);
    }

    @Test
    public void isNotNullEmptyOrWhitespaceEmptyReturnsFalse() {
        // Arrange
        String input = "";

        // Act
        boolean result = StringTools.isNotNullEmptyOrWhitespace(input);

        // Assert
        assertFalse(result);
    }

    @Test
    public void isNotNullEmptyOrWhitespaceWhitespaceReturnsFalse() {
        // Arrange
        String input = " ";

        // Act
        boolean result = StringTools.isNotNullEmptyOrWhitespace(input);

        // Assert
        assertFalse(result);
    }

    @Test
    public void isNotNullEmptyOrWhitespaceStringWithSymbolOtherThanWhitespaceReturnsTrue() {
        // Arrange
        String input = "w";

        // Act
        boolean result = StringTools.isNotNullEmptyOrWhitespace(input);

        // Assert
        assertTrue(result);
    }
}
