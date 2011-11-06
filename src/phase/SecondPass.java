/**
 * @course Operating Systems
 * @instructor Prof. Mohamed Zahran
 * @project #1 Linker
 * @author Yaxing Chen
 * @id N16929794
 * 
 * @desc
 * class: SecondPass
 * the second phase of linker
 * 
 * @param
 * FirstPassResult fpr
 * 
 * @return
 * SecondPassResult spr
 * 
 * @see
 * SecondPassResult.java
 */
package phase;

import result.*;
import java.util.*;

import linker.Error;

public class SecondPass extends Phase{
	
	protected FirstPassResult fpr;
	
	public SecondPass(ArrayList<String> modules, int MEM_SIZE, FirstPassResult fpr) {
		this.modules = modules;
		this.MEM_SIZE = MEM_SIZE;
		this.fpr = fpr;
	}
	
	public SecondPassResult run() {
		SecondPassResult spr = new SecondPassResult();
		
		for(int i = 0; i < modules.size(); i += 3) {
			int moduleNo = i / 3 + 1;
			ArrayList<String> useList = new ArrayList<String>();//use list, mark as used or not
			parseUseList(useList, modules.get(i + 1), moduleNo);
			
			boolean[] curModuleUsedTrack = new boolean[useList.size()];
			parseCode(spr, useList, modules.get(i + 2), fpr.moduleInitAddress.get(moduleNo - 1)
					, curModuleUsedTrack, moduleNo);
			moduleWarningCheck(useList, curModuleUsedTrack);
		}
		
		globalWarningCheck(spr);
		
		return spr;
	}
	
	protected void parseUseList(ArrayList<String> useList, String useListDef, int moduleNo) {
		int qty = Tool.get1stInt(useListDef);
		if(qty == 0) {
			return;
		}
		String[] tmp = useListDef.substring(useListDef.indexOf(" ") + 1).split(" ");
		tmp = Tool.cleanStringArray(tmp);
		
		if(qty < tmp.length) {
			Error.error(Error.USE_DEF_EXCEED_BOND, "module no: " + moduleNo);
		}
		
		for(String useDef : tmp) {
			useList.add(useDef);
		}
	}
	
	/**
	 * @desc 
	 * parse code part, relocation, redirection
	 * 
	 * @param SecondPassResult spr
	 * @param ArrayList<String> useList
	 * @param String code
	 * @param int moduleAddr
	 * @param boolean[] curModuleUsedTrack used to track the use situation of
	 * 										 symbols in current module's define list
	 */
	protected void parseCode(SecondPassResult spr, ArrayList<String> useList, 
							String code, int moduleAddr, boolean[] curModuleUsedTrack, int moduleNo) {
		
		boolean errorExist = false; // if error exists, stop building memory map
		
		int moduleSize = Tool.get1stInt(code);
		String[] tmp = code.substring(code.indexOf(" ") + 1).split(" ");
		tmp = Tool.cleanStringArray(tmp);
		
		if(tmp.length % 2 != 0) {
			Error.error(Error.WRONG_FORMAT, code);
			errorExist = true;
		}
		
		if(tmp.length / 2 > moduleSize) {
			Error.error(Error.CODE_DEF_EXCEED_MODULE_BOND, code);
			errorExist = true;
		}
		
		for(int i = 0; i < tmp.length - 1; i += 2) {
			try{
				String op = tmp[i];
				String addr = tmp[i + 1];
				switch(op.charAt(0)) {
				case 'E':
					int symbolN = Integer.parseInt(addr.substring(1));
					
					if(symbolN >= useList.size()) {
						Error.error(Error.EXT_ADDR_TOO_LARGE, addr);
						errorExist = true;
					}
					
					String symbol = useList.get(symbolN);
					
					if(!fpr.definition.containsKey(symbol)) {
						Error.error(Error.SYMBOL_NOT_DEFINED, symbol);
						errorExist = true;
					}
					
					curModuleUsedTrack[symbolN] = true;
					spr.usedList.put(symbol, true);
					
					String symbolAddr = String.format("%03d", fpr.definition.get(symbol));
					addr = addr.charAt(0) + symbolAddr;
					break;
					
				case 'R':
					Integer relAddr = Integer.parseInt(addr.substring(1));
					
					if(relAddr > moduleSize) {
						Error.error(Error.REL_ADDR_EXCEED_MODULE_BOND, "in code: " + addr 
									+ ", relative addr: " + addr.charAt(0) + relAddr.toString());
						errorExist = true;
					}
					
					relAddr += moduleAddr;
					addr = addr.charAt(0) + String.format("%03d", relAddr);
					break;
					
				case 'A':
					if(Integer.parseInt(addr.substring(1)) > MEM_SIZE) {
						Error.error(Error.ADDR_EXCEED_MEM_SIZE, addr);
						errorExist = true;
					}
					break;
					
				case 'I':
					break;
					
				default:
					Error.error(Error.WRONG_OPCODE, op);
					errorExist = true;
					break;
				}
				
				if(!errorExist) {
					spr.memMap.add(addr);
				}
				
			} catch(Exception e) {
				e.printStackTrace();
				Error.error(Error.WRONG_FORMAT, code);
			}
		}
	}
	
	/**
	 * check following warning:
	 * symbol appears in a use list but it not actually used in the module
	 * @param spr
	 * @param useList
	 */
	protected void moduleWarningCheck(ArrayList<String> useList, boolean[] usedTrack) {
		for(int i = 0; i < useList.size(); i ++) {
			if(!usedTrack[i]) {
				Error.error(Error.WAR_USE_LIST_SYMBOL_NOT_USED, useList.get(i));
			}
		}
	}
	
	/**
	 * check following warning:
	 * a symbol is defined but not used
	 * @param spr
	 */
	protected void globalWarningCheck(SecondPassResult spr) {
		ArrayList<String> tmp = fpr.definition_out;
		for(int i = 0; i < tmp.size(); i ++) {
			String cur = tmp.get(i);
			String symbol = cur.substring(0, cur.indexOf("="));
			if(!spr.usedList.containsKey(symbol)) {
				Error.error(Error.WAR_DEFINED_SYMBOL_NOT_USED, "symbol : " + symbol 
						+ " was defined in module: " + fpr.defModuleNo.get(symbol));
			}
		}
	}
}
