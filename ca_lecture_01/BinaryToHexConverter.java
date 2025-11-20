package ca_lecture_01;

/**
 * Converts Binary to Hexadecimal by grouping bits into 4 (nibbles).
 * This mimics the manual grouping method.
 */
public class BinaryToHexConverter {

    public String convert(String binaryStr) {
        if (binaryStr == null) return "0";
        String binary = binaryStr.trim();
        if (binary.isEmpty()) return "0";

        // Step A: Pad with leading zeros until length is divisible by 4
        // e.g., "101" (len 3) -> "0101" (len 4)
        while (binary.length() % 4 != 0) {
            binary = "0" + binary;
        }

        StringBuilder hex = new StringBuilder();

        // Step B: Process 4 bits at a time (Left to Right)
        for (int i = 0; i < binary.length(); i += 4) {
            // Get the group of 4 bits
            String nibble = binary.substring(i, i + 4);
            // Convert 4 bits -> 1 Hex Digit
            hex.append(nibbleToHex(nibble));
        }

        return hex.toString();
    }

    // Manual switch case for 4-bit groups
    private char nibbleToHex(String nibble) {
        return switch (nibble) {
            case "0000" -> '0'; case "0001" -> '1'; case "0010" -> '2'; case "0011" -> '3';
            case "0100" -> '4'; case "0101" -> '5'; case "0110" -> '6'; case "0111" -> '7';
            case "1000" -> '8'; case "1001" -> '9'; case "1010" -> 'A'; case "1011" -> 'B';
            case "1100" -> 'C'; case "1101" -> 'D'; case "1110" -> 'E'; case "1111" -> 'F';
            default -> throw new IllegalArgumentException("Invalid binary sequence");
        };
    }
}