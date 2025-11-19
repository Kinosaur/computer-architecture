package ca_lecture_01;

/**
 * Converts Decimal to Hexadecimal using repeated division by 16.
 * Uses a manual lookup array for 0-15 -> 0-F.
 */
public class DecimalToHexConverter {

    // Manual mapping for 0-15
    private static final char[] HEX_CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    public String convert(String decimalStr) {
        if (decimalStr == null || decimalStr.trim().isEmpty()) return "0";

        long n;
        try {
            n = Long.parseLong(decimalStr.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Input must be a number.");
        }

        if (n < 0) throw new IllegalArgumentException("Positive integers only.");
        if (n == 0) return "0";

        StringBuilder hex = new StringBuilder();

        // Algorithm: Repeated Division by 16
        while (n > 0) {
            int remainder = (int) (n % 16);
            // Map remainder (0-15) to char (0-F) manually
            hex.append(HEX_CHARS[remainder]);
            n = n / 16;
        }

        return hex.reverse().toString();
    }
}