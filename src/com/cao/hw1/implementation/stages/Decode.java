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
import com.cao.hw1.implementation.latches.DecodeToExecute;
import com.cao.hw1.implementation.latches.FetchToDecode;
import com.cao.hw1.utilitytypes.EnumOpcode;
import com.cao.hw1.voidtypes.VoidInstruction;

/**
 *
 * @author roger
 */
public class Decode extends PipelineStageBase<FetchToDecode, DecodeToExecute> {

    public Decode(CpuCore core, PipelineRegister input, PipelineRegister output) {
        super(core, input, output);
    }

    @Override
    public boolean stageWaitingOnResource() {
        // Hint:  You will need to implement this to deal with 
        // dependencies.
        return globals.data_waiting;
    }

    @Override
    public void compute(FetchToDecode input, DecodeToExecute output) {
        InstructionBase ins = input.getInstruction();

        // These null instruction checks are mostly just to speed up
        // the simulation.  The Void types were created so that null
        // checks can be almost completely avoided.
        
        if(globals.flush){
            ins = VoidInstruction.getVoidInstruction();
            output.setInstruction(ins);
            return;
        }
        
        if (ins.isNull()) {
            return;
        }
        
        
        
        if (!globals.fetch_waiting && ins.getOpcode() == EnumOpcode.BRA) {
            globals.fetch_waiting = true;
        }

        globals.data_waiting = Util.verifyIfShouldStall(globals, ins);
        
        //System.out.println("decoding\t" + ins.getLineNum() + " : " + ins.getInstructionString());
        if (globals.data_waiting) {
            return;
        }
        int[] regfile = globals.register_file;

        // Do what the decode stage does:
        // - Look up source operands
        Util.fetchValues(ins, regfile);

//        if(!globals.fetch_waiting)
        try{
            Util.updateAccessInfo(ins, globals, true);
        } catch (ArrayIndexOutOfBoundsException e){
            //System.out.println(e.getMessage());
        }
        
        // - Decode instruction
        //   - Opcode already resides in the Instruction
        // - Resolve branches  
        //   - Will be resolved after execution is complete
        output.setInstruction(ins);
        // Set other data that's passed to the next stage.
    }

    

    
    
}
