/**
 * @course Operating Systems
 * @instructor Prof. Mohamed Zahran
 * @project #1 Linker
 * @author Yaxing Chen
 * @id N16929794
 * 
 * @desc
 * class: Error
 * used for error and warning handling
 */
package linker;

public class Error {
	//error, warning no
	public static final int FILE_NOT_EXIST = 0; 
	public static final int WRONG_FORMAT = 1; 
	public static final int DUPLICATE_DEFINE = 2;
	public static final int DEF_EXCEED_BOND = 3;
	public static final int USE_DEF_EXCEED_BOND = 4;
	public static final int CODE_DEF_EXCEED_MODULE_BOND = 5;
	public static final int WRONG_OPCODE = 6;
	public static final int SYMBOL_NOT_DEFINED = 7;
	public static final int REL_ADDR_EXCEED_MODULE_BOND = 8;
	public static final int ADDR_EXCEED_MEM_SIZE = 9;
	public static final int EXT_ADDR_TOO_LARGE = 10; 
	
	public static final int WAR_USE_LIST_SYMBOL_NOT_USED = 11;
	public static final int WAR_DEFINED_SYMBOL_NOT_USED = 12;
	
	public static boolean errorOccured = false; //mark the appearance of errors
	
	protected static StringBuilder writeBuffer = new StringBuilder();
	
	public static StringBuilder getErrorInfo() {
		return writeBuffer;
	}
	
	protected static void errorOutput(String errorInfo) {
		writeBuffer.append(errorInfo + "\n");
		System.out.println(errorInfo);
	}
	
	public static void error(int errorNo, String extraInfo) {
		switch(errorNo) {
		case FILE_NOT_EXIST:
			errorOutput("Error: File: " + extraInfo + " doesn't exist.");
			errorOccured = true;
			break;
		
		case WRONG_FORMAT:
			errorOutput("Error: wrong input format. " + extraInfo);
			errorOccured = true;
			break;
			
		case DUPLICATE_DEFINE:
			errorOutput("Error: symbol: " + extraInfo);
			errorOccured = true;
			break;
			
		case DEF_EXCEED_BOND:
			errorOutput("Error: address appearing in a definition exceeds the size of the module: " 
								+ extraInfo);
			errorOccured = true;
			break;
			
		case USE_DEF_EXCEED_BOND:
			errorOutput("Error: use list exceed defined list length." + extraInfo);
			errorOccured = true;
			break;
			
		case CODE_DEF_EXCEED_MODULE_BOND:
			errorOutput("Error: code define exceed module size: " + extraInfo);
			errorOccured = true;
			break;
			
		case WRONG_OPCODE:
			errorOutput("Error: wrong opcode: " + extraInfo);
			errorOccured = true;
			break;
			
		case SYMBOL_NOT_DEFINED:
			errorOutput("Error: symbol not defined: " + extraInfo);
			errorOccured = true;
			break;
			
		case REL_ADDR_EXCEED_MODULE_BOND:
			errorOutput("Error: relative address exceeds the size of the module: " + extraInfo);
			errorOccured = true;
			break;
			
		case ADDR_EXCEED_MEM_SIZE:
			errorOutput("Error: absolute address exceeds the size of the machine: " + extraInfo);
			errorOccured = true;
			break;
			
		case EXT_ADDR_TOO_LARGE:
			errorOutput("Error: external address is too large to reference an entry in the use list: " + extraInfo);
			errorOccured = true;
			break;
			
		case WAR_USE_LIST_SYMBOL_NOT_USED:
			errorOutput("Warning: symbol appears in a use list but not used in the module: " + extraInfo);
			break;
			
		case WAR_DEFINED_SYMBOL_NOT_USED:
			errorOutput("Warning: symbol is defined but not used: " + extraInfo);
			break;
			
		default:
			break;
		}
	}
}
