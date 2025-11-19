package ca_lecture_01;

/**
 * Converts Binary to Decimal using positional weights (1, 2, 4, 8...).
 */
public class BinaryToDecimalConverter {

    public String convert(String binaryStr) {
        if (binaryStr == null) return "0";

        String binary = binaryStr.trim();
        if (binary.isEmpty()) return "0";

        long decimal = 0;
        long weight = 1; // Starts at 2^0

        // Algorithm: Scan from Right to Left
        for (int i = binary.length() - 1; i >= 0; i--) {
            char bit = binary.charAt(i);

            if (bit == '1') {
                decimal += weight;
            } else if (bit != '0') {
                throw new IllegalArgumentException("Not a binary number: " + bit);
            }

            // Next position is worth 2x more
            weight *= 2;
        }

        return String.valueOf(decimal);
    }
}