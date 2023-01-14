package com.codecademy.logic;

public class NumericRangeTools {

    /**
     * @desc Validates if the input is within range of 0-100%
     *
     * @subcontract value within valid range {
     *   @requires 0 <= percentage <= 100;
     *   @ensures \result = true;
     * }
     *
     * @subcontract value out of range low {
     *   @requires percentage < 0;
     *   @ensures \result = false;
     * }
     *
     * @subcontract value out of range high {
     *   @requires percentage > 100;
     *   @ensures \result = false;
     * }
     *
     */
    public static boolean isValidPercentage(int percentage) {
        return isWithinRange(percentage, 0, 100);
    }

    /**
     * @desc Validates if the input is within range of min and max
     *
     * @subcontract value within valid range {
     *   @requires min <= input <= max;
     *   @ensures \result = true;
     * }
     *
     * @subcontract value out of range low {
     *   @requires input < min;
     *   @ensures \result = false;
     * }
     *
     * @subcontract value out of range high {
     *   @requires input > max;
     *   @ensures \result = false;
     * }
     *
     * @subcontract min bigger than max {
     *   @requires min > max;
     *   @signals (IllegalArgumentException);
     * }
     *
     */
    public static boolean isWithinRange(int input, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Min is bigger than max");
        }
        if (input >= min && input <= max) {
            return true;
        }
        return false;
    }
}
