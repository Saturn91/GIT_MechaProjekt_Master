package logger;

import com.EnergyHarvesting.Master.TestProject.App;
import com.EnergyHarvesting.Master.TestProject.gui.GUI;
import com.saturn91.saveToFile.SaveToFile;

public class Log {
	public static final String logLine ="[LOG]  ";
	public static final String info =  "[INFO] ";
	public static final String error = "[ERROR]";
	
	public static boolean debug = false;
	public static boolean writeLogFile = false;
	public static StringBuilder logFile = new StringBuilder();
	
	//Info
	public static void printInfoln(String infoline){
		String line = info + " " + infoline;
		if(debug){
			printInCMD(appendDate(line)+"\n", 1);			
			String logger = appendDate(line);
			System.out.println(logger);
		}			
	}
	
	public static void printInfoln(String infoline, boolean bool){
		String line = info + " " + infoline;	
		printInCMD(appendDate(line)+"\n", 1);
		String logger = appendDate(line);
		System.out.println(logger);
		if(writeLogFile){
			logFile.append(logger+"\n");
		}
	}
	
	//Error
	public static void printErrorln(String errorline){
		String line = error + " " + errorline;
		printInCMD(appendDate(line)+"\n", -1);
		String logger = appendDate(line);
		System.err.println(logger);
		if(writeLogFile){
			logFile.append(logger+"\n");
		}
	}	
	
	//---------------normal---------------
	public static void println(String log){
		String line = appendDate(logLine + " " + log);
		if(debug){
			System.out.println(line);
			printInCMD(line+"\n", 0);
		}	
	}
	
	public static void println(String log, boolean bool){
		String logger = appendDate(logLine + " " + log);
		System.out.println(logger);
		printInCMD(logger + "\n", 0);
		if(writeLogFile){
			logFile.append(logger+"\n");
		}
	}
	
	public static void print(String log){
		String logger = log;
		if(debug){
			System.out.print(logger);
			printInCMD(logger, 0);
		}
		if(writeLogFile){
			logFile.append(logger);
		}
	}
	
	public static void print(String log, boolean withDate){
		String logger = appendDate(log);
		if(debug){
			System.out.print(logger);
			printInCMD(logger, 0);
		}
		if(writeLogFile){
			logFile.append(logger);
		}
	}
	
	public static void saveLogFile(){
		if(writeLogFile){
			printInfoln("Saved Logfile", true);
			SaveToFile.saveToTextFile("LogFile.log", logFile.toString());
		}else{
			if(debug){
				printErrorln("writeLogFile = false");
			}			
		}
	}
	
	private static String appendDate(String input){
		return App.getDate() + " " + input;
	}
	
	private static void printInCMD(String input, int type){
		if(App.isWithGui()){
			GUI.appendLogLine(input, type);
		}		
	}
}
