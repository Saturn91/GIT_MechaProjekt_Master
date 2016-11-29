package nrf24_Reciver;

import com.pi4j.wiringpi.Spi;

/**
 * 
 * @author M.Geissbberger
 * Read and write methode are implemented from Example: 
 * --> https://github.com/Pi4J/pi4j/blob/master/pi4j-example/src/main/java/WiringPiSPIExample.java
 */
public class NRF24 implements NRF24_ReciverInterface{
	
	// SPI operations
    public static final byte WRITE_CMD = 0x40;
    public static final byte READ_CMD  = 0x41;
    private static final int packetLength = 4;

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public SensorData getData() {
		// TODO Auto-generated method stub
		return null;
	}
	
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
