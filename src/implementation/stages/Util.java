/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation.stages;

import baseclasses.InstructionBase;
import implementation.GlobalData;
import utilitytypes.EnumComparison;

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
    public static boolean verifyIfShouldStall(GlobalData globals, boolean reg_accesses[], InstructionBase ins){
        boolean result = false;
            
        if(ins.getOper0().isRegister()) {
            result = result || (globals.register_invalid[ins.getOper0().getRegisterNumber()] && !reg_accesses[ins.getOper0().getRegisterNumber()]);
        }
        if(ins.getSrc1().isRegister()) {
            result = result || (globals.register_invalid[ins.getSrc1().getRegisterNumber()] && !reg_accesses[ins.getSrc1().getRegisterNumber()]);
        }
        if(ins.getSrc2().isRegister()) {
            result = result || (globals.register_invalid[ins.getSrc2().getRegisterNumber()] && !reg_accesses[ins.getSrc2().getRegisterNumber()]);
        }
        switch(ins.getOpcode()){
            case LOAD:
                //result = result || (globals.register_invalid[globals.register_file[ins.getSrc1().getRegisterNumber()]] && !ins.reg_accesses[globals.register_file[ins.getSrc1().getRegisterNumber()]]);
                break;

            case STORE:
                result = result || (globals.register_invalid[ins.getOper0().getRegisterNumber()] && !reg_accesses[ins.getOper0().getRegisterNumber()]);
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
    public static void updateAccessInfo(InstructionBase ins, boolean reg_accesses[], GlobalData globals, boolean access){
        switch (ins.getOpcode()) {
            case MOVC:
            case ADD:
            case CMP:
            case LOAD:
                globals.register_invalid[ins.getOper0().getRegisterNumber()] = access;
                reg_accesses[ins.getOper0().getRegisterNumber()] = access;
                break;

            case STORE:
//                int index = ins.getSrc1().getValue();
//                int offset = (ins.getSrc2().isNull() ? 0 : ins.getSrc2().getValue());
//                globals.register_invalid[index + offset] = access;
//                ins.reg_accesses[index + offset] = access;
                break;
        }
    }
    
    public static void releaseAllRegisters(InstructionBase ins, boolean reg_accesses[], GlobalData globals){
        for(int i=0; i<reg_accesses.length; i++){
            reg_accesses[i] = false;
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
    
    public static int enumCompFromObj(EnumComparison enumCompObj){
        int enumComp = 17;
        
        switch(enumCompObj){
            case EQ:
                enumComp = 1;
                break;
            case NE:
                enumComp = 2;
                break;
            case GT:
                enumComp = 3;
                break;
            case GE:
                enumComp = 4;
                break;
            case LT:
                enumComp = 5;
                break;
            case LE:
                enumComp = 6;
                break;
            case HI:
                enumComp = 7;
                break;
            case LO:
                enumComp = 8;
                break;
            case HS:
                enumComp = 9;
                break;
            case LS:
                enumComp = 10;
                break;
            case CC:
                enumComp = 11;
                break;
            case CS:
                enumComp = 12;
                break;
            case PL:
                enumComp = 13;
                break;
            case MI:
                enumComp = 14;
                break;
            case VC:
                enumComp = 15;
                break;
            case VS:
                enumComp = 16;
                break;
            case NULL:
                enumComp = 17;
                break;
        }
        
        return enumComp;
    }
    
    public static EnumComparison enumCompFromInt(int enumComp){
        EnumComparison enumCompObj = EnumComparison.NULL;
        
        switch(enumComp){
            case 1:
                enumCompObj = EnumComparison.EQ;
                break;
            case 2:
                enumCompObj = EnumComparison.NE;
                break;
            case 3:
                enumCompObj = EnumComparison.GT;
                break;
            case 4:
                enumCompObj = EnumComparison.GE;
                break;
            case 5:
                enumCompObj = EnumComparison.LT;
                break;
            case 6:
                enumCompObj = EnumComparison.LE;
                break;
            case 7:
                enumCompObj = EnumComparison.HI;
                break;
            case 8:
                enumCompObj = EnumComparison.LO;
                break;
            case 9:
                enumCompObj = EnumComparison.HS;
                break;
            case 10:
                enumCompObj = EnumComparison.LS;
                break;
            case 11:
                enumCompObj = EnumComparison.CC;
                break;
            case 12:
                enumCompObj = EnumComparison.CS;
                break;
            case 13:
                enumCompObj = EnumComparison.PL;
                break;
            case 14:
                enumCompObj = EnumComparison.MI;
                break;
            case 15:
                enumCompObj = EnumComparison.VC;
                break;
            case 16:
                enumCompObj = EnumComparison.VS;
                break;
            case 17:
                enumCompObj = EnumComparison.NULL;
                break;
        }
        
        
        return enumCompObj;
    }
}
