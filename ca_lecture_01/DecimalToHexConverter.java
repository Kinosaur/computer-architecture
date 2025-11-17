package ca_lecture_01;

public class DecimalToHexConverter {

    public String convert(String decimalStr) {
        if (decimalStr == null || decimalStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        // Check for non-digit characters
        for (int i = 0; i < decimalStr.length(); i++) {
            if (decimalStr.charAt(i) < '0' || decimalStr.charAt(i) > '9') {
                throw new IllegalArgumentException("Input must be a positive integer.");
            }
        }

        int n;
        try {
            n = Integer.parseInt(decimalStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid decimal number: " + decimalStr);
        }

        if (n == 0) {
            return "0H";
        }

        String hex = "";
        while (n > 0) {
            int remainder = n % 16;
            hex = toHexChar(remainder) + hex; // Prepend the hex char
            n = n / 16;
        }

        return hex + "H";
    }

    // Helper: Converts a single decimal digit (0-15) to its hex char.
    private char toHexChar(int n) {
        if (n < 10) {
            return (char) (n + '0'); // '0' is 48
        } else {
            return (char) (n - 10 + 'A'); // 'A' is 65
        }
    }
}