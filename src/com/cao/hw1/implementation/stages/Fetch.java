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
import com.cao.hw1.implementation.latches.FetchToDecode;
import com.cao.hw1.utilitytypes.EnumOpcode;
import com.cao.hw1.voidtypes.VoidLatch;

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
        return false;
    }

    @Override
    public void compute(VoidLatch input, FetchToDecode output) {
        // Fetch the instruction
        
        if(globals.nPC != -1){
            globals.program_counter = globals.nPC;
            globals.nPC = -1;
        }

        InstructionBase ins = globals.program.getInstructionAt(globals.program_counter);
        
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
        InstructionBase ins = globals.program.getInstructionAt(globals.program_counter);
        if (nextStageCanAcceptWork()) {
            
            if (ins.getOpcode() == EnumOpcode.JMP) {
                globals.program_counter = ins.getLabelTarget().getAddress();
            } else {
                globals.program_counter++;
            }
        }

    }
}
