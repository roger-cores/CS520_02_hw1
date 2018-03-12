/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

import baseclasses.CpuCore;
import baseclasses.PipelineRegister;
import implementation.latches.*;
import implementation.stages.*;
import tools.InstructionSequence;
import voidtypes.VoidRegister;

/**
 * This is an example of a class that builds a specific CPU simulator out of
 * pipeline stages and pipeline registers.
 * 
 * @author 
 */
public class MyCpuCore extends CpuCore<GlobalData> {
    PipelineRegister FetchToDecode;
    PipelineRegister DecodeToExecute;
    PipelineRegister ExecuteToMemory;
    PipelineRegister MemoryToWriteback;
    
    Fetch       Fetch;
    Decode      Decode;
    Execute     Execute;
    Memory      Memory;
    WriteBack   Writeback;
    
    private void setup() throws Exception {
        // Instantiate pipeline registers
        FetchToDecode     = new PipelineRegister(FetchToDecode.class);
        DecodeToExecute   = new PipelineRegister(DecodeToExecute.class);
        ExecuteToMemory   = new PipelineRegister(ExecuteToMemory.class);
        MemoryToWriteback = new PipelineRegister(MemoryToWriteBack.class);
        
        // Add the registers to the list or registers that get automatically clocked
        registers.add(FetchToDecode);
        registers.add(DecodeToExecute);
        registers.add(ExecuteToMemory);
        registers.add(MemoryToWriteback);
        
        // Instantiate pipeline stages
        Fetch       = new Fetch(this, VoidRegister.getVoidRegister(), FetchToDecode);
        Decode      = new Decode(this, FetchToDecode, DecodeToExecute);
        Execute     = new Execute(this, DecodeToExecute, ExecuteToMemory);
        Memory      = new Memory(this, ExecuteToMemory, MemoryToWriteback);
        Writeback   = new WriteBack(this, MemoryToWriteback, VoidRegister.getVoidRegister());

        // Add pipeline stages to list of pipeline stages that are automatically told to compute
        stages.add(Fetch);
        stages.add(Decode);
        stages.add(Execute);
        stages.add(Memory);
        stages.add(Writeback);
        
        globals = new GlobalData();
    }
    
    public MyCpuCore() throws Exception {
        setup();
    }
    
    public void loadProgram(InstructionSequence program) {
        globals.program = program;
    }
    
    public void runProgram() {
        // Call advanceClock() in a loop until an error occurs or the HALT
        // instruction is executed.
        
        while(true){
            advanceClock();
        }
    }
}
