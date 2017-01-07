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
		Log.printInfoln("Initialized NRF24", true);		
	}

	public SensorData getData() {
		SensorData data = null;
		byte[] input = byteSendSimulation();
		if(input != null){
			float temp = (float) ((Math.random()*60)-10);
			float volt = (float) (Math.random()*10);
			data = new SensorData(input[0], temp, volt);
		}
		return data;
	}
	
	private long lastTime= 0;
	private long nowTime;
	private int maxDelta = 10000;
	private int minDelta = 5000;
	private int delta = 0;
	
	private byte[] byteSendSimulation(){
		byte[] data = null;
		nowTime = System.currentTimeMillis();
		if(nowTime - lastTime > delta){
			data = new byte[bytes];
			Log.println("recived Data!");
			lastTime = nowTime;
			delta = (int) (Math.random()*(maxDelta-minDelta))+minDelta;
			
			data[0] = 1;	//Sensor Address
			
			for(int i = 1; i < data.length; i++){
				data[i] = (byte) (Math.random()*16);
			}
		}
		
		return data;
	}
}
