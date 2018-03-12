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
import implementation.latches.ExecuteToMemory;
import implementation.latches.MemoryToWriteBack;

/**
 *
 * @author roger
 */
public class Memory extends PipelineStageBase<ExecuteToMemory,MemoryToWriteBack> {
        public Memory(CpuCore core, PipelineRegister input, PipelineRegister output) {
            super(core, input, output);
        }

        @Override
        public void compute(ExecuteToMemory input, MemoryToWriteBack output) {
            GlobalData globals = (GlobalData)core.getGlobalResources();
            InstructionBase ins = input.getInstruction();
            if (ins.isNull()) return;

            // Access memory...
            switch(ins.getOpcode()){
                case LOAD:
                    ins.getOper0().setValue(globals.memory[ins.getSrc1().getValue()]);
                    break;
                    
                case STORE:
                    int value = ins.getOper0().getValue();
                    int index = ins.getSrc1().getValue();
                    int offset = (ins.getSrc2().isNull()?0:ins.getSrc2().getValue());
                    globals.memory[index+offset] = value; //write back
            }
//            System.out.println("memory\t\t" +  + ins.getLineNum() + " : " + ins.getInstructionString());
            output.setInstruction(ins);
            output.reg_accesses = input.reg_accesses;
            // Set other data that's passed to the next stage.
        }
    }
