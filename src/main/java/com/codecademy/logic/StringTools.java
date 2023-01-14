package com.codecademy.logic;

public class StringTools {
    /**
     * @desc Validates if the input is not null, empty or whitespace
     *
     * @subcontract null string {
     *   @requires string == null;
     *   @ensures \result = false;
     * }
     *
     * @subcontract empty string {
     *   @requires string != null &&
     *             string.length() == 0;
     *   @ensures \result = false;
     * }
     *
     * @subcontract whitespace string {
     *   @requires string != null &&
     *             string.replaceAll(" ", "").length() == 0;
     *   @signals \result = false;
     * }
     *
     * @subcontract not a null, empty or whitespace string {
     *   @requires string != null &&
     *             string.length() > 0 &&
     *             string.replaceAll(" ", "").length() > 0;
     *   @signals \result = true;
     * }
     *
     */
    public static boolean isNotNullEmptyOrWhitespace(String string) {
        if (string == null) {
            return false;
        }
        if (string.length() == 0) {
            return false;
        }
        if (string.replaceAll(" ", "").length() == 0) {
            return false;
        }
        return true;
    }
}
