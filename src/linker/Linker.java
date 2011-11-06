/**
 * @course Operating Systems
 * @instructor Prof. Mohamed Zahran
 * @project #1 Linker
 * @author Yaxing Chen
 * @id N16929794
 * 
 * @desc
 * class: Linker
 * main class, fetch input from designated or default file, write output to default or designated file
 * 
 * 2 phases of this linker:
 * First Pass: generate symbol table and initial address table of each module
 * Second Pass: take result of Fist Pass as parameters, generate memory map
 */

package linker;

import java.util.*;
import java.io.*;

import phase.*;
import result.*;

public class Linker {
	
	protected static final int MEM_SIZE = 600;
	protected static String DEFAULT_INPUT_FILE = "input";
	protected static String DEFAULT_OUTPUT_FILE = "output";
	
	protected ArrayList<String> fetchModules(String path) {
		ArrayList<String> modules = new ArrayList<String>();
		String tmp = "";
		try {
			File input = new File(path);
			if(!input.exists()) {
				Error.error(Error.FILE_NOT_EXIST, path);
			}
			
			BufferedReader reader = new BufferedReader(new FileReader(input));
			while((tmp = reader.readLine()) != null) {
				if(tmp.isEmpty()) {
					continue;
				}
				tmp = tmp.trim();
				modules.add(tmp);
			}
			reader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return modules;
	}
	
	protected void flushResultFile(String path) {
		File output = new File(path);
		if(output.exists()) {
			output.delete();
		}
	}
	
	public static void main(String[] args) {
		String inputPath;
		FirstPass fp = null;
		SecondPass sp = null;
		FirstPassResult fpr = null;
		SecondPassResult spr = null;
		
		if(args == null || args.length == 0) {
			inputPath = DEFAULT_INPUT_FILE;
			Output.filePath = DEFAULT_OUTPUT_FILE;
		}
		else {
			inputPath = args[0].isEmpty() ? DEFAULT_INPUT_FILE : args[0];
			Output.filePath = args[1].isEmpty() ? DEFAULT_OUTPUT_FILE : args[1];
		}
		
		Linker linker = new Linker();
		ArrayList<String> modules = linker.fetchModules(inputPath);
		linker.flushResultFile(Output.filePath);
		
		if(modules != null) {
			fp = new FirstPass(modules, MEM_SIZE);
			fpr = fp.run();
			
			sp = new SecondPass(modules, MEM_SIZE, fpr);
			spr = sp.run();
		}
		
		if(!Error.errorOccured) {
			Output.completeOutput(fpr, spr);
		}
	}
}
