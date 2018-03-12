/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation.latches;

import baseclasses.LatchBase;

/**
 *
 * @author roger
 */
public class ExecuteToMemory extends LatchBase{
    public boolean[] reg_accesses = new boolean[32];
}
