package ca_lecture_01;

/**
 * Converts a positive hexadecimal integer string to a decimal string.
 * Uses positional notation (sum of weights 16^n).
 * Supports 64-bit positive integers (long).
 */
public class HexToDecimalConverter {

    /**
     * Converts a positive hexadecimal integer string to decimal.
     * @param hexStr The hex number as a string (e.g., "1FE" or "7FFFFFFFFFFFFFFF")
     * @return The decimal string representation (e.g., "510")
     * @throws IllegalArgumentException if input is invalid
     */
    public String convert(String hexStr) {
        if (hexStr == null || hexStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        hexStr = hexStr.trim();

        if (hexStr.endsWith("H") || hexStr.endsWith("h")) {
            hexStr = hexStr.substring(0, hexStr.length() - 1);
        }

        if (hexStr.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        hexStr = hexStr.toUpperCase();

        if (hexStr.length() > 16) {
            throw new IllegalArgumentException("Hexadecimal string too long (max 16 digits)");
        }

        if (hexStr.length() == 16 && hexStr.charAt(0) > '7') {
            throw new IllegalArgumentException("Hexadecimal value exceeds positive long range");
        }

        long decimal = 0;
        long weight = 1;

        for (int i = hexStr.length() - 1; i >= 0; i--) {
            char c = hexStr.charAt(i);
            int digit;

            if (c >= '0' && c <= '9') {
                digit = c - '0';
            } else if (c >= 'A' && c <= 'F') {
                digit = c - 'A' + 10;
            } else {
                throw new IllegalArgumentException("Invalid hex character: " + c);
            }

            decimal = decimal + (digit * weight);
            weight = weight * 16;
        }

        return String.valueOf(decimal);
    }
}