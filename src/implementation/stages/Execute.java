/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation.stages;

import baseclasses.CpuCore;
import baseclasses.InstructionBase;
import baseclasses.PipelineRegister;
import baseclasses.PipelineStageBase;
import implementation.GlobalData;
import implementation.MyALU;
import implementation.latches.DecodeToExecute;
import implementation.latches.ExecuteToMemory;

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
            GlobalData globals = (GlobalData)core.getGlobalResources();
            InstructionBase ins = input.getInstruction();
            
//            if(globals.flush){
//                Util.releaseAllRegisters(ins, input.reg_accesses, globals);
//                ins = VoidInstruction.getVoidInstruction();
//                output.setInstruction(ins);
//                globals.flush = false;
//                return;
//            }
            
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
//                case BRA:
//                    int v = Util.enumCompFromObj(ins.getComparison());
//                    result = MyALU.execute(ins.getOpcode(), Util.enumCompFromObj(ins.getComparison()), source2, oper0);
//                    globals.fetch_waiting = false;
//                    if(result==0){ //no-branch
//                        //no-action
//                    } else {//take branch
//                        //flush previous
//                        globals.flush = true;
//                        globals.nPC = ins.getLabelTarget().getAddress();
//                        
//                    }
//                    break;
                case OUT:
                    
                    System.out.println("@@output: " + "\t" + ins.getOper0().getValue());
                    
                    break;
            }
            
                        
            // Fill output with what passes to Memory stage...
            output.setInstruction(ins);
            output.reg_accesses = input.reg_accesses;
            // Set other data that's passed to the next stage.
        }
    }
