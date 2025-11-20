package ca_lecture_01;

/**
 * Converts Hexadecimal to Decimal using manual positional weights.
 * Formula: Decimal = Sum of (Digit * 16^Power)
 */
public class HexToDecimalConverter {

    public String convert(String hexStr) {
        // 1. Validation
        if (hexStr == null) return "0";

        // Clean input: remove spaces and 'H' suffix if present
        String hex = hexStr.trim().toUpperCase();
        if (hex.endsWith("H")) {
            hex = hex.substring(0, hex.length() - 1);
        }
        if (hex.isEmpty()) return "0";

        long decimal = 0;
        int length = hex.length();

        // 2. Algorithm: Sum of Weights (Math.pow method)
        // We iterate through each character of the hex string
        for (int i = 0; i < length; i++) {
            // Step A: Get the character at position 'i' (e.g., 'A' in "1A")
            char c = hex.charAt(i);

            // Step B: Get its decimal value (0-15)
            int digitValue = getHexValue(c);

            // Step C: Calculate the power of 16 based on position
            // The rightmost digit has power 0.
            // Example: "1A" (length 2)
            // Index 0 ('1'): Power = 2 - 1 - 0 = 1
            // Index 1 ('A'): Power = 2 - 1 - 1 = 0
            int power = length - 1 - i;

            // Step D: Calculate value for this position: Digit * 16^Power
            // We cast Math.pow result to long
            long positionValue = digitValue * (long) Math.pow(16, power);

            // Step E: Add to total
            decimal += positionValue;
        }

        return String.valueOf(decimal);
    }

    // Helper to map char '0'-'F' to integer 0-15
    private int getHexValue(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0'; // '5' - '0' = 5
        } else if (c >= 'A' && c <= 'F') {
            return c - 'A' + 10; // 'A' - 'A' + 10 = 10
        } else {
            throw new IllegalArgumentException("Invalid hex char: " + c);
        }
    }
}