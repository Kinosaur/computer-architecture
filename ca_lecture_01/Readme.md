# Java Number Converter (Positive Integers)

## 1. Overview
This is a console-based Java application for converting positive integers between Decimal, Binary, and Hexadecimal number systems. It provides a simple menu-driven interface for users to perform conversions and view results instantly.

The project follows a modular, object-oriented design where each conversion type is handled by a dedicated class. All core algorithms (like repeated division and positional weighting) are implemented manually without relying on Java's built-in conversion methods like `Integer.toBinaryString()`.

**Key Constraint:** This simulator supports positive integers only.

## 2. Features
- Decimal ↔ Binary
- Decimal ↔ Hexadecimal
- Binary ↔ Hexadecimal (Direct conversion using 4-bit nibbles)
- Hexadecimal ↔ Decimal
- Hexadecimal ↔ Binary (New feature!)
- **64-bit Support:** Uses `long` to handle large positive integers (up to 9,223,372,036,854,775,807).

## 3. Project Structure
```
└── ca_lecture_01/
    ├── NumberConverter.java            # Main entry point (Menu & Input Loop)
    ├── DecimalToBinaryConverter.java   # Logic: Decimal -> Binary
    ├── BinaryToDecimalConverter.java   # Logic: Binary -> Decimal
    ├── DecimalToHexConverter.java      # Logic: Decimal -> Hex
    ├── HexToDecimalConverter.java      # Logic: Hex -> Decimal
    ├── BinaryToHexConverter.java       # Logic: Binary -> Hex
    └── HexToBinaryConverter.java       # Logic: Hex -> Binary
```

## 4. How to Run

### Requirements
Java JDK (Version 14+ recommended for enhanced switch syntax).

### Steps

**Compile:**
```bash
javac ca_lecture_01/*.java
```

**Run:**
```bash
java ca_lecture_01.NumberConverter
```

## 5. Implementation Details

- **Manual Algorithms:** Instead of libraries, manual logic is used:
  - **Decimal to Base-N:** Repeated division by N (2 or 16).
  - **Base-N to Decimal:** Positional notation (sum of digit × N^power).
  - **Binary ↔ Hex:** Grouping bits into 4-bit "nibbles" and mapping them directly.
- **Data Types:** `long` is used throughout to prevent overflow issues common with `int` when handling 32-bit unsigned values (like FFFFFFFF).
- **Simplicity:** Validation is straightforward (checks for null, empty, or negative input) without over-engineering.

## 6. Future Improvements

- **Negative Numbers:** Implement Two's Complement logic to support negative inputs.
- **Floating Point:** Add support for fractional numbers (e.g., 12.5).
- **GUI:** Create a simple graphical interface using JavaFX or Swing.
