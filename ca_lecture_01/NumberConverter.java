package ca_lecture_01;

import java.util.Scanner;

/**
 * Console-based number conversion program for POSITIVE INTEGERS.
 * Displays a menu and calls specialized converter classes to perform conversions.
 */
public class NumberConverter {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                // 1. Display the menu
                System.out.println("\n--- Positive Integer Conversion Simulator ---");
                System.out.println("1. Decimal to Binary");
                System.out.println("2. Binary to Decimal");
                System.out.println("3. Decimal to Hexadecimal");
                System.out.println("4. Binary to Hexadecimal");
                System.out.println("5. Hexadecimal to Decimal");
                System.out.println("6. Exit");
                System.out.print("Choose an option (1-6): ");

                String choice = scanner.nextLine().trim();
                String input;
                String result;

                try {
                    // 2. Switch to handle each conversion
                    switch (choice) {
                        case "1":
                            System.out.print("Enter positive decimal integer: ");
                            input = scanner.nextLine().trim();
                            DecimalToBinaryConverter d2b = new DecimalToBinaryConverter();
                            result = d2b.convert(input);
                            System.out.println("Binary: " + result);
                            break;
                        case "2":
                            System.out.print("Enter positive binary integer: ");
                            input = scanner.nextLine().trim();
                            BinaryToDecimalConverter b2d = new BinaryToDecimalConverter();
                            result = b2d.convert(input);
                            System.out.println("Decimal: " + result);
                            break;
                        case "3":
                            System.out.print("Enter positive decimal integer: ");
                            input = scanner.nextLine().trim();
                            DecimalToHexConverter d2h = new DecimalToHexConverter();
                            result = d2h.convert(input);
                            System.out.println("Hexadecimal: " + result);
                            break;
                        case "4":
                            System.out.print("Enter positive binary integer: ");
                            input = scanner.nextLine().trim();
                            BinaryToHexConverter b2h = new BinaryToHexConverter();
                            result = b2h.convert(input);
                            System.out.println("Hexadecimal: " + result);
                            break;
                        case "5":
                            System.out.print("Enter positive hexadecimal integer (e.g., 1FE): ");
                            input = scanner.nextLine().trim();
                            HexToDecimalConverter h2d = new HexToDecimalConverter();
                            result = h2d.convert(input);
                            System.out.println("Decimal: " + result);
                            break;
                        case "6":
                            System.out.println("Exiting. Goodbye!");
                            return; // Exit main
                        default:
                            System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                    }
                } catch (IllegalArgumentException e) {
                    // Catch errors from the converter classes (e.g., invalid input)
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
}