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
}
