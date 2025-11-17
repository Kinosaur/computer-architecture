package ca_lecture_01;

/**
 * Converts positive decimal integers to binary representation.
 * Uses the repeated division-by-2 algorithm (remainder method).
 * Supports 64-bit positive integers (long).
 */
public class DecimalToBinaryConverter {

    /**
     * Converts a decimal string to binary.
     * @param decimalStr Positive decimal integer (e.g., "2147483648")
     * @return Binary representation (e.g., "10000000000000000000000000000000")
     * @throws IllegalArgumentException if input is invalid
     */
    public String convert(String decimalStr) {
        if (decimalStr == null || decimalStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        decimalStr = decimalStr.trim();
        if (decimalStr.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        long n;
        try {
            n = Long.parseLong(decimalStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Input must be a valid positive integer");
        }

        if (n < 0) {
            throw new IllegalArgumentException("Input must be a positive integer");
        }

        if (n == 0) {
            return "0";
        }

        StringBuilder binary = new StringBuilder();
        while (n > 0) {
            binary.append(n % 2);
            n = n / 2;
        }

        return binary.reverse().toString();
    }
}