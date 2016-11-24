package logger;

import com.EnergyHarvesting.Master.TestProject.App;

public class Log {
	public static final String info =  "[ LOG ]";
	public static final String error = "[ERROR]";
	
	public static boolean debug = false;
	public static boolean writeLogFile = false;
	public static StringBuilder logFile = new StringBuilder();
	public static String lastLine;
	
	//Info
	public static void printInfoln(String infoline){
		println(info + " " + infoline);
	}
	
	public static void printInfoln(String infoline, boolean bool){
		println(info + " " + infoline, bool);
	}
	
	//Error
	public static void printErrorln(String errorline){
		println(error + " " + errorline);
	}	
	
	//---------------normal---------------
	public static void println(String log){
		String logger = App.getDate() + " " + log;
		if(debug){
			System.out.println(logger);
		}	
		if(writeLogFile){
			logFile.append(logger+"\n");
		}
	}
	
	public static void println(String log, boolean bool){
		String logger = App.getDate() + " " + log;
		System.out.println(logger);
		lastLine = logger;
		if(writeLogFile){
			logFile.append(logger+"\n");
		}
	}
	
	public static void print(String log){
		String logger = log;
		if(debug){
			System.out.print(logger);
		}
		if(writeLogFile){
			logFile.append(logger);
		}
	}
	
	public static void print(String log, boolean withDate){
		String logger = App.getDate() + " " + log;
		if(debug){
			System.out.print(logger);
		}
		if(writeLogFile){
			logFile.append(logger);
		}
	}
	
	public static void saveLogFile(){
		if(writeLogFile){
			println("Save LogFile is not implemented yet!");
		}else{
			System.err.println("Not able to Save LogFile: writeLogFile = false");
		}
	}
}
