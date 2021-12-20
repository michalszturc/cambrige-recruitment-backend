package com.michal.szturc.utils;

/**
 * @author Michał Szturc
 */
public  class NumberUtils {

    public static String padWithZeros(String strToPad, int length) {
        if (strToPad.length() >= length) {
            return strToPad;
        }
        StringBuilder builder = new StringBuilder();
        while (builder.length() < length - strToPad.length()) {
            builder.append('0');
        }
        builder.append(strToPad);

        return builder.toString();
    }
}
