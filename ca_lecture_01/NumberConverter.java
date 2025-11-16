package ca_lecture_01;

import java.util.Scanner;

/**
 * Console-based number conversion program.
 * Displays a menu and calls specialized converter classes to perform conversions.
 */
public class NumberConverter {

    private static final int BINARY_PRECISION = 4;

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                displayMenu();
                String choice = scanner.nextLine().trim();

                String input;
                String result;

                try {
                    switch (choice) {
                        case "1":
                            System.out.print("Enter decimal number (e.g., 13 or .375): ");
                            input = scanner.nextLine().trim();
                            DecimalToBinaryConverter d2b = new DecimalToBinaryConverter();
                            result = d2b.convert(input, BINARY_PRECISION);
                            System.out.println("Binary: " + result);
                            break;
                        case "2":
                            System.out.print("Enter binary number (e.g., 1101 or .011): ");
                            input = scanner.nextLine().trim();
                            BinaryToDecimalConverter b2d = new BinaryToDecimalConverter();
                            result = b2d.convert(input);
                            System.out.println("Decimal: " + result);
                            break;
                        case "3":
                            System.out.print("Enter decimal number (e.g., 510 or .93): ");
                            input = scanner.nextLine().trim();
                            DecimalToHexConverter d2h = new DecimalToHexConverter();
                            result = d2h.convert(input);
                            System.out.println("Hexadecimal: " + result);
                            break;
                        case "4":
                            System.out.print("Enter binary number (e.g., 101101 or .01): ");
                            input = scanner.nextLine().trim();
                            BinaryToHexConverter b2h = new BinaryToHexConverter();
                            result = b2h.convert(input);
                            System.out.println("Hexadecimal: " + result);
                            break;
                        case "5":
                            System.out.print("Enter hexadecimal number (e.g., 1FE or .6H): ");
                            input = scanner.nextLine().trim();
                            HexToDecimalConverter h2d = new HexToDecimalConverter();
                            result = h2d.convert(input);
                            System.out.println("Decimal: " + result);
                            break;
                        case "6":
                            System.out.println("Exiting. Goodbye!");
                            scanner.close();
                            return;
                        default:
                            System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n--- Number Conversion Simulator ---");
        System.out.println("1. Decimal to Binary");
        System.out.println("2. Binary to Decimal");
        System.out.println("3. Decimal to Hexadecimal");
        System.out.println("4. Binary to Hexadecimal");
        System.out.println("5. Hexadecimal to Decimal");
        System.out.println("6. Exit");
        System.out.print("Choose an option (1-6): ");
    }
}