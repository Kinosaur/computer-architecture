/**
 * Converts a decimal number string (integer or float) to a hexadecimal string.
 * Uses repeated division by 16 for integers and repeated multiplication by 16 for fractions.
 * Appends "H" suffix to output. Handles inputs like "510.375", ".93", and "510".
 * Fractional conversion continues until termination (fraction becomes 0) or reaches a practical limit.
 */
package ca_lecture_01;

public class DecimalToHexConverter {

    private static final int MAX_HEX_FRACTION_DIGITS = 20;

    public String convert(String decimalStr) {
        if (decimalStr == null || decimalStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        decimalStr = decimalStr.trim();
        String integerHex = "0";
        String fractionalHex = "";

        if (decimalStr.contains(".")) {
            String[] parts = decimalStr.split("\\.");

            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid decimal format");
            }

            if (!parts[0].isEmpty()) {
                try {
                    int integerNum = Integer.parseInt(parts[0]);
                    integerHex = convertIntegerToHex(integerNum);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid integer part: " + parts[0]);
                }
            }

            if (!parts[1].isEmpty()) {
                try {
                    double fractionalNum = Double.parseDouble("0." + parts[1]);
                    fractionalHex = convertFractionToHex(fractionalNum);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid fractional part: " + parts[1]);
                }
            }
        } else {
            try {
                int integerNum = Integer.parseInt(decimalStr);
                integerHex = convertIntegerToHex(integerNum);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid decimal number: " + decimalStr);
            }
        }

        String finalHex;
        if (fractionalHex.isEmpty()) {
            finalHex = integerHex;
        } else {
            finalHex = integerHex + "." + fractionalHex;
        }

        return finalHex + "H";
    }

    // Converts integer part using repeated division by 16
    private String convertIntegerToHex(int n) {
        if (n == 0) {
            return "0";
        }
        String hex = "";
        while (n > 0) {
            hex = toHexChar(n % 16) + hex;
            n = n / 16;
        }
        return hex;
    }

    // Converts fractional part using repeated multiplication by 16
    // Stops when fraction becomes 0 (terminating) or reaches MAX_HEX_FRACTION_DIGITS (non-terminating)
    private String convertFractionToHex(double frac) {
        if (frac == 0) {
            return "";
        }
        String hex = "";
        int iterations = 0;

        while (frac > 0 && iterations < MAX_HEX_FRACTION_DIGITS) {
            frac = frac * 16;
            int digit = (int) frac;
            hex = hex + toHexChar(digit);
            frac = frac - digit;
            iterations++;
        }
        return hex;
    }

    // Converts single digit (0-15) to hexadecimal character (0-9, A-F)
    private char toHexChar(int n) {
        if (n < 0 || n > 15) {
            throw new IllegalArgumentException("Value out of hex range (0-15)");
        }
        if (n < 10) {
            return (char) (n + '0');
        } else {
            return (char) (n - 10 + 'A');
        }
    }
}