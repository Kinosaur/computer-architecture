package ca_lecture_01;

/**
 * Converts a positive binary integer string to a hexadecimal string.
 * Groups bits into 4-bit nibbles.
 */
public class BinaryToHexConverter {

    /**
     * Converts a positive binary integer string to hexadecimal.
     * @param binaryStr The binary number as a string (e.g., "11010")
     * @return The hexadecimal string representation (e.g., "1A")
     * @throws IllegalArgumentException if input is invalid
     */
    public String convert(String binaryStr) {
        if (binaryStr == null || binaryStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        binaryStr = binaryStr.trim();
        if (binaryStr.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        for (int i = 0; i < binaryStr.length(); i++) {
            char c = binaryStr.charAt(i);
            if (c != '0' && c != '1') {
                throw new IllegalArgumentException("Invalid binary character: " + c);
            }
        }

        int padding = binaryStr.length() % 4;
        if (padding != 0) {
            int zerosToPad = 4 - padding;
            String zeros = "0".repeat(zerosToPad);
            binaryStr = zeros + binaryStr;
        }

        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < binaryStr.length(); i = i + 4) {
            String nibble = binaryStr.substring(i, i + 4);
            hex.append(binaryNibbleToHexChar(nibble));
        }

        return hex.toString();
    }

    /**
     * Helper: Converts a 4-bit binary string (nibble) to a hex char.
     */
    private char binaryNibbleToHexChar(String nibble) {
        return switch (nibble) {
            case "0000" -> '0';
            case "0001" -> '1';
            case "0010" -> '2';
            case "0011" -> '3';
            case "0100" -> '4';
            case "0101" -> '5';
            case "0110" -> '6';
            case "0111" -> '7';
            case "1000" -> '8';
            case "1001" -> '9';
            case "1010" -> 'A';
            case "1011" -> 'B';
            case "1100" -> 'C';
            case "1101" -> 'D';
            case "1110" -> 'E';
            case "1111" -> 'F';
            default -> throw new IllegalStateException("Unreachable: invalid binary nibble");
        };
    }
}