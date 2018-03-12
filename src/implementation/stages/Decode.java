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
import implementation.latches.DecodeToExecute;
import implementation.latches.FetchToDecode;
import utilitytypes.EnumComparison;
import utilitytypes.EnumOpcode;
import voidtypes.VoidInstruction;

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
        GlobalData globals = (GlobalData)core.getGlobalResources();
        return globals.data_waiting;
    }

    @Override
    public void compute(FetchToDecode input, DecodeToExecute output) {
        GlobalData globals = (GlobalData)core.getGlobalResources();
        InstructionBase ins = input.getInstruction();
        boolean reg_accesses[] = new boolean[32];

        // These null instruction checks are mostly just to speed up
        // the simulation.  The Void types were created so that null
        // checks can be almost completely avoided.
        
        if(globals.flush){
            ins = VoidInstruction.getVoidInstruction();
            output.setInstruction(ins);
            globals.flush = false;
            return;
        }
        
        if (ins.isNull()) {
            return;
        }
        
        
        
        

        globals.data_waiting = Util.verifyIfShouldStall(globals, reg_accesses, ins);
        
        //System.out.println("decoding\t" + ins.getLineNum() + " : " + ins.getInstructionString());
        if (globals.data_waiting) {
            return;
        }
        
        int[] regfile = globals.register_file;

        // Do what the decode stage does:
        // - Look up source operands
        Util.fetchValues(ins, regfile);
        
        
        if (ins.getOpcode() == EnumOpcode.BRA) {
            int result = 0;
            EnumComparison actual = Util.enumCompFromInt(ins.getOper0().getValue());
            EnumComparison given = ins.getComparison();
            switch(actual){
                case EQ:
                    if(given == EnumComparison.EQ || given == EnumComparison.LE || given == EnumComparison.GE){
                        result = 1;
                    }
                    break;
                case LT:
                    if(given == EnumComparison.LT || given == EnumComparison.LE) 
                        result = 1;
                    break;
                case GT:
                    if(given == EnumComparison.GT || given == EnumComparison.GE) 
                        result = 1;
                    break;
            }
            if(result==0){ //no-branch
                        //no-action
            } else {//take branch
                //flush previous
                globals.flush = true;
                globals.nPC = ins.getLabelTarget().getAddress();

            }
        } 
        else if(ins.getOpcode() == EnumOpcode.JMP){
            globals.nPC = ins.getLabelTarget().getAddress();
            globals.jmp_waiting = false;
        }

//        if(!globals.fetch_waiting)
        try{
            Util.updateAccessInfo(ins, reg_accesses, globals, true);
        } catch (ArrayIndexOutOfBoundsException e){
            //System.out.println(e.getMessage());
        }
        
        // - Decode instruction
        //   - Opcode already resides in the Instruction
        // - Resolve branches  
        output.setInstruction(ins);
        output.reg_accesses = reg_accesses;
        // Set other data that's passed to the next stage.
    }

    

    
    
}
