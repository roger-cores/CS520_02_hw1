/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cao.hw1.cpusimulator;

import com.cao.hw1.implementation.MyCpuCore;
import com.cao.hw1.tools.InstructionSequence;
import java.io.IOException;

/**
 *
 * @author millerti
 */
public class CpuSimulator {
    
    public static boolean printStagesEveryCycle = false;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, Exception {
        InstructionSequence seq = new InstructionSequence();
        seq.loadFile("samples/sieve.asm");
//        seq.printProgram();
        
        MyCpuCore core = new MyCpuCore();
        core.loadProgram(seq);
        core.runProgram();
    }    
}
