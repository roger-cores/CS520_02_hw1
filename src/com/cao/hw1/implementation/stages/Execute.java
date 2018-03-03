/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cao.hw1.implementation.stages;

import com.cao.hw1.baseclasses.CpuCore;
import com.cao.hw1.baseclasses.InstructionBase;
import com.cao.hw1.baseclasses.PipelineRegister;
import com.cao.hw1.baseclasses.PipelineStageBase;
import com.cao.hw1.implementation.MyALU;
import com.cao.hw1.implementation.latches.DecodeToExecute;
import com.cao.hw1.implementation.latches.ExecuteToMemory;
import com.cao.hw1.voidtypes.VoidInstruction;

/**
 *
 * @author roger
 */
public class Execute extends PipelineStageBase<DecodeToExecute,ExecuteToMemory> {
        public Execute(CpuCore core, PipelineRegister input, PipelineRegister output) {
            super(core, input, output);
        }

        @Override
        public void compute(DecodeToExecute input, ExecuteToMemory output) {
            InstructionBase ins = input.getInstruction();
            
            if(globals.flush){
                Util.releaseAllRegisters(ins, globals);
                ins = VoidInstruction.getVoidInstruction();
                output.setInstruction(ins);
                globals.flush = false;
                return;
            }
            
            if (ins.isNull()) return;

            int source1 = ins.getSrc1().getValue();
            int source2 = ins.getSrc2().getValue();
            int oper0 =   ins.getOper0().getValue();
//            System.out.println("executing\t" +  + ins.getLineNum() + " : " + ins.getInstructionString());
            
            switch(ins.getOpcode()){
                case MOVC:
                    ins.getOper0().setValue(source1);
                    break;
                case ADD:
                case CMP:
                    int result = MyALU.execute(ins.getOpcode(), source1, source2, oper0);
                    ins.getOper0().setValue(result);
                    break;
                case BRA:
                    result = MyALU.execute(ins.getOpcode(), ins.getComparison().getValue(), source2, oper0);
                    globals.fetch_waiting = false;
                    if(result==0){ //no-branch
                        //no-action
                    } else {//take branch
                        //flush previous
                        globals.flush = true;
                        globals.nPC = ins.getLabelTarget().getAddress();
                        
                    }
                    break;
                case OUT:
                    
                    System.out.println("r[" + ins.getOper0().getRegisterNumber() + "]\t:\t" + ins.getOper0().getValue());
                    
                    break;
            }
            
                        
            // Fill output with what passes to Memory stage...
            output.setInstruction(ins);
            // Set other data that's passed to the next stage.
        }
    }
