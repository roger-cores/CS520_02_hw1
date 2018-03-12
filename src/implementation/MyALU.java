/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

import implementation.stages.Util;
import utilitytypes.EnumComparison;
import utilitytypes.EnumOpcode;

/**
 * The code that implements the ALU has been separates out into a static
 * method in its own class.  However, this is just a design choice, and you
 * are not required to do this.
 * 
 * @author 
 */
public class MyALU {
    public static int execute(EnumOpcode opcode, int input1, int input2, int oper0) {
        int result = 0;
        
        // Implement code here that performs appropriate computations for
        // any instruction that requires an ALU operation.  See
        // EnumOpcode.
        
        switch(opcode){
            case ADD:
                result = input1 + input2;
                break;
            case CMP:
                if(input1 == input2){
                    result = Util.enumCompFromObj(EnumComparison.EQ);
                } else if(input1 < input2){
                    result = Util.enumCompFromObj(EnumComparison.LT);
                } else if(input1 > input2){
                    result = Util.enumCompFromObj(EnumComparison.GT);
                }
                break;
//            case BRA:
//                EnumComparison actual = Util.enumCompFromInt(oper0);
//                EnumComparison given = Util.enumCompFromInt(input1);
//                
//                switch(actual){
//                    case EQ:
//                        if(given == EnumComparison.EQ || given == EnumComparison.LE || given == EnumComparison.GE){
//                            result = 1;
//                        }
//                        break;
//                    case LT:
//                        if(given == EnumComparison.LT || given == EnumComparison.LE) result = 1;
//                        break;
//                    case GT:
//                        if(given == EnumComparison.GT || given == EnumComparison.GE) result = 1;
//                        break;
//                }
//                break;
        }
        
        return result;
    }    
}
