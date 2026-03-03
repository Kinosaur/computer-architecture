import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CpuSimulator {

    // ------------------------------------------------------------------------
    // Architectural State
    // ------------------------------------------------------------------------
    private int[] registers = new int[8];
    private int totalCycles = 0;
    private int instructionCount = 0;

    // Chronological execution log
    private List<String> executionLog = new ArrayList<>();

    // Instruction Opcodes (6-bit)
    private static final int OP_MOV = 1;
    private static final int OP_ADD = 2;
    private static final int OP_SUB = 3;
    private static final int OP_MUL = 4;
    private static final int OP_DIV = 5;
    private static final int OP_END = 63;

    // Bit-shifting constants
    private static final int OPCODE_SHIFT = 26;
    private static final int REG_SHIFT = 23;
    private static final int FLAG_SHIFT = 22;
    private static final int IMMEDIATE_MASK = 0x3FFFFF;

    // ------------------------------------------------------------------------
    // Startup Header
    // ------------------------------------------------------------------------
    public void printStartupHeader() {
        System.out.println("====================================================");
        System.out.println("Details of the Instruction Set Architecture (ISA)");
        System.out.println("====================================================");
        System.out.println("This is a 32-bit ISA and supports 32-bit arithmetic.");
        System.out.println("There are eight 32-bit General Purpose Registers (r0 to r7).");
        System.out.println("Register r7 stores upper-half multiplication results and division remainders.");
        System.out.println("Hence r7 cannot be used as destination in MUL or DIV.");
        System.out.println("----------------------------------------------------");
        System.out.println("Syntax: <opcode> <dest_reg> <source_reg/immediate>");
        System.out.println("Example: mov r1 3");
        System.out.println("Type 'end 0 0' to terminate.");
        System.out.println("====================================================\n");
    }

    // ------------------------------------------------------------------------
    // Assembler
    // ------------------------------------------------------------------------
    public int assemble(String instruction) {

        String[] parts = instruction.trim().toLowerCase().split("\\s+");

        String mnemonic = parts[0];
        if (mnemonic.equals("end")) {
            return (OP_END << OPCODE_SHIFT);
        }

        int opcode = switch (mnemonic) {
            case "mov" -> OP_MOV;
            case "add" -> OP_ADD;
            case "sub" -> OP_SUB;
            case "mul" -> OP_MUL;
            case "div" -> OP_DIV;
            default -> throw new IllegalArgumentException("Unknown mnemonic");
        };

        int destReg = Integer.parseInt(parts[1].substring(1));

        if ((opcode == OP_MUL || opcode == OP_DIV) && destReg == 7) {
            throw new IllegalArgumentException("r7 cannot be destination for MUL/DIV.");
        }

        int flag;
        int operandField;

        String op2 = parts[2];
        if (op2.startsWith("r")) {
            flag = 0;
            operandField = Integer.parseInt(op2.substring(1)) & 0x7;
        } else {
            flag = 1;
            int imm = Integer.parseInt(op2);
            operandField = imm & IMMEDIATE_MASK;
        }

        return ((opcode & 0x3F) << OPCODE_SHIFT)
                | ((destReg & 0x7) << REG_SHIFT)
                | ((flag & 0x1) << FLAG_SHIFT)
                | (operandField & IMMEDIATE_MASK);
    }

    // ------------------------------------------------------------------------
    // Execution Engine
    // ------------------------------------------------------------------------
    private int getCycleCost(int opcode) {
        return switch (opcode) {
            case OP_MOV -> 1;
            case OP_ADD -> 1;
            case OP_SUB -> 1;
            case OP_MUL -> 4;
            case OP_DIV -> 8;
            default -> 0;
        };
    }

    public int execute(int instruction) {

        int opcode = (instruction >>> OPCODE_SHIFT) & 0x3F;
        if (opcode == OP_END) return -1;

        int destReg = (instruction >>> REG_SHIFT) & 0x7;
        int flag = (instruction >>> FLAG_SHIFT) & 0x1;
        int operandField = instruction & IMMEDIATE_MASK;

        int value2 = (flag == 1)
                ? (operandField << 10) >> 10
                : registers[operandField & 0x7];

        switch (opcode) {

            case OP_MOV:
                registers[destReg] = value2;
                executionLog.add(formatReg(destReg));
                break;

            case OP_ADD:
                registers[destReg] += value2;
                executionLog.add(formatReg(destReg));
                break;

            case OP_SUB:
                registers[destReg] -= value2;
                executionLog.add(formatReg(destReg));
                break;

            case OP_MUL:
                long product = (long) registers[destReg] * (long) value2;
                registers[7] = (int) (product >> 32);
                registers[destReg] = (int) product;

                executionLog.add(
                        "r7:r" + destReg + " = " + product +
                                " [" + to64BitBinary(product) + "]"
                );
                executionLog.add(formatReg(7));
                executionLog.add(formatReg(destReg));
                break;

            case OP_DIV:
                if (value2 != 0) {
                    int quotient = registers[destReg] / value2;
                    int remainder = registers[destReg] % value2;
                    registers[destReg] = quotient;
                    registers[7] = remainder;

                    executionLog.add(
                            String.format(
                                    "r%d = %d [%s]   r7 = %d [%s]",
                                    destReg,
                                    registers[destReg],
                                    to32BitBinary(registers[destReg]),
                                    registers[7],
                                    to32BitBinary(registers[7])
                            )
                    );
                } else {
                    executionLog.add("Hardware Exception: Division by zero.");
                }
                break;
        }

        int cycles = getCycleCost(opcode);
        totalCycles += cycles;
        instructionCount++;
        return cycles;
    }

    // ------------------------------------------------------------------------
    // Formatting Helpers
    // ------------------------------------------------------------------------
    private String formatReg(int r) {
        return String.format("r%d = %d [%s]",
                r, registers[r], to32BitBinary(registers[r]));
    }

    private String to32BitBinary(int value) {
        return String.format("%32s", Integer.toBinaryString(value)).replace(' ', '0');
    }

    private String to64BitBinary(long value) {
        return String.format("%64s", Long.toBinaryString(value)).replace(' ', '0');
    }

    public String formatInstructionBinary(int instruction) {
        String bin = to32BitBinary(instruction);
        return bin.substring(0, 6) + " "
                + bin.substring(6, 9) + " "
                + bin.substring(9, 10) + " "
                + bin.substring(10);
    }

    private String formatDecoded(String decoded) {
        String[] parts = decoded.split("\\s+");
        if (parts.length == 3) {
            return parts[0] + " " + parts[1] + ", " + parts[2];
        }
        return decoded;
    }

    public void printExecutionLog() {
        System.out.println("\nAfter the program execution contents of the registers are.....\n");
        for (String line : executionLog) {
            System.out.println(line);
        }
        System.out.println();
    }

    public void printCPI() {
        double cpi = (double) totalCycles / instructionCount;
        System.out.println("CPI of the program........");
        System.out.printf("CPI = %.2f\n", cpi);
    }

    // ------------------------------------------------------------------------
    // Main
    // ------------------------------------------------------------------------
    public static void main(String[] args) {

        CpuSimulator cpu = new CpuSimulator();
        cpu.printStartupHeader();

        Scanner scanner = new Scanner(System.in);
        List<String> programList = new ArrayList<>();
        List<Integer> memoryList = new ArrayList<>();

        System.out.println("Enter instructions line by line:");
        int lineCount = 1;

        while (true) {
            System.out.print(lineCount + " ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            int encoded = cpu.assemble(input);
            programList.add(input);
            memoryList.add(encoded);
            lineCount++;

            if (input.toLowerCase().startsWith("end")) break;
        }

        scanner.close();

        System.out.println("\nProgram Assembly Complete. Commencing Execution...\n");
        System.out.println("PC      Decoded:                 Encoded instructions (32-bit):            Clock cycles");
        System.out.println("----------------------------------------------------------------------------------------");

        int pc = 0;
        while (pc < memoryList.size()) {
            int instruction = memoryList.get(pc);
            String decoded = programList.get(pc);

            int cycles = cpu.execute(instruction);
            if (cycles == -1) break;

            System.out.printf(
                    "PC[%d] -> %-20s :  %-40s %d\n",
                    pc,
                    cpu.formatDecoded(decoded),
                    cpu.formatInstructionBinary(instruction),
                    cycles
            );

            pc++;
        }

        cpu.printExecutionLog();
        cpu.printCPI();
    }
}