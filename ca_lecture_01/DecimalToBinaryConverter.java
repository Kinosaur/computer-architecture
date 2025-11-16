/**
 * Converts a decimal number string (integer or float) to a binary string.
 * Uses repeated division by 2 for integers and repeated multiplication by 2 for fractions.
 * Handles inputs like "13.375", ".375", and "13".
 */
package ca_lecture_01;

public class DecimalToBinaryConverter {

    public String convert(String decimalStr, int precision) {
        if (decimalStr == null || decimalStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        decimalStr = decimalStr.trim();
        String integerBinary = "0";
        String fractionalBinary = "";

        if (decimalStr.contains(".")) {
            String[] parts = decimalStr.split("\\.");

            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid decimal format");
            }

            if (!parts[0].isEmpty()) {
                try {
                    int integerNum = Integer.parseInt(parts[0]);
                    integerBinary = convertIntegerToBinary(integerNum);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid integer part: " + parts[0]);
                }
            }

            if (!parts[1].isEmpty()) {
                try {
                    double fractionalNum = Double.parseDouble("0." + parts[1]);
                    fractionalBinary = convertFractionToBinary(fractionalNum, precision);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid fractional part: " + parts[1]);
                }
            }
        } else {
            try {
                int integerNum = Integer.parseInt(decimalStr);
                integerBinary = convertIntegerToBinary(integerNum);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid decimal number: " + decimalStr);
            }
        }

        if (fractionalBinary.isEmpty()) {
            return integerBinary;
        } else {
            return integerBinary + "." + fractionalBinary;
        }
    }

    // Converts integer part using repeated division by 2
    private String convertIntegerToBinary(int n) {
        if (n == 0) {
            return "0";
        }
        String binary = "";
        while (n > 0) {
            binary = (n % 2) + binary;
            n = n / 2;
        }
        return binary;
    }

    // Converts fractional part using repeated multiplication by 2
    private String convertFractionToBinary(double frac, int precision) {
        if (frac == 0) {
            return "";
        }
        String binary = "";
        for (int i = 0; i < precision; i++) {
            if (frac == 0) {
                break;
            }
            frac = frac * 2;
            if (frac >= 1) {
                binary = binary + "1";
                frac = frac - 1;
            } else {
                binary = binary + "0";
            }
        }
        return binary;
    }
}