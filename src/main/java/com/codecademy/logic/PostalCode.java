package com.codecademy.logic;

public class PostalCode {

    /**
     * @desc Formats the input postal code to a uniform output in the form
     * nnnn<space>MM, where nnnn is numeric and > 999 and MM are 2 capital letters.
     * Spaces before and after the input string are trimmed.
     *
     * @subcontract null postalCode {
     *   @requires postalCode == null;
     *   @signals (NullPointerException) postalCode == null;
     * }
     *
     * @subcontract valid postalCode {
     *   @requires Integer.valueOf(postalCode.trim().substring(0, 4)) > 999 &&
     *             Integer.valueOf(postalCode.trim().substring(0, 4)) <= 9999 &&
     *             postalCode.trim().substring(4).trim().length == 2 &&
     *             'A' <= postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <= 'Z' &&
     *             'A' <= postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <= 'Z';
     *   @ensures \result = postalCode.trim().substring(0, 4) + " " +
     *                  postalCode.trim().substring(4).trim().toUpperCase()
     * }
     *
     * @subcontract invalid postalCode {
     *   @requires no other valid precondition;
     *   @signals (IllegalArgumentException);
     * }
     *
     */
    public static String formatPostalCode(/* non_null */ String postalCode) {
        // Null check
        if (postalCode == null) {
            throw new NullPointerException();
        }
        // Remove spaces
        postalCode = postalCode.replaceAll(" ", "");
        // No space postal code must be six long
        if (postalCode.length() != 6) {
            throw new IllegalArgumentException();
        }
        // First four characters must be numbers
        int numbers;
        try {
            numbers = Integer.parseInt(postalCode.substring(0,4));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
        boolean fourNumbers = numbers >= 1000 && numbers <= 9999;
        if (!fourNumbers) {
            throw new IllegalArgumentException();
        }
        // Last two characters must be letters
        String letters = postalCode.substring(4).toUpperCase();
        if (letters.charAt(0) < 'A' || letters.charAt(0) > 'Z' || letters.charAt(1) < 'A' || letters.charAt(1) > 'Z') {
            throw new IllegalArgumentException();
        }
        // Valid postal code, format it
        return Integer.toString(numbers) + ' ' + letters;
    }
}
