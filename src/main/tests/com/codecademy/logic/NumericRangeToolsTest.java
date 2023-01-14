package com.codecademy.logic;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NumericRangeToolsTest {

    @Test
    public void isValidPercentageValueWithinValidRange0ReturnsTrue() {
        // Arrange
        int percentage = 0;

        // Act
        boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertTrue(result);
    }

    @Test
    public void isValidPercentageValueWithinValidRange100ReturnsTrue() {
        // Arrange
        int percentage = 100;

        // Act
        boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertTrue(result);
    }

    @Test
    public void isValidPercentageValueOutOfRangeLowReturnsFalse() {
        // Arrange
        int percentage = -1;

        // Act
        boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertFalse(result);
    }

    @Test
    public void isValidPercentageValueOutOfRangeHighReturnsFalse() {
        // Arrange
        int percentage = 101;

        // Act
        boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertFalse(result);
    }

    @Test
    public void isWithinRangeValueWithinValidRangeMinReturnsTrue() {
        // Arrange
        int input = -1;
        int min = -1;
        int max = 0;

        // Act
        boolean result = NumericRangeTools.isWithinRange(input, min, max);

        // Assert
        assertTrue(result);
    }

    @Test
    public void isWithinRangeValueWithinValidRangeMaxReturnsTrue() {
        // Arrange
        int input = 0;
        int min = -1;
        int max = 0;

        // Act
        boolean result = NumericRangeTools.isWithinRange(input, min, max);

        // Assert
        assertTrue(result);
    }

    @Test
    public void isWithinRangeValueWithinSameRangeReturnsTrue() {
        // Arrange
        int input = 0;
        int min = 0;
        int max = 0;

        // Act
        boolean result = NumericRangeTools.isWithinRange(input, min, max);

        // Assert
        assertTrue(result);
    }

    @Test
    public void isWithinRangeValueOutsideValidRangeMinReturnsFalse() {
        // Arrange
        int input = -2;
        int min = -1;
        int max = 0;

        // Act
        boolean result = NumericRangeTools.isWithinRange(input, min, max);

        // Assert
        assertFalse(result);
    }

    @Test
    public void isWithinRangeValueOutsideValidRangeMaxReturnsFalse() {
        // Arrange
        int input = 1;
        int min = -1;
        int max = 0;

        // Act
        boolean result = NumericRangeTools.isWithinRange(input, min, max);

        // Assert
        assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isWithinRangeMinBiggerThanMaxThrowsIllegalArgumentException() {
        // Arrange
        int input = 0;
        int min = 1;
        int max = -1;

        // Act
        boolean result = NumericRangeTools.isWithinRange(input, min, max);
    }
}
