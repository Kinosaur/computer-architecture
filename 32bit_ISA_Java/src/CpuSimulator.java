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
            default -> throw new IllegalArgumentException("Unknown mnemonic: " + mnemonic);
        };

        if (parts.length < 2 || !parts[1].startsWith("r")) {
            throw new IllegalArgumentException("Missing or malformed destination register.");
        }

        int destReg = Integer.parseInt(parts[1].substring(1));

        if ((opcode == OP_MUL || opcode == OP_DIV) && destReg == 7) {
            throw new IllegalArgumentException("r7 cannot be destination for MUL/DIV.");
        }

        if (parts.length < 3) {
            throw new IllegalArgumentException("Missing second operand.");
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
            case OP_MOV, OP_ADD, OP_SUB -> 1;
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
                        String.format("r7: r%d = %d [%s]", destReg, product, to32BitBinary((int)product))
                );
                break;

            case OP_DIV:
                if (value2 != 0) {
                    int quotient = registers[destReg] / value2;
                    int remainder = registers[destReg] % value2;
                    registers[destReg] = quotient;
                    registers[7] = remainder;

                    executionLog.add(
                            String.format("r%d = %d [%s] r7: %d [%s]",
                                    destReg, registers[destReg], to32BitBinary(registers[destReg]),
                                    registers[7], to32BitBinary(registers[7])
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
    // Part II: Pipelined Execution & Hazard Detection
    // ------------------------------------------------------------------------
    public void simulatePipeline(List<Integer> memoryList, List<String> programList, Scanner scanner) {
        System.out.println("\nPipelined Execution of the Program");
        System.out.println("====================================================\n");
        System.out.println("It is assumed that the CPU has 4-pipeline stages: IF (Instruction Fetch) ID (Instruction decoding), \nEX (Execution) and WB (Write back). And each stage completes within one clock cycle and RAW hazard (any) \nwill be solved with FORWARDING (from the o/p register of ALU to the i/p of \nnext instruction's ALU stage) without causing any stall.\n");

        int n = 0;
        for (int instr : memoryList) {
            if (((instr >>> OPCODE_SHIFT) & 0x3F) == OP_END) break;
            n++;
        }

        if (n == 0) return;

        System.out.print("               ");
        for (int i = 1; i <= n + 3; i++) {
            System.out.printf("%-5d", i);
        }
        System.out.println();

        for (int i = 0; i < n; i++) {
            String decoded = formatDecoded(programList.get(i));
            String prefix = String.format("%d %s :", i, decoded);
            System.out.printf("%-15s", prefix);

            for (int space = 0; space < i; space++) {
                System.out.print("     ");
            }
            System.out.println("IF | ID | EX | WB");
        }

        int pipelineCycles = n + 4 - 1;
        System.out.println("\nPipelined execution took " + pipelineCycles + " clock cycles for the program execution.\n");

        System.out.println("RAW hazard details:\n");
        boolean hazardFound = false;

        for (int i = 1; i < n; i++) {
            int currentInstr = memoryList.get(i);
            int currentOpcode = (currentInstr >>> OPCODE_SHIFT) & 0x3F;
            int currentDest = (currentInstr >>> REG_SHIFT) & 0x7;
            int currentFlag = (currentInstr >>> FLAG_SHIFT) & 0x1;
            int currentSrc = currentInstr & 0x7;

            int prevInstr = memoryList.get(i - 1);
            int prevOpcode = (prevInstr >>> OPCODE_SHIFT) & 0x3F;
            int prevDest = (prevInstr >>> REG_SHIFT) & 0x7;

            List<Integer> registersWritten = new ArrayList<>();
            registersWritten.add(prevDest);
            if (prevOpcode == OP_MUL || prevOpcode == OP_DIV) {
                registersWritten.add(7);
            }

            List<Integer> registersRead = new ArrayList<>();
            if (currentOpcode != OP_MOV) {
                registersRead.add(currentDest);
            }
            if (currentFlag == 0) {
                registersRead.add(currentSrc);
            }

            for (int readReg : registersRead) {
                if (registersWritten.contains(readReg)) {
                    System.out.printf("r%d in instructions %d and %d caused RAW hazard and is solved by forwarding.\n",
                            readReg, i, i + 1);
                    hazardFound = true;
                }
            }
        }

        if (!hazardFound) {
            System.out.println("No RAW hazards detected.");
        }

        System.out.print("\nPress 'y' to continue or 'n' to stop the simulation : ");
        String response = scanner.nextLine().trim();
        if (response.equalsIgnoreCase("n")) {
            System.exit(0);
        }
    }

    // ------------------------------------------------------------------------
    // Formatting Helpers
    // ------------------------------------------------------------------------
    private String formatReg(int r) {
        return String.format("r%d = %d [%s]", r, registers[r], to32BitBinary(registers[r]));
    }

    private String to32BitBinary(int value) {
        return String.format("%32s", Integer.toBinaryString(value)).replace(' ', '0');
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
        System.out.println("\nValues of registers after the execution of the instruction set:");
        System.out.println("During the execution of the above code sequence, the values of registers would be varied in the following way:");
        for (String line : executionLog) {
            System.out.println(line);
        }
        System.out.println();
    }

    public void printCPI() {
        if (instructionCount > 0) {
            double cpi = (double) totalCycles / instructionCount;
            System.out.println("CPI of the program........");
            System.out.printf("CPI = %.2f\n", cpi);
        } else {
            System.out.println("CPI = 0.00");
        }
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

            try {
                int encoded = cpu.assemble(input);
                programList.add(input);
                memoryList.add(encoded);
                lineCount++;

                if (input.toLowerCase().startsWith("end")) break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again.");
            }
        }

        System.out.println("\nProgram Assembly Complete. Commencing Execution...\n");
        System.out.printf("%-10s %-18s %-36s %s\n", "PC", "Decoded:", "Encoded instructions (32-bit):", "Clock cycles");
        System.out.println("--------------------------------------------------------------------------------");

        int pc = 0;
        while (pc < memoryList.size()) {
            int instruction = memoryList.get(pc);
            String decoded = programList.get(pc);

            int cycles = cpu.execute(instruction);
            if (cycles == -1) break;

            System.out.printf(
                    "PC[%d] ->   %-15s :  %-36s %d\n",
                    pc,
                    cpu.formatDecoded(decoded),
                    cpu.formatInstructionBinary(instruction),
                    cycles
            );

            pc++;
        }

        cpu.printExecutionLog();
        cpu.printCPI();

        cpu.simulatePipeline(memoryList, programList, scanner);
        scanner.close();
    }
}