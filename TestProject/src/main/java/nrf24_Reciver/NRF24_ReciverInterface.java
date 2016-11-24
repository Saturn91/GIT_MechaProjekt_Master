package nrf24_Reciver;

public interface NRF24_ReciverInterface {
	/**
	 * Intitalize NRF24Modul when RaspberryPI is started
	 */
	public void init();
	
	/**
	 * Check if the connected NRF24 has recieved Data
	 * @return null: no Data recieved, else Data recieved
	 */
	public byte[] getData();
}
