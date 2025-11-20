package ca_lecture_01;

/**
 * Converts Binary to Decimal using manual Math.pow logic.
 * Iterates from Right (LSB) to Left (MSB).
 */
public class BinaryToDecimalConverter {

    public String convert(String binaryStr) {
        if (binaryStr == null) return "0";

        String binary = binaryStr.trim();
        if (binary.isEmpty()) return "0";

        long decimal = 0;
        int power = 0; // Start with power 0 (2^0)

        // Algorithm: Scan from Right (end) to Left (start)
        for (int i = binary.length() - 1; i >= 0; i--) {
            char bit = binary.charAt(i);
            int bitValue;

            if (bit == '1') {
                bitValue = 1;
            } else if (bit == '0') {
                bitValue = 0;
            } else {
                throw new IllegalArgumentException("Not a binary number: " + bit);
            }

            // Calculation: Bit * 2^Power
            // Example: 1 * 2^0, 0 * 2^1, 1 * 2^2...
            long term = bitValue * (long) Math.pow(2, power);

            decimal += term;
            power++; // Increase power for next position
        }

        return String.valueOf(decimal);
    }
}