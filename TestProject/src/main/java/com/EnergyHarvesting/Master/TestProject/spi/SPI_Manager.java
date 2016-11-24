package com.EnergyHarvesting.Master.TestProject.spi;

import com.pi4j.wiringpi.Spi;

public class SPI_Manager {
	
	// SPI operations
    public static final byte WRITE_CMD = 0x40;
    public static final byte READ_CMD  = 0x41;
    private static final int packetLength = 5;
	
	public static boolean write(byte[] data){
		try {
			byte[] packet = new byte[data.length + 1];
			packet[0] = WRITE_CMD;
			
			for(int i = 0; i < data.length; i++){
				packet[i+1] = data[i];
			}
			
			Spi.wiringPiSPIDataRW(0, packet, packetLength);	//Chanel, data, packet.length?
		} catch (Exception e) {
			System.err.println("SPI_Manager: failed to write data");
			return false;
		}
		
		return true;
	}
	
	public static byte[] read(){
		byte[] packet = new byte[packetLength];
		Spi.wiringPiSPIDataRW(0, packet, packetLength);
		return packet;
	}
	
	public static long lastTime = 0;
	public static long nowTime;
	public static final long simuMaxTimeDelta = 5000;
	public static long nextDelta = 0;
	/**
	 * this method simulates a SPI-connection which delivers Data to the Pins of the RaspberryPy
	 * @return
	 */
	public static byte[] simuHW_Read(){
		nowTime = System.currentTimeMillis();
		byte[] packet = new byte[packetLength];
		
		if(nowTime-lastTime > nextDelta){
			lastTime = nowTime;
			nextDelta = (long) (Math.random()*simuMaxTimeDelta);
			packet[0] = 1;
			for(int i = 1; i < packet.length; i++){
				packet[i] = (byte) (Math.random()*15);
			}
		}
		return packet;
	}
}
