package com.codecademy.logic;

import org.junit.Test;

import static org.junit.Assert.*;

public class PostalCodeTest {
    @Test
    public void formatPostalCodeValidPostalCodeWithoutSpaceReturnsFormattedPostalCode() {
        // Arrange
        String input = "1000AA";

        // Act
        String result = PostalCode.formatPostalCode(input);

        // Assert
        String expected = "1000 AA";
        assertEquals(expected, result);
    }

    @Test
    public void formatPostalCodeValidPostalCodeLowercaseReturnsFormattedPostalCode() {
        // Arrange
        String input = "1000 aa";

        // Act
        String result = PostalCode.formatPostalCode(input);

        // Assert
        String expected = "1000 AA";
        assertEquals(expected, result);
    }

    @Test
    public void formatPostalCodePostalCodeWithSpacesReturnsFormattedPostalCode() {
        // Arrange
        String input = " 1000AA ";

        // Act
        String result = PostalCode.formatPostalCode(input);

        // Assert
        String expected = "1000 AA";
        assertEquals(expected, result);
    }

    @Test(expected = NullPointerException.class)
    public void formatPostalCodeInvalidPostalCodeNullThrowsNullPointerException() {
        // Arrange
        String input = null;

        // Act
        PostalCode.formatPostalCode(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatPostalCodeInvalidPostalCodeThreeNumbersThrowsIllegalArgumentException() {
        // Arrange
        String input = "999AA";

        // Act
        PostalCode.formatPostalCode(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatPostalCodeInvalidPostalCodeFiveNumbersThrowsIllegalArgumentException() {
        // Arrange
        String input = "10000AA";

        // Act
        PostalCode.formatPostalCode(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatPostalCodeInvalidPostalCodeOneLetterThrowsIllegalArgumentException() {
        // Arrange
        String input = "1000A";

        // Act
        PostalCode.formatPostalCode(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatPostalCodeInvalidPostalCodeThreeLettersThrowsIllegalArgumentException() {
        // Arrange
        String input = "1000AAA";

        // Act
        PostalCode.formatPostalCode(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatPostalCodeInvalidPostalCodeWithSymbolThrowsIllegalArgumentException() {
        // Arrange
        String input = "1000A#";

        // Act
        PostalCode.formatPostalCode(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatPostalCodeInvalidPostalCodeLetterInNumbersThrowsIllegalArgumentException() {
        // Arrange
        String input = "1A00AA";

        // Act
        PostalCode.formatPostalCode(input);
    }
}
