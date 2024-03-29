package controler;

import java.io.Serializable;
import java.util.ArrayList;

import nrf24_Reciver.SensorData;
import logger.Log;

import com.EnergyHarvesting.Master.TestProject.gui.subPanel.SettingPanel;
import com.saturn91.saveToFile.SaveToFile;

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
		Log.printInfoln("Initialize Controller", true);
		sensors = new TemperaturSensor[16];		//not more than 16 Sensor possibly with a byte address
	}
	
	public void handleData(SensorData data){
		if(data != null){
			administrateSensors(data.getId());
			sensors[data.getId()].addData(data.getVoltage(), data.getTemperatur());
		}		
	}
	
	public void update(){
		if(SettingPanel.updateSensorNames()){
			sensorNames = SettingPanel.getSensorNames();
			if(sensorNames != null){
				for(int i = 0; i < 16; i++){
					if(sensors[i] != null){
						if(sensorNames[i] != null){
							sensors[i].setName(sensorNames[i]);
						}else{
							sensors[i].setName("Sensor"+i);
						}
					}
				}			
				Log.printInfoln("renamed SensorNames!", true);
				SettingPanel.resetUpdateSensorNames();
			}			
		}
	}
	
	public void save(String fileName){
		if(SaveToFile.saveToBinaryFile(fileName, this)){
			Log.printInfoln("saved Data to " + fileName, true); 	//print saveData
		}else{
			Log.printErrorln("not able to load: " + fileName); 	//print saveData
		}
	}
	
	private void administrateSensors(int id){
		if(sensors[id] == null){
			if(sensorNames == null){
				sensors[id] = new TemperaturSensor(id, "Sensor"+id);
				Log.printInfoln("found new Sensor Address=" + id, true);
			}else{
				if(sensorNames[id] != null){
					sensors[id] = new TemperaturSensor(id, sensorNames[id]);
					Log.printInfoln("found new Sensor " + sensorNames[id], true);
				}else{
					sensors[id] = new TemperaturSensor(id, "Sensor"+id);
					Log.printInfoln("found new Sensor Address=" + id, true);
				}				
			}			
		}
	}
	
	public void setSensorNames(String[] names){
		if(names.length == 16){
			sensorNames = names;
		}else{
			Log.printErrorln("Name List must be 16 entries long!");
		}
	}
	
	public TemperaturSensor[] getSensors(){
		return sensors;
	}
}
