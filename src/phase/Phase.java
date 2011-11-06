/**
 * @course Operating Systems
 * @instructor Prof. Mohamed Zahran
 * @project #1 Linker
 * @author Yaxing Chen
 * @id N16929794
 * 
 * @desc
 * class: Phase
 * abstract of phases(1, 2 pass)
 */
package phase;

import java.util.*;

import result.PassResult;


public abstract class Phase {
	protected ArrayList<String> modules;
	protected int MEM_SIZE;
	
	public abstract PassResult run();
}
