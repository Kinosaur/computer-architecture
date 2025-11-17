package ca_lecture_01;

/**
 * Converts a positive decimal integer string to a hexadecimal string.
 * Uses the repeated division-by-16 algorithm (remainder method).
 * Supports 64-bit positive integers (long).
 */
public class DecimalToHexConverter {

    /**
     * Converts a decimal string to hexadecimal.
     * @param decimalStr Positive decimal integer (e.g., "255")
     * @return Hexadecimal representation (e.g., "FF")
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

        StringBuilder hex = new StringBuilder();
        while (n > 0) {
            hex.append(toHexChar(n % 16));
            n = n / 16;
        }

        return hex.reverse().toString();
    }

    /**
     * Helper: Converts a single decimal digit (0-15) to its hex char.
     */
    private char toHexChar(long n) {
        if (n < 10) {
            return (char) (n + '0');
        } else {
            return (char) (n - 10 + 'A');
        }
    }
}