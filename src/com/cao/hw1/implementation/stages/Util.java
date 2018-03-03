/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cao.hw1.implementation.stages;

import com.cao.hw1.baseclasses.InstructionBase;
import com.cao.hw1.implementation.GlobalData;

/**
 *
 * @author roger
 */
public class Util {
    
    /**
     * Checks if current instruction uses any of the dirty registers or memory locations
     * 
     * @param globals
     * @return true if at least one dirty register is found in the current instruction
     */
    public static boolean verifyIfShouldStall(GlobalData globals, InstructionBase ins){
        boolean result = false;
            
        if(ins.getOper0().isRegister()) {
            result = result || (globals.register_invalid[ins.getOper0().getRegisterNumber()] && !ins.reg_accesses[ins.getOper0().getRegisterNumber()]);
        }
        if(ins.getSrc1().isRegister()) {
            result = result || (globals.register_invalid[ins.getSrc1().getRegisterNumber()] && !ins.reg_accesses[ins.getSrc1().getRegisterNumber()]);
        }
        if(ins.getSrc2().isRegister()) {
            result = result || (globals.register_invalid[ins.getSrc2().getRegisterNumber()] && !ins.reg_accesses[ins.getSrc2().getRegisterNumber()]);
        }
        switch(ins.getOpcode()){
            case LOAD:
                //result = result || (globals.register_invalid[globals.register_file[ins.getSrc1().getRegisterNumber()]] && !ins.reg_accesses[globals.register_file[ins.getSrc1().getRegisterNumber()]]);
                break;

            case STORE:
                result = result || (globals.register_invalid[ins.getOper0().getRegisterNumber()] && !ins.reg_accesses[ins.getOper0().getRegisterNumber()]);
                break;
        }
            
        return result;
    }
    
    /**
     * 
     * @param ins the instruction that wants to acquire or release a register access
     * @param globals the global data
     * @param access true to set access. false to release access to a register
     */
    public static void updateAccessInfo(InstructionBase ins, GlobalData globals, boolean access){
        switch (ins.getOpcode()) {
            case MOVC:
            case ADD:
            case CMP:
            case LOAD:
                globals.register_invalid[ins.getOper0().getRegisterNumber()] = access;
                ins.reg_accesses[ins.getOper0().getRegisterNumber()] = access;
                break;

            case STORE:
//                int index = ins.getSrc1().getValue();
//                int offset = (ins.getSrc2().isNull() ? 0 : ins.getSrc2().getValue());
//                globals.register_invalid[index + offset] = access;
//                ins.reg_accesses[index + offset] = access;
                break;
        }
    }
    
    public static void releaseAllRegisters(InstructionBase ins, GlobalData globals){
        for(int i=0; i<ins.reg_accesses.length; i++){
            ins.reg_accesses[i] = false;
            globals.register_invalid[i] = false;
        }
    }
    
    public static void writeBack(InstructionBase ins, GlobalData globals){
        switch(ins.getOpcode()){
            case MOVC:
            case ADD:
            case CMP:
            case LOAD:
                if(ins.getOper0().isRegister()){
                    globals.register_file[ins.getOper0().getRegisterNumber()] = ins.getOper0().getValue(); //write back

                }
                break;
            case STORE:
                int value = ins.getOper0().getValue();
                int index = ins.getSrc1().getValue();
                int offset = (ins.getSrc2().isNull()?0:ins.getSrc2().getValue());
                globals.memory[index+offset] = value; //write back

        }
    }
    
    public static void fetchValues(InstructionBase ins, int regfile[]){
        if (ins.getSrc1().isRegister()) {
            ins.getSrc1().setValue(regfile[ins.getSrc1().getRegisterNumber()]);
        }
        if (ins.getSrc2().isRegister()) {
            ins.getSrc2().setValue(regfile[ins.getSrc2().getRegisterNumber()]);
        }
        if (ins.getOper0().isRegister()) {
            ins.getOper0().setValue(regfile[ins.getOper0().getRegisterNumber()]);
        }
    }
}
