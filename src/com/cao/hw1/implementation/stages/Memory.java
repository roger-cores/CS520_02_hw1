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
import com.cao.hw1.implementation.latches.ExecuteToMemory;
import com.cao.hw1.implementation.latches.MemoryToWriteBack;

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
            InstructionBase ins = input.getInstruction();
            if (ins.isNull()) return;

            // Access memory...
            switch(ins.getOpcode()){
                case LOAD:
                    ins.getOper0().setValue(globals.register_file[ins.getSrc1().getValue()]);
                    break;
            }
//            System.out.println("memory\t\t" +  + ins.getLineNum() + " : " + ins.getInstructionString());
            output.setInstruction(ins);
            // Set other data that's passed to the next stage.
        }
    }
