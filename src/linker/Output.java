/**
 * @course Operating Systems
 * @instructor Prof. Mohamed Zahran
 * @project #1 Linker
 * @author Yaxing Chen
 * @id N16929794
 * 
 * @desc
 * class: Output
 * write buffer control
 */
package linker;

import java.io.*;
import result.*;

public class Output {
	public static String filePath;
	
	public static void completeOutput(FirstPassResult fpr, SecondPassResult spr) {
		StringBuilder writeBuffer = new StringBuilder();
		
		if(fpr != null) {
			writeBuffer.append("Symbol Table\n");
			for(int i = 0; i < fpr.definition_out.size(); i ++) {
				writeBuffer.append(fpr.definition_out.get(i) + "\n");
			}
		}
		
		if(spr != null) {
			writeBuffer.append("\nMemory Map\n");
			for(int i = 0; i < spr.memMap.size(); i ++) {
				writeBuffer.append(i + ": " + spr.memMap.get(i) + "\n");
			}
		}
		
		Output.write(writeBuffer);
	}
	
	public static void write(StringBuilder content) {
		String out = content.toString();
		try {
			File file = new File(filePath);
			if(!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(out);
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
