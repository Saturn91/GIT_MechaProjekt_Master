package controler;

import java.io.Serializable;

import nrf24_Reciver.SensorData;
import logger.Log;
import logger.SaveToFile;

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
public class Controller implements Serializable{
	
	private TemperaturSensor[] sensors;
	private String[] sensorNames = null;
	
	public Controller() {
		Log.printInfoln("----Initialize Controller!-----");
		sensors = new TemperaturSensor[16];		//not more than 16 Sensor possibly with a byte address
	}
	
	public void handleData(SensorData data){
		if(data != null){
			administrateSensors(data.getId());
			sensors[data.getId()].addData(data.getVoltage(), data.getTemperatur());
		}		
	}
	
	public void save(String fileName){
		Log.printInfoln("saved Data to " + fileName, true); 	//print saveData
		SaveToFile.saveToBinaryFile(fileName, this);
	}
	
	private void administrateSensors(int id){
		if(sensors[id] == null){
			if(sensorNames == null){
				sensors[id] = new TemperaturSensor(id, ""+id);
				Log.printInfoln("found new Sensor " + id, true);
			}else{
				sensors[id] = new TemperaturSensor(id, sensorNames[id]);
				Log.printInfoln("found new Sensor " + sensorNames[id], true);
			}			
		}
	}
	
	public void setSensorNames(String[] names){
		if(names.length == 16){
			sensorNames = names;
		}else{
			Log.printErrorln("---Name List must be 16 entries long!---");
		}
	}
}
