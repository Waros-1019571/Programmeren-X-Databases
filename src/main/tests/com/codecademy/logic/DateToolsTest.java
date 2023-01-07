package com.codecademy.logic;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateToolsTest {

    @Test
    public void validateDate31DaysInMonthReturnsTrue() {
        // Arrange
        int day = 31;
        int month = 1;
        int year = 1999;

        // Act
        boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertTrue(result);
    }

    @Test
    public void validateDate30DaysInMonthReturnsTrue() {
        // Arrange
        int day = 30;
        int month = 6;
        int year = 1999;

        // Act
        boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertTrue(result);
    }

    @Test
    public void validateDate29DaysInMonthReturnsTrue() {
        // Arrange
        int day = 29;
        int month = 2;
        int year = 2000;

        // Act
        boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertTrue(result);
    }

    @Test
    public void validateDate28DaysInMonthReturnsTrue() {
        // Arrange
        int day = 28;
        int month = 2;
        int year = 2000;

        // Act
        boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertTrue(result);
    }

    @Test
    public void validateDateAllOtherCases31DaysDay0ReturnsFalse() {
        // Arrange
        int day = 0;
        int month = 1;
        int year = 2000;

        // Act
        boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertFalse(result);
    }

    @Test
    public void validateDateAllOtherCases31DaysDay32ReturnsFalse() {
        // Arrange
        int day = 32;
        int month = 1;
        int year = 2000;

        // Act
        boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertFalse(result);
    }

    @Test
    public void validateDateAllOtherCases30DaysDay0ReturnsFalse() {
        // Arrange
        int day = 0;
        int month = 4;
        int year = 2000;

        // Act
        boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertFalse(result);
    }

    @Test
    public void validateDateAllOtherCases30DaysDay31ReturnsFalse() {
        // Arrange
        int day = 31;
        int month = 4;
        int year = 2000;

        // Act
        boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertFalse(result);
    }

    @Test
    public void validateDateAllOtherCases29DaysDay0ReturnsFalse() {
        // Arrange
        int day = 0;
        int month = 2;
        int year = 2000;

        // Act
        boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertFalse(result);
    }

    @Test
    public void validateDateAllOtherCases29DaysDay30ReturnsFalse() {
        // Arrange
        int day = 30;
        int month = 2;
        int year = 2000;

        // Act
        boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertFalse(result);
    }

    @Test
    public void validateDateAllOtherCases28DaysDay0ReturnsFalse() {
        // Arrange
        int day = 0;
        int month = 2;
        int year = 2000;

        // Act
        boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertFalse(result);
    }

    @Test
    public void validateDateAllOtherCases28DaysDay29ReturnsFalse() {
        // Arrange
        int day = 29;
        int month = 2;
        int year = 1999;

        // Act
        boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertFalse(result);
    }
}
