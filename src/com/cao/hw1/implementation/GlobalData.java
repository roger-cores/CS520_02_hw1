/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cao.hw1.implementation;

import com.cao.hw1.tools.InstructionSequence;
import com.cao.hw1.utilitytypes.IGlobals;

/**
 * As a design choice, some data elements that are accessed by multiple
 * pipeline stages are stored in a common object.
 * 
 * TODO:  Add to this any additional global or shared state that is missing.
 * 
 * @author 
 */
public class GlobalData implements IGlobals {
    public InstructionSequence program;
    public int program_counter = 0;
    public int[] register_file = new int[32];
    public boolean[] register_invalid = new boolean[32];
    

    @Override
    public void reset() {
        program_counter = 0;
        register_file = new int[32];
    }
    
    
    // Other global and shared variables here....
    public boolean fetch_waiting = false;
    public boolean data_waiting = false;
    public boolean flush = false;
    public int nPC = -1;

}
