/**
 * @course Operating Systems
 * @instructor Prof. Mohamed Zahran
 * @project #1 Linker
 * @author Yaxing Chen
 * @id N16929794
 * 
 * @desc
 * class: SecondPassResult
 * the result of the second pass
 */
package result;

import java.util.*;
public class SecondPassResult extends PassResult{
	//record all used symbols in all modules
	// to detect warning: a symbol is defined but not used, 
	public HashMap<String, Boolean> usedList; 
	
	//memory map
	public ArrayList<String> memMap;
	
	public SecondPassResult() {
		this.usedList = new HashMap<String, Boolean>();
		this.memMap = new ArrayList<String>();
	}
}
