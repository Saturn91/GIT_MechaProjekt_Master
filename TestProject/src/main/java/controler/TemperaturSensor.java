package controler;

public class TemperaturSensor {
	private Graph temperatur;
	private Graph voltage;
	private int address;
	
	public TemperaturSensor(int address, String name) {
		this.temperatur  = new Graph(name + ": Voltage");
		this.voltage = new Graph(name + ": Temperatur");
		this.address = address;
	}
	
	public void addData(float voltage, float temperatur){
		this.temperatur.addPoint(new Point(temperatur, System.currentTimeMillis()));
		this.voltage.addPoint(new Point(voltage, System.currentTimeMillis()));
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
	
	
}
