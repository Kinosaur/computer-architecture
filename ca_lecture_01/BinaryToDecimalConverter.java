package ca_lecture_01;

/**
 * Converts Binary to Decimal using manual positional weights.
 * Logic: Scan from Right (LSB) to Left (MSB), doubling the weight each time.
 */
public class BinaryToDecimalConverter {

    public String convert(String binaryStr) {
        if (binaryStr == null) return "0";

        String binary = binaryStr.trim();
        if (binary.isEmpty()) return "0";

        long decimal = 0;
        long weight = 1; // Step A: Starting weight is 2^0 = 1

        // Algorithm: Scan from Right (end) to Left (start)
        for (int i = binary.length() - 1; i >= 0; i--) {
            char bit = binary.charAt(i);

            if (bit == '1') {
                // Step B: If bit is 1, add current weight to total
                decimal += weight;
            } else if (bit != '0') {
                throw new IllegalArgumentException("Not a binary number: " + bit);
            }

            // Step C: Move to next position, so weight doubles (1, 2, 4, 8...)
            weight *= 2;
        }
        return String.valueOf(decimal);
    }
}