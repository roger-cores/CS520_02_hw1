/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cao.hw1.voidtypes;

import com.cao.hw1.utilitytypes.Operand;

/**
 *
 * @author millerti
 */
public class VoidOperand extends Operand {
    static final private VoidOperand SINGLETON = new VoidOperand();
    
    static public VoidOperand getVoidOperand() {
        return SINGLETON;
    }

    @Override
    public boolean isRegister() { return false; }    
    @Override
    public int getRegisterNumber() { return -1; }
    @Override
    public int getValue() { return 0; }
    @Override
    public void setValue(int v) { }
    @Override
    public boolean hasValue() { return false; }
    
    @Override
    public Operand duplicate() {
        return this;
    }
    
    @Override
    public boolean isNull() { return true; }
    
    private VoidOperand() { }
}
