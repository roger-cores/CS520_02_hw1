/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cao.hw1.voidtypes;

import com.cao.hw1.baseclasses.InstructionBase;
import com.cao.hw1.baseclasses.LatchBase;

/**
 *
 * @author millerti
 */
public class VoidLatch extends LatchBase {
    private static final VoidLatch SINGLETON = new VoidLatch();
    
    public static VoidLatch getVoidLatch() { return SINGLETON; }
    
    public InstructionBase getInstruction() { 
        return VoidInstruction.getVoidInstruction(); 
    }

    public boolean isNull() { return true; }
}
