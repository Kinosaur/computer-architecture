package ca_lecture_01;

public class DecimalToBinaryConverter {

    /**
     * Converts a positive decimal integer string to binary.
     * @param decimalStr The decimal number as a string (e.g., "13")
     * @return The binary string representation (e.g., "1101")
     * @throws IllegalArgumentException if input is invalid
     */
    public String convert(String decimalStr) {
        if (decimalStr == null || decimalStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        // Check for non-digit characters
        for (int i = 0; i < decimalStr.length(); i++) {
            if (decimalStr.charAt(i) < '0' || decimalStr.charAt(i) > '9') {
                throw new IllegalArgumentException("Input must be a positive integer.");
            }
        }

        int n;
        try {
            n = Integer.parseInt(decimalStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid decimal number: " + decimalStr);
        }

        if (n == 0) {
            return "0";
        }

        String binary = "";
        while (n > 0) {
            int remainder = n % 2;
            binary = remainder + binary; // Prepend the remainder
            n = n / 2;
        }
        return binary;
    }
}