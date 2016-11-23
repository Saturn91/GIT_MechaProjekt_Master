package com.EnergyHarvesting.Master.TestProject.spi;

import com.pi4j.wiringpi.Spi;

public class SPI_Manager {
	
	// SPI operations
    public static final byte WRITE_CMD = 0x40;
    public static final byte READ_CMD  = 0x41;
    private static final int packetLength = 4;
	
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
}
