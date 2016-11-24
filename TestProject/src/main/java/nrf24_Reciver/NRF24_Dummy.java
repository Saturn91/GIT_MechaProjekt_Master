package nrf24_Reciver;

import logger.Log;

/**
 * Simulates the signal send by a Sensor (Address = 1);
 * @author M.Geissbberger
 *
 */
public class NRF24_Dummy implements NRF24_ReciverInterface{

	public static int bytes = 5;
	
	public void init() {
		Log.println("Initialized NRF24");		
	}

	public SensorData getData() {
		SensorData data = null;
		byte[] input = byteSendSimulation();
		if(input != null){
			float temp = ((float) ((60/256)-10))*((float) (input[1]*16+input[2]));
			float volt = ((float) ((60/256)-10))*((float) (input[3]*16+input[4]));
			data = new SensorData(input[0], temp, volt);
		}
		return data;
	}
	
	private long lastTime= 0;
	private long nowTime;
	private int maxDelta = 10000;
	private int delta = 0;
	
	private byte[] byteSendSimulation(){
		byte[] data = null;
		nowTime = System.currentTimeMillis();
		if(nowTime - lastTime > delta){
			data = new byte[bytes];
			Log.printInfoln("----recived Data!-----");
			lastTime = nowTime;
			delta = (int) (Math.random()*maxDelta);
			
			data[0] = 1;	//Sensor Address
			
			for(int i = 1; i < data.length; i++){
				data[i] = (byte) (Math.random()*16);
			}
		}
		
		return data;
	}
}
