# Java Number Base Conversion Simulator

## 1. Overview
This is a simple, console-based Java application for converting positive integers between different number bases. It provides a menu-driven interface allowing users to select a conversion type and input a number.

The project uses a clean, object-oriented design where each conversion type is handled by its own specialized class. All conversion logic is implemented manually (using algorithms like repeated division and positional weighting) without using Java’s built-in base-conversion methods.

This project focuses on positive 64-bit integers (`long`) to ensure all conversions are robust and reversible.

## 2. Features
- Decimal to Binary (e.g., 42 → 101010)  
- Binary to Decimal (e.g., 101010 → 42)  
- Decimal to Hexadecimal (e.g., 255 → FF)  
- Hexadecimal to Decimal (e.g., FF → 255)  
- Binary to Hexadecimal (e.g., 10101010 → AA)  
- Robust validation: handles invalid input, empty strings, and overflow  
- 64-bit support: correctly processes numbers up to `Long.MAX_VALUE`

## 3. Project Structure
The project is organized into separate classes following the Single Responsibility Principle.

```
└── ca_lecture_01/
    ├── NumberConverter.java            (Main application, runs the menu)
    ├── DecimalToBinaryConverter.java   (Decimal → Binary)
    ├── BinaryToDecimalConverter.java   (Binary → Decimal)
    ├── DecimalToHexConverter.java      (Decimal → Hexadecimal)
    ├── HexToDecimalConverter.java      (Hexadecimal → Decimal)
    └── BinaryToHexConverter.java       (Binary → Hexadecimal)
```

## 4. How to Run

### Requirements
- Java JDK (Version 17 or higher recommended)

### Steps
1. Open your terminal or command prompt.  
2. Navigate to the directory containing the `ca_lecture_01` folder.  
3. Compile all Java files:
   ```bash
   javac ca_lecture_01/*.java
   ```

4. Run the main application:
   ```bash
   java ca_lecture_01.NumberConverter
   ```
   
5. Follow the on-screen menu to choose your conversion.

### Example Output

```
--- Positive Integer Conversion Simulator (64-bit) ---
1. Decimal to Binary
2. Binary to Decimal
3. Decimal to Hexadecimal
4. Binary to Hexadecimal
5. Hexadecimal to Decimal
6. Exit
Choose an option (1-6): 3
Enter positive decimal integer: 1024
Hexadecimal: 400
```

## 5. Design Decisions

### 64-bit Integers (`long`)

The project standardizes on using `long` for all calculations. This avoids reversibility errors (e.g., Binary → Decimal → Binary) and supports the full positive integer range available on modern systems.

### Manual Algorithms

Built-in functions such as `Long.parseLong(s, 2)` or `Long.toHexString()` are not used. All logic is manually implemented using:

* Repeated division
* Positional weighting
* Nibble grouping

### Strict Validation

Each converter class validates its own input:

* Trims whitespace
* Rejects empty strings
* Validates character sets (e.g., only `0` and `1` for binary)

### Overflow Protection

Converters include upfront checks to avoid overflow:

* `BinaryToDecimalConverter` rejects inputs longer than 63 bits
* `HexToDecimalConverter` validates against positive `long` range (`0x7FFFFFFFFFFFFFFF`)

## 6. Future Enhancements

### Negative Number Support

* Implement Two’s Complement for fixed-width formats (16-bit, 32-bit, 64-bit).

### Floating-Point Support

* Add conversion for fractional numbers (e.g., 13.375).
* Implement separate integer and fractional algorithms.
* Handle non-terminating fractions with user-defined precision.
* Possibly extend to IEEE 754 floating-point representation.

### Additional Bases

* Add support for Octal (Base-8).

### Graphical User Interface (GUI)

* Wrap the converter logic in Java Swing or JavaFX.

### Unit Testing

* Add JUnit tests covering normal cases, edge cases, invalid inputs, and limits.
