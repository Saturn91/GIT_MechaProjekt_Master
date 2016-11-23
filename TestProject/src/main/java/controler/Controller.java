package controler;

import java.util.ArrayList;

/**
 * Controller calculates Averagetemperature, saves Data into a file, adds time to Data,
 * prepare the graphs for GUI
 * 
 * - getGraphs() provides the GUI with all the graphs specified in Constructor
 * - handleData(byte[]) adds Data to the graphs
 * - calculate() starts all the needed Calculations for the App
 * @author M.Geissbberger
 *
 */
public class Controller {
	
	private ArrayList<Graph> graphs = new ArrayList<Graph>();	//holds all Graphs with data
	
	public Controller() {
		//initialize Graphs
		graphs.add(new Graph("Temperature1"));		//Temperature of Sensor1
		graphs.add(new Graph("Temperature2"));		//Temperature of Sensor2
		graphs.add(new Graph("Temperature3"));		//Temperature of Sensor3
		graphs.add(new Graph("Voltage1"));			//Voltage of Sensor1
		graphs.add(new Graph("Voltage2"));			//Voltage of Sensor2
		graphs.add(new Graph("Voltage3"));			//Voltage of Sensor3
	}
	
	public void handleData(byte[] data){
		if(data[0] != 0){
			System.out.println("Controler: Handle Data: not implemented yet!");
			//TODO add points to right graphs depending from address in Data
		}		
	}
	
	public void calculate(){
		System.out.println("Controler: Calcul: not implemented yet!");
	}
	
	public ArrayList<Graph> getGraphs(){
		return graphs;
	}
	
	public void save(String fileName){
		//TODO save Graphs
	}
	
	
}
