package ca_lecture_01;

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

        int decimal = 0;
        int weight = 1; // Represents 2^0, 2^1, 2^2, ...

        // Iterate from the rightmost character
        for (int i = binaryStr.length() - 1; i >= 0; i--) {
            char c = binaryStr.charAt(i);

            if (c == '1') {
                decimal = decimal + weight;
            } else if (c != '0') {
                // Input contains an invalid character
                throw new IllegalArgumentException("Invalid binary character: " + c);
            }
            weight = weight * 2;
        }

        return String.valueOf(decimal);
    }
}