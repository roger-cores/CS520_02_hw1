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
import implementation.latches.MemoryToWriteBack;
import utilitytypes.EnumOpcode;
import voidtypes.VoidLatch;

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
            GlobalData globals = (GlobalData)core.getGlobalResources();
            InstructionBase ins = input.getInstruction();
            if (ins.isNull()) return;

            // Write back result to register file
//            System.out.println("writing back\t" +  + ins.getLineNum() + " : " + ins.getInstructionString());
            Util.writeBack(ins, globals);
            Util.updateAccessInfo(ins, input.reg_accesses, globals, false);
            if (input.getInstruction().getOpcode() == EnumOpcode.HALT) {
                // Stop the simulation
                System.exit(0);
            }
        }
    }
