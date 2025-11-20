package ca_lecture_01;

/**
 * Converts Decimal to Binary using repeated division by 2.
 * This mimics the manual "pen and paper" method.
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
        // We build the binary string in reverse order (remainders from bottom to top)
        StringBuilder binary = new StringBuilder();

        // Step-by-step manual logic:
        // Loop until the number becomes 0
        while (n > 0) {
            long remainder = n % 2;  // Step A: Find remainder (0 or 1)
            binary.append(remainder); // Step B: Write it down
            n = n / 2;               // Step C: Divide number by 2 for next step
        }

        // 4. The remainders were collected in reverse order (LSB to MSB).
        // We must reverse it to get the correct binary string (MSB to LSB).
        return binary.reverse().toString();
    }
}