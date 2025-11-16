/**
 * Converts a binary number string (integer or float) to a decimal string.
 * Uses positional notation with weights 2^n for integers and 2^-n for fractions.
 * Handles inputs like "1101.011", ".011", and "1101".
 */
package ca_lecture_01;

public class BinaryToDecimalConverter {

    public String convert(String binaryStr) {
        if (binaryStr == null || binaryStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        binaryStr = binaryStr.trim();
        double finalResult = 0;

        if (binaryStr.contains(".")) {
            String[] parts = binaryStr.split("\\.");

            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid binary format");
            }

            if (!parts[0].isEmpty()) {
                finalResult += convertIntegerPart(parts[0]);
            }

            if (!parts[1].isEmpty()) {
                finalResult += convertFractionalPart(parts[1]);
            }
        } else {
            finalResult = convertIntegerPart(binaryStr);
        }

        return formatOutput(finalResult);
    }

    // Converts binary integer part using positional weights (2^0, 2^1, 2^2, ...)
    private int convertIntegerPart(String integerPart) {
        int decimal = 0;
        int weight = 1;
        for (int i = integerPart.length() - 1; i >= 0; i--) {
            char c = integerPart.charAt(i);
            if (c == '1') {
                decimal += weight;
            } else if (c != '0') {
                throw new IllegalArgumentException("Invalid binary character: " + c);
            }
            weight *= 2;
        }
        return decimal;
    }

    // Converts binary fractional part using positional weights (2^-1, 2^-2, 2^-3, ...)
    private double convertFractionalPart(String fractionalPart) {
        double decimal = 0;
        double weight = 0.5;
        for (int i = 0; i < fractionalPart.length(); i++) {
            char c = fractionalPart.charAt(i);
            if (c == '1') {
                decimal += weight;
            } else if (c != '0') {
                throw new IllegalArgumentException("Invalid binary character: " + c);
            }
            weight /= 2;
        }
        return decimal;
    }

    // Formats double output, removing unnecessary trailing zeros
    private String formatOutput(double value) {
        String result = String.format("%.10f", value).replaceAll("0+$", "").replaceAll("\\.$", "");
        return result;
    }
}