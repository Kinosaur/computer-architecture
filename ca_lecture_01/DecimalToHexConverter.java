package ca_lecture_01;

/**
 * Converts Decimal to Hexadecimal using repeated division by 16.
 * Manual lookup array is used to map 10-15 to A-F.
 */
public class DecimalToHexConverter {

    // Manual mapping for remainders 0-15
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
            // Step A: Find remainder (0-15)
            int remainder = (int) (n % 16);

            // Step B: Map remainder to Hex Character (e.g., 10 -> 'A')
            hex.append(HEX_CHARS[remainder]);

            // Step C: Divide number by 16 for next step
            n = n / 16;
        }

        // Step D: Reverse the string (results came bottom-to-top)
        return hex.reverse().toString();
    }
}