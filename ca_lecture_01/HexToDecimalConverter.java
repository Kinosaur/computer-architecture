package ca_lecture_01;

/**
 * Converts Hexadecimal to Decimal using the manual positional weight formula.
 * Formula: Value = Sum of (Digit * 16^Power)
 */
public class HexToDecimalConverter {

    public String convert(String hexStr) {
        if (hexStr == null) return "0";

        // Clean input: remove spaces and optional 'H' suffix
        String hex = hexStr.trim().toUpperCase();
        if (hex.endsWith("H")) {
            hex = hex.substring(0, hex.length() - 1);
        }
        if (hex.isEmpty()) return "0";

        long decimal = 0;
        int length = hex.length();

        // Algorithm: Positional Notation
        // We iterate from Right (LSB) to Left (MSB).
        // The rightmost digit is at position 0 (16^0).
        for (int i = 0; i < length; i++) {
            // Step A: Get the character at index i
            // Example: Input "1A", i=0 -> '1', i=1 -> 'A'
            char c = hex.charAt(i);

            // Step B: Get the decimal value of this hex digit (0-15)
            int digitValue = getHexValue(c);

            // Step C: Calculate the power of 16 based on position from the right
            // Example "1A" (len 2):
            // i=0 ('1') -> power = 2 - 1 - 0 = 1
            // i=1 ('A') -> power = 2 - 1 - 1 = 0
            int power = length - 1 - i;

            // Step D: Calculate position value: Digit * 16^Power
            long positionValue = digitValue * (long) Math.pow(16, power);

            // Step E: Add to total sum
            decimal += positionValue;
        }

        return String.valueOf(decimal);
    }

    // Manual logic to map hex char to value 0-15
    private int getHexValue(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';      // '0'-'9' -> 0-9
        } else if (c >= 'A' && c <= 'F') {
            return c - 'A' + 10; // 'A'-'F' -> 10-15
        } else {
            throw new IllegalArgumentException("Invalid hex char: " + c);
        }
    }
}