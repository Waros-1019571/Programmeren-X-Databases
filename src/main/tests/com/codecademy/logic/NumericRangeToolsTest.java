package com.codecademy.logic;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NumericRangeToolsTest {

    // isValidPercentage int

    @Test
    public void isValidPercentageValueIntWithinValidRange0ReturnsTrue() {
        // Arrange
        int percentage = 0;

        // Act
        boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertTrue(result);
    }

    @Test
    public void isValidPercentageValueIntWithinValidRange100ReturnsTrue() {
        // Arrange
        int percentage = 100;

        // Act
        boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertTrue(result);
    }

    @Test
    public void isValidPercentageValueIntOutOfRangeLowReturnsFalse() {
        // Arrange
        int percentage = -1;

        // Act
        boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertFalse(result);
    }

    @Test
    public void isValidPercentageValueIntOutOfRangeHighReturnsFalse() {
        // Arrange
        int percentage = 101;

        // Act
        boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertFalse(result);
    }

    // isValidPercentage double

    @Test
    public void isValidPercentageDoubleValueWithinValidRange0ReturnsTrue() {
        // Arrange
        double percentage = 0.0;

        // Act
        boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertTrue(result);
    }

    @Test
    public void isValidPercentageDoubleValueWithinValidRange100ReturnsTrue() {
        // Arrange
        double percentage = 100.0;

        // Act
        boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertTrue(result);
    }

    @Test
    public void isValidPercentageDoubleValueOutOfRangeLowReturnsFalse() {
        // Arrange
        double percentage = -0.00000000000001;

        // Act
        boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertFalse(result);
    }

    @Test
    public void isValidPercentageDoubleValueOutOfRangeHighReturnsFalse() {
        // Arrange
        double percentage = 100.00000000000001;

        // Act
        boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertFalse(result);
    }

    // isWithinRange

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
