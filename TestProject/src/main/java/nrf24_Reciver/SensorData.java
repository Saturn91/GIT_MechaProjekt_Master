package nrf24_Reciver;

public class SensorData {
	private byte id;
	private float temperatur;
	private float voltage;
	
	public SensorData(byte id, float temperatur, float voltage) {
		this.id = id;
		this.temperatur = temperatur;
		this.voltage = voltage;
	}

	public byte getId() {
		return id;
	}

	public float getTemperatur() {
		return temperatur;
	}

	public float getVoltage() {
		return voltage;
	}	
}
