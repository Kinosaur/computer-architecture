package ca_lecture_01;

import java.util.Scanner;

/**
 * Main class to run the Positive Integer Conversion Simulator.
 */
public class NumberConverter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Outer Loop: The Main Menu
        while (true) {
            System.out.println("\n============================================");
            System.out.println("   Number Converter (Positive Integers)     ");
            System.out.println("============================================");
            System.out.println("1. Decimal to Binary");
            System.out.println("2. Binary to Decimal");
            System.out.println("3. Decimal to Hexadecimal");
            System.out.println("4. Binary to Hexadecimal");
            System.out.println("5. Hexadecimal to Decimal");
            System.out.println("6. Hexadecimal to Binary"); // NEW OPTION
            System.out.println("7. Exit");
            System.out.println("--------------------------------------------");
            System.out.print("Choose option: ");

            String choiceLine = scanner.nextLine();
            if (choiceLine == null) break;
            String choice = choiceLine.trim();

            if (choice.equals("7")) {
                System.out.println("Goodbye!");
                break;
            }

            // Validate menu choice (Updated to 1-6)
            if (!choice.matches("[1-6]")) {
                System.out.println("Invalid option. Please enter 1-7.");
                continue;
            }

            // Inner Loop: "Continue with current program"
            boolean stayInCurrentMode = true;

            while (stayInCurrentMode) {
                try {
                    String input;
                    String result;

                    switch (choice) {
                        case "1" -> {
                            System.out.print("\n[Decimal -> Binary] Enter number: ");
                            input = scanner.nextLine();
                            result = new DecimalToBinaryConverter().convert(input);
                            System.out.println(">> Binary: " + result);
                        }
                        case "2" -> {
                            System.out.print("\n[Binary -> Decimal] Enter number: ");
                            input = scanner.nextLine();
                            result = new BinaryToDecimalConverter().convert(input);
                            System.out.println(">> Decimal: " + result);
                        }
                        case "3" -> {
                            System.out.print("\n[Decimal -> Hex] Enter number: ");
                            input = scanner.nextLine();
                            result = new DecimalToHexConverter().convert(input);
                            System.out.println(">> Hexadecimal: " + result);
                        }
                        case "4" -> {
                            System.out.print("\n[Binary -> Hex] Enter number: ");
                            input = scanner.nextLine();
                            result = new BinaryToHexConverter().convert(input);
                            System.out.println(">> Hexadecimal: " + result);
                        }
                        case "5" -> {
                            System.out.print("\n[Hexadecimal -> Decimal] Enter number: ");
                            input = scanner.nextLine();
                            result = new HexToDecimalConverter().convert(input);
                            System.out.println(">> Decimal: " + result);
                        }
                        case "6" -> {
                            System.out.print("\n[Hexadecimal -> Binary] Enter number: ");
                            input = scanner.nextLine();
                            result = new HexToBinaryConverter().convert(input);
                            System.out.println(">> Binary: " + result);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

                // The "One-Step Confirmation"
                System.out.print("\nConvert another number in this mode? (Y/N): ");
                String answer = scanner.nextLine().trim();

                if (!answer.equalsIgnoreCase("Y")) {
                    stayInCurrentMode = false; // Breaks inner loop, returns to Main Menu
                }
            }
        }
        scanner.close();
    }
}