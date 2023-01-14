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

    // isPositiveNumber

    @Test
    public void isPositiveNumberInput0ReturnsTrue() {
        // Arrange
        int input = 0;

        // Act
        boolean result = NumericRangeTools.isPositiveNumber(input);

        // Assert
        assertTrue(result);
    }

    @Test
    public void isPositiveNumberInputIntegerMaxValueReturnsTrue() {
        // Arrange
        int input = Integer.MAX_VALUE;

        // Act
        boolean result = NumericRangeTools.isPositiveNumber(input);

        // Assert
        assertTrue(result);
    }

    @Test
    public void isPositiveNumberInputIntegerNegativeReturnsTrue() {
        // Arrange
        int input = -1;

        // Act
        boolean result = NumericRangeTools.isPositiveNumber(input);

        // Assert
        assertFalse(result);
    }

    // isWithinRange int

    @Test
    public void isWithinRangeIntValueWithinValidRangeMinReturnsTrue() {
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
    public void isWithinRangeIntValueWithinValidRangeMaxReturnsTrue() {
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
    public void isWithinRangeIntValueWithinSameRangeReturnsTrue() {
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
    public void isWithinRangeIntValueOutsideValidRangeMinReturnsFalse() {
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
    public void isWithinRangeIntValueOutsideValidRangeMaxReturnsFalse() {
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
    public void isWithinRangeIntMinBiggerThanMaxThrowsIllegalArgumentException() {
        // Arrange
        int input = 0;
        int min = 1;
        int max = -1;

        // Act
        boolean result = NumericRangeTools.isWithinRange(input, min, max);
    }

    // isWithinRange double

    @Test
    public void isWithinRangeDoubleValueWithinValidRangeMinReturnsTrue() {
        // Arrange
        double input = -1;
        double min = -1;
        double max = 0;

        // Act
        boolean result = NumericRangeTools.isWithinRange(input, min, max);

        // Assert
        assertTrue(result);
    }

    @Test
    public void isWithinRangeDoubleValueWithinValidRangeMaxReturnsTrue() {
        // Arrange
        double input = 0;
        double min = -1;
        double max = 0;

        // Act
        boolean result = NumericRangeTools.isWithinRange(input, min, max);

        // Assert
        assertTrue(result);
    }

    @Test
    public void isWithinRangeDoubleValueWithinSameRangeReturnsTrue() {
        // Arrange
        double input = 0;
        double min = 0;
        double max = 0;

        // Act
        boolean result = NumericRangeTools.isWithinRange(input, min, max);

        // Assert
        assertTrue(result);
    }

    @Test
    public void isWithinRangeDoubleValueOutsideValidRangeMinReturnsFalse() {
        // Arrange
        double input = -2;
        double min = -1;
        double max = 0;

        // Act
        boolean result = NumericRangeTools.isWithinRange(input, min, max);

        // Assert
        assertFalse(result);
    }

    @Test
    public void isWithinRangeDoubleValueOutsideValidRangeMaxReturnsFalse() {
        // Arrange
        double input = 1;
        double min = -1;
        double max = 0;

        // Act
        boolean result = NumericRangeTools.isWithinRange(input, min, max);

        // Assert
        assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isWithinRangeDoubleMinBiggerThanMaxThrowsIllegalArgumentException() {
        // Arrange
        double input = 0;
        double min = 1;
        double max = -1;

        // Act
        boolean result = NumericRangeTools.isWithinRange(input, min, max);
    }
}
