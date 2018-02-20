/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitytypes;

/**
 * Enum for all opcodes to be implemented by the CPU simulator.
 * 
 * @author millerti
 */
public enum EnumOpcode {
    /* 
    Opcode meanings are as follows:
    
    ADD - add two registers
    SUB - subtract
    AND - bitwise AND
    OR  - bitwise OR
    SHL - logical shift left (inserts zeros on the right)
    ASR - arithmetic shift right (replicates sign bit)
    LSR - logical shift right (inserts zeros on the left)
    XOR - bitwise exclusive OR
    CMP - subtract two registers, put comparison flags into destination reg
    ROL - rotate left
    ROR - rotate right
    MULS - signed multiply
    MULU - unsigned multiply
    DIVS - signed divide
    DIVU - unsigned divide
    BRA - branch with condition
    JMP - unconditionally branch
    CALL - Branches to target address, puts return address in register
    LOAD - load from memory
    STORE - store to memory
    MOVC - load register from literal in instruction
    OUT - print contents of src1 to display
    HALT - stop simulation
    NOP - no operation
    INVALID - ???
    NULL - represents pipeline bubble
    */
    
    
    ADD, SUB, AND, OR, SHL, ASR, LSR, XOR, CMP, ROL, ROR,
    MULS, MULU, DIVS, DIVU,
    BRA, JMP, CALL, 
    LOAD, STORE, MOVC, OUT,
    HALT, NOP, INVALID, NULL;
    
    public static EnumOpcode fromString(String name) {
        name = name.trim().toUpperCase();
        EnumOpcode op = null;
        try {
            op = EnumOpcode.valueOf(name);
        } catch (Exception e) {
            op = null;
        }
        return op;
    }
}