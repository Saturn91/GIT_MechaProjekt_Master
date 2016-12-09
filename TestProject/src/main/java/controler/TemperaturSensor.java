package controler;

import java.io.Serializable;
import java.util.ArrayList;

public class TemperaturSensor implements Serializable{
	private String name;
	private Graph temperatur;
	private Graph voltage;
	private int address;
	private boolean newData = false;
	
	private ArrayList<Long> times = new ArrayList<Long>();
	
	public TemperaturSensor(int address, String name) {
		this.name = name;
		this.temperatur  = new Graph(name + ": Voltage");
		this.voltage = new Graph(name + ": Temperatur");
		this.address = address;
	}
	
	public void addData(float voltage, float temperatur){
		this.temperatur.addPoint(new Point(temperatur, System.currentTimeMillis()));
		this.voltage.addPoint(new Point(voltage, System.currentTimeMillis()));
		this.times.add(System.currentTimeMillis());
		this.newData = true;
	}
	
	
	public Graph getTemperatur(){
		return temperatur;
	}
	
	public Graph getVoltage(){
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
	
	
}
