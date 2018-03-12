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
import implementation.latches.FetchToDecode;
import utilitytypes.EnumOpcode;
import voidtypes.VoidLatch;

/**
 *
 * @author roger
 */
public class Fetch extends PipelineStageBase<VoidLatch, FetchToDecode> {

    public Fetch(CpuCore core, PipelineRegister input, PipelineRegister output) {
        super(core, input, output);

    }

    @Override
    public String getStatus() {
        // Generate a string that helps you debug.
        return "";
    }

    @Override
    public boolean stageWaitingOnResource() {
        GlobalData globals = (GlobalData)core.getGlobalResources();
        return false;
    }

    @Override
    public void compute(VoidLatch input, FetchToDecode output) {
        // Fetch the instruction
        GlobalData globals = (GlobalData)core.getGlobalResources();
        if(globals.nPC != -1){
            globals.program_counter = globals.nPC;
            globals.nPC = -1;
        }
        if(globals.jmp_waiting){
            return;
        }

        InstructionBase ins = globals.program.getInstructionAt(globals.program_counter);
        
//        if(ins.getOpcode() == EnumOpcode.JMP){
//            globals.jmp_waiting = true;
//        }
        
        if (ins.isNull()) {
            return;
        }
        
        

        // Do something idempotent to compute the next program counter.
        // Don't forget branches, which MUST be resolved in the Decode
        // stage.  You will make use of global resources to commmunicate
        // between stages.
        // Your code goes here...
        //System.out.println("fetching\t" + ins.getLineNum() + " : " + ins.getInstructionString());
        
        output.setInstruction(ins);
    }

    /**
     * This function is to advance state to the next clock cycle and can be
     * applied to any data that must be updated but which is not stored in a
     * pipeline register.
     */
    @Override
    public void advanceClock() {
        // Hint:  You will need to implement this help with waiting
        // for branch resolution and updating the program counter.
        // Don't forget to check for stall conditions, such as when
        // nextStageCanAcceptWork() returns false.
        GlobalData globals = (GlobalData)core.getGlobalResources();
        InstructionBase ins = globals.program.getInstructionAt(globals.program_counter);
        if (nextStageCanAcceptWork()) {
            if (ins.getOpcode() == EnumOpcode.JMP) {
//                globals.program_counter = ins.getLabelTarget().getAddress();
                globals.jmp_waiting = true;
                globals.program_counter = -1;
            } else {
                globals.program_counter++;
            }
        }

    }
}
