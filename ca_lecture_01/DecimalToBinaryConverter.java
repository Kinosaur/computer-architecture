package ca_lecture_01;

/**
 * Converts Decimal to Binary using repeated division by 2.
 */
public class DecimalToBinaryConverter {

    public String convert(String decimalStr) {
        // 1. Basic Validation
        if (decimalStr == null || decimalStr.trim().isEmpty()) {
            return "0";
        }

        // 2. Parse Input
        long n;
        try {
            n = Long.parseLong(decimalStr.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Input must be a number.");
        }

        if (n < 0) throw new IllegalArgumentException("Positive integers only.");
        if (n == 0) return "0";

        // 3. Algorithm: Repeated Division by 2
        StringBuilder binary = new StringBuilder();
        while (n > 0) {
            long remainder = n % 2;
            binary.append(remainder);
            n = n / 2;
        }

        // 4. Reverse to get correct order
        return binary.reverse().toString();
    }
}