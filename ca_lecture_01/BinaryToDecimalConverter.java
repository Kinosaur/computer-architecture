package ca_lecture_01;

/**
 * Converts a positive binary integer string to a decimal string.
 * Uses positional notation (sum of weights 2^n).
 * Supports 64-bit positive integers (long).
 */
public class BinaryToDecimalConverter {

    /**
     * Converts a positive binary integer string to decimal.
     * @param binaryStr The binary number as a string (e.g., "1101")
     * @return The decimal string representation (e.g., "13")
     * @throws IllegalArgumentException if input is invalid
     */
    public String convert(String binaryStr) {
        if (binaryStr == null || binaryStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        binaryStr = binaryStr.trim();
        if (binaryStr.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        if (binaryStr.length() > 63) {
            throw new IllegalArgumentException("Binary string too long (max 63 bits)");
        }

        long decimal = 0;
        long weight = 1;

        for (int i = binaryStr.length() - 1; i >= 0; i--) {
            char c = binaryStr.charAt(i);

            if (c == '1') {
                decimal = decimal + weight;
            } else if (c != '0') {
                throw new IllegalArgumentException("Invalid binary character: " + c);
            }
            weight = weight * 2;
        }

        return String.valueOf(decimal);
    }
}