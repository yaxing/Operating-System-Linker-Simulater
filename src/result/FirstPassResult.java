/**
 * @course Operating Systems
 * @instructor Prof. Mohamed Zahran
 * @project #1 Linker
 * @author Yaxing Chen
 * @id N16929794
 * 
 * @desc
 * class: FirstPassResult
 * the result of the first pass
 */
package result;

import java.util.*;
public class FirstPassResult extends PassResult{
	//symbol table, 2 copies
	//1. stored in hash map, for searching efficiency
	//2. stored in arraylist, for output, guarantee the sequence of input
	public HashMap<String, Integer> definition;
	public ArrayList<String> definition_out;
	
	//record the moduleNo in which the symbol was defined
	public HashMap<String, Integer> defModuleNo;
	
	//modules' init addresses
	public ArrayList<Integer> moduleInitAddress;
	
	public FirstPassResult() {
		this.definition = new HashMap<String, Integer>();
		this.definition_out = new ArrayList<String>();
		this.moduleInitAddress = new ArrayList<Integer>();
		this.defModuleNo = new HashMap<String, Integer>();
	}
}
