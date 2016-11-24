package controler;

public class TemperaturSensor {
	private Graph temperatur;
	private Graph voltage;
	private byte adress;
	
	public TemperaturSensor(byte adress, Graph temperatur, Graph voltage) {
		this.temperatur = temperatur;
		this.voltage = voltage;
		this.adress = adress;
	}
	
	public void addData(){
		
	}
	
	
}
