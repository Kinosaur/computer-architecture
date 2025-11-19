package ca_lecture_01;

/**
 * Converts Hexadecimal to Decimal using positional weights (1, 16, 256...).
 */
public class HexToDecimalConverter {

    public String convert(String hexStr) {
        if (hexStr == null) return "0";

        // Clean input: remove spaces and 'H' suffix if present
        String hex = hexStr.trim().toUpperCase();
        if (hex.endsWith("H")) {
            hex = hex.substring(0, hex.length() - 1);
        }
        if (hex.isEmpty()) return "0";

        long decimal = 0;
        long weight = 1; // Starts at 16^0

        // Algorithm: Scan from Right to Left
        for (int i = hex.length() - 1; i >= 0; i--) {
            char c = hex.charAt(i);
            int digitValue = getHexValue(c);

            decimal += (digitValue * weight);

            // Next position is worth 16x more
            weight *= 16;
        }

        return String.valueOf(decimal);
    }

    // Manual logic to get value 0-15 from char
    private int getHexValue(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0'; // e.g., '5' - '0' = 5
        } else if (c >= 'A' && c <= 'F') {
            return c - 'A' + 10; // e.g., 'A' - 'A' + 10 = 10
        } else {
            throw new IllegalArgumentException("Invalid hex char: " + c);
        }
    }
}