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
import com.cao.hw1.implementation.latches.MemoryToWriteBack;
import com.cao.hw1.utilitytypes.EnumOpcode;
import com.cao.hw1.voidtypes.VoidLatch;

/**
 *
 * @author roger
 */
public class WriteBack extends PipelineStageBase<MemoryToWriteBack,VoidLatch> {
        public WriteBack(CpuCore core, PipelineRegister input, PipelineRegister output) {
            super(core, input, output);
        }

        @Override
        public void compute(MemoryToWriteBack input, VoidLatch output) {
            InstructionBase ins = input.getInstruction();
            if (ins.isNull()) return;

            // Write back result to register file
//            System.out.println("writing back\t" +  + ins.getLineNum() + " : " + ins.getInstructionString());
            Util.writeBack(ins, globals);
            Util.updateAccessInfo(ins, globals, false);
            if (input.getInstruction().getOpcode() == EnumOpcode.HALT) {
                // Stop the simulation
                System.exit(0);
            }
        }
    }
