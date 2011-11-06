/**
 * @course Operating Systems
 * @instructor Prof. Mohamed Zahran
 * @project #1 Linker
 * @author Yaxing Chen
 * @id N16929794
 * 
 * @desc
 * class: FirstPass
 * the first phase of linker
 * 
 * @param
 * ArrayList<String> modules input
 * 
 * @return
 * FirstPassResult fpr
 * 
 * @see
 * FirstPassResult.java
 */
package phase;

import java.util.*;

import result.FirstPassResult;

import linker.Error;

public class FirstPass extends Phase{
	
	public FirstPass(ArrayList<String> modules, int MEM_SIZE) {
		this.modules = modules;
		this.MEM_SIZE = MEM_SIZE;
	}
	
	@Override
	public FirstPassResult run() {
		FirstPassResult result = new FirstPassResult();
		
		if(modules.size() % 3 != 0 || modules.size() == 0) {
			Error.error(Error.WRONG_FORMAT, "wrong program line number.");
			return null;
		}
		
		for(int i = 0; i < modules.size(); i += 3) {
			if(i >= 3 && result.moduleInitAddress.size() > 0) {
				parseInitAddress(result, modules.get(i - 1));
			}
			else {
				result.moduleInitAddress.add(0);
			}
			parseSymbolTable(result, modules.get(i), Tool.get1stInt(modules.get(i + 2)), i / 3 + 1);
		}
		return result;
	}
	
	/**
	 * @desc
	 * parse out symbol table
	 * 
	 * @param FirstPassResult result fpr object to hold fpr result
	 * @param String defList current module's define list
	 * @param int moduleSize current module size
	 * @param int moduleNo current module #
	 */
	protected void parseSymbolTable(FirstPassResult result, String defList, int moduleSize, int moduleNo) {
		int initAddr = result.moduleInitAddress.get(result.moduleInitAddress.size() - 1);
		try{
			String[] buffer = defList.split(" ");
			buffer = Tool.cleanStringArray(buffer);
			int qty = Integer.parseInt(buffer[0]);
			int j = 1;
			for(int i = 0; i < qty; i ++) {
				if(result.definition.containsKey(buffer[j])) {
					Error.error(Error.DUPLICATE_DEFINE, buffer[j] + " is multiply defined in module " + moduleNo);
				}
				String def = buffer[j++];
				int addr = Integer.parseInt(buffer[j++]);
				if(addr > moduleSize) {
					Error.error(Error.DEF_EXCEED_BOND, "given address: " + Integer.toString(addr) 
								+ ", module size: " + moduleSize);
				}
				addr += initAddr;
				
				result.definition.put(def, addr);
				result.definition_out.add(def + "=" + addr);
				result.defModuleNo.put(def, moduleNo);
			}
		} catch(Exception e) {
			//e.printStackTrace();
			Error.error(Error.WRONG_FORMAT, "");
		}
	}
	
	/**
	 * @desc
	 * parse out modules' initial address
	 * 
	 * @param FirstPassResult result
	 * @param String code code of current module
	 */
	protected void parseInitAddress(FirstPassResult result, String code) {
		try{
			int qty = result.moduleInitAddress.size();
			result.moduleInitAddress.add(result.moduleInitAddress.get(qty - 1) 
					+ Integer.parseInt(code.substring(0, code.indexOf(" "))));
		} catch(Exception e) {
			Error.error(Error.WRONG_FORMAT, "");
		}
	}
}
