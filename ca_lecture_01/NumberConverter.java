package ca_lecture_01;

import java.util.Scanner;

/**
 * Console-based number conversion program for positive integers.
 * Displays a menu and calls specialized converter classes.
 * Supports 64-bit positive integers (long).
 */
public class NumberConverter {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n--- Positive Integer Conversion Simulator (64-bit) ---");
                System.out.println("1. Decimal to Binary");
                System.out.println("2. Binary to Decimal");
                System.out.println("3. Decimal to Hexadecimal");
                System.out.println("4. Binary to Hexadecimal");
                System.out.println("5. Hexadecimal to Decimal");
                System.out.println("6. Exit");
                System.out.print("Choose an option (1-6): ");

                String choice = scanner.nextLine();
                if (choice == null) {
                    break;
                }

                String input;
                String result;

                try {
                    switch (choice.trim()) {
                        case "1" -> {
                            System.out.print("Enter positive decimal integer: ");
                            input = scanner.nextLine();
                            DecimalToBinaryConverter d2b = new DecimalToBinaryConverter();
                            result = d2b.convert(input);
                            System.out.println("Binary: " + result);
                        }
                        case "2" -> {
                            System.out.print("Enter positive binary integer: ");
                            input = scanner.nextLine();
                            BinaryToDecimalConverter b2d = new BinaryToDecimalConverter();
                            result = b2d.convert(input);
                            System.out.println("Decimal: " + result);
                        }
                        case "3" -> {
                            System.out.print("Enter positive decimal integer: ");
                            input = scanner.nextLine();
                            DecimalToHexConverter d2h = new DecimalToHexConverter();
                            result = d2h.convert(input);
                            System.out.println("Hexadecimal: " + result);
                        }
                        case "4" -> {
                            System.out.print("Enter positive binary integer: ");
                            input = scanner.nextLine();
                            BinaryToHexConverter b2h = new BinaryToHexConverter();
                            result = b2h.convert(input);
                            System.out.println("Hexadecimal: " + result);
                        }
                        case "5" -> {
                            System.out.print("Enter positive hexadecimal integer (e.g., 1FE): ");
                            input = scanner.nextLine();
                            HexToDecimalConverter h2d = new HexToDecimalConverter();
                            result = h2d.convert(input);
                            System.out.println("Decimal: " + result);
                        }
                        case "6" -> {
                            System.out.println("Exiting. Goodbye!");
                            return;
                        }
                        default -> System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
}