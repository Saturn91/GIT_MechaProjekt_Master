package controler;

import java.io.Serializable;
import java.util.ArrayList;

public class TemperaturSensor implements Serializable{
	private String name;
	private ArrayList<Float> temperature;
	private ArrayList<Float> voltage;
	private int address;
	private boolean newData = false;
	
	private ArrayList<Long> times = new ArrayList<Long>();
	
	public TemperaturSensor(int address, String name) {
		this.name = name;
		this.temperature  = new ArrayList<Float>();
		this.voltage = new ArrayList<Float>();
		this.address = address;
	}
	
	public void addData(float voltage, float temperatur){
		this.temperature.add(temperatur);
		this.voltage.add(voltage);
		this.times.add(System.currentTimeMillis());
		this.newData = true;
	}
	
	
	public ArrayList<Float> getTemperatur(){
		return temperature;
	}
	
	public ArrayList<Float> getVoltage(){
		return voltage;
	}
	
	public int getAddress(){
		return address;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean hasNewData(){
		return newData;
	}
	
	public void resetNewData(){
		newData = false;
	}
	
	public long getTime(int index){
		return times.get(index);
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	
}
