package ca_lecture_01;

/**
 * Converts Hexadecimal to Binary by converting each hex digit to 4 bits.
 */
public class HexToBinaryConverter {

    public String convert(String hexStr) {
        if (hexStr == null) return "0";

        // Clean input
        String hex = hexStr.trim().toUpperCase();
        if (hex.endsWith("H")) {
            hex = hex.substring(0, hex.length() - 1);
        }
        if (hex.isEmpty()) return "0";

        StringBuilder binary = new StringBuilder();

        // Algorithm: Convert each hex char to 4 binary bits
        for (int i = 0; i < hex.length(); i++) {
            char c = hex.charAt(i);
            binary.append(hexCharToBinary(c));
        }

        return binary.toString();
    }

    // Manual lookup for 0-F -> 4-bit binary
    private String hexCharToBinary(char c) {
        return switch (c) {
            case '0' -> "0000";
            case '1' -> "0001";
            case '2' -> "0010";
            case '3' -> "0011";
            case '4' -> "0100";
            case '5' -> "0101";
            case '6' -> "0110";
            case '7' -> "0111";
            case '8' -> "1000";
            case '9' -> "1001";
            case 'A' -> "1010";
            case 'B' -> "1011";
            case 'C' -> "1100";
            case 'D' -> "1101";
            case 'E' -> "1110";
            case 'F' -> "1111";
            default -> throw new IllegalArgumentException("Invalid hex char: " + c);
        };
    }
}