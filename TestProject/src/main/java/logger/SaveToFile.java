package logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveToFile {
	
	private static BufferedWriter bw;
	private static ObjectOutputStream outputStream;
	private static ObjectInputStream in;
	
	public static boolean saveToTextFile(String filePath, String data){
		try {
			bw = new BufferedWriter(new FileWriter(new File(filePath)));
			bw.write(data);
			bw.close( );       
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String getDataFromTextFile(String filePath){
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("file.txt"));
			 StringBuilder sb = new StringBuilder();
			    String line = br.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			    br.close();
			    return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Attention: object must implement serizable!
	 * @param filePath
	 * @param obj
	 * @return
	 */
	public static boolean saveToBinaryFile(String filePath, Object obj){
		try {
	        outputStream = new ObjectOutputStream(new FileOutputStream(filePath));
	        outputStream.writeObject(obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static Object getDataFromBinaryFile(String filePath){
		try {
			in = new ObjectInputStream(new FileInputStream(filePath));
			return in.readObject();
		} catch (Exception e) {
			return null;
		}		
	}
}
