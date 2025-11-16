/**
 * Converts a decimal number string (integer or float) to a binary string.
 * - Positive integers and floats: standard binary representation.
 * - Negative integers: 8-bit Two's Complement representation.
 * - Negative floats: not supported.
 */
package ca_lecture_01;

public class DecimalToBinaryConverter {

    private static final int BIT_WIDTH = 8;

    public String convert(String decimalStr, int precision) {
        if (decimalStr == null || decimalStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        decimalStr = decimalStr.trim();

        if (decimalStr.startsWith("-") && decimalStr.contains(".")) {
            throw new IllegalArgumentException("Negative floating-point numbers are not supported.");
        }

        if (decimalStr.startsWith("-") && !decimalStr.contains(".")) {
            try {
                int integerNum = Integer.parseInt(decimalStr);
                if (integerNum < -128 || integerNum > 127) {
                    throw new IllegalArgumentException("Number out of 8-bit signed range [-128, 127]: " + integerNum);
                }
                return convertNegativeToTwosComplement(integerNum);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid integer format: " + decimalStr);
            }
        }

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
                    integerBinary = convertPositiveInteger(integerNum);
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
                integerBinary = convertPositiveInteger(integerNum);
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

    private String convertPositiveInteger(int n) {
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

    private String convertNegativeToTwosComplement(int n) {
        // Step 1: Get positive binary from absolute value
        String positiveBinary = convertPositiveInteger(-n);

        // Step 2: Pad to 8 bits
        String padded = padToWidth(positiveBinary);

        // Step 3: Invert bits (One's Complement)
        String inverted = invertBits(padded);

        // Step 4: Add one
        return addOne(inverted);
    }

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

    private String padToWidth(String s) {
        while (s.length() < BIT_WIDTH) {
            s = "0" + s;
        }
        return s;
    }

    private String invertBits(String binary) {
        StringBuilder result = new StringBuilder();
        for (char bit : binary.toCharArray()) {
            result.append(bit == '0' ? '1' : '0');
        }
        return result.toString();
    }

    private String addOne(String binary) {
        char[] bits = binary.toCharArray();
        int carry = 1;

        for (int i = bits.length - 1; i >= 0 && carry == 1; i--) {
            if (bits[i] == '0') {
                bits[i] = '1';
                carry = 0;
            } else {
                bits[i] = '0';
            }
        }

        return new String(bits);
    }
}