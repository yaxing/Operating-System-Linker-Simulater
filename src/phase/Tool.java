/**
 * @course Operating Systems
 * @instructor Prof. Mohamed Zahran
 * @project #1 Linker
 * @author Yaxing Chen
 * @id N16929794
 * 
 * @desc
 * class: Tool
 * aggregate some tool functions
 */
package phase;


import java.util.*;
public class Tool {
	
	/*
	 * delete redundant or illegal null elements
	 */
	public static ArrayList<String> cleanStringArray(ArrayList<String> list) {
		int i = 0;
		for(String s : list) {
			if(s == null || s.length() == 0) {
				list.remove(i++);
			}
		}
		return list;
	}
	
	public static String[] cleanStringArray(String[] list) {
		ArrayList<String> clear = new ArrayList<String>();
		for(int i = 0; i < list.length; i ++) {
			String s = list[i];
			if(s != null && s.length() > 0) {
				clear.add(s);
			}
		}
		return clear.toArray(new String[clear.size()]);
	}
	
	/**
	 * parse out the first number of a string
	 * @param string
	 * @return
	 */
	public static int get1stInt(String string) {
		int splitFlag = string.indexOf(" ");
		return Integer.parseInt(string.substring(0, splitFlag <= 0 ? 1 : splitFlag));
	}
}
