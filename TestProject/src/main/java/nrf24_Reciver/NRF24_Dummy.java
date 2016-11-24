package nrf24_Reciver;

import logger.Log;

public class NRF24_Dummy implements NRF24_ReciverInterface{

	public static int bytes = 4;
	
	public void init() {
		Log.println("Initialized NRF24");		
	}

	public byte[] getData() {
		return byteSendSimulation();
	}
	
	private long lastTime= 0;
	private long nowTime;
	private int maxDelta = 10000;
	private int delta = 0;
	
	private byte[] byteSendSimulation(){
		byte[] data = new byte[bytes];
		nowTime = System.currentTimeMillis();
		if(nowTime - lastTime > delta){
			Log.printInfoln("recived Data!");
			lastTime = nowTime;
			delta = (int) (Math.random()*maxDelta);
			for(int i = 0; i < data.length; i++){
				data[i] = (byte) (Math.random()*15);
			}
		}
		
		return data;
	}
}
