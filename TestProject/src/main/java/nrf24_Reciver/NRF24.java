package nrf24_Reciver;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinEdge;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import logger.Log;

public class NRF24 implements NRF24_ReciverInterface{

	private GpioController gpio;

	//pins
	private GpioPinDigitalInput clkPin;
	private GpioPinDigitalInput csPin;
	private GpioPinDigitalInput dataPin;

	//Data
	private SensorData data = null;
	private boolean readData = false;
	private final int maxBitNum = 40;
	private boolean[] dataStream = new boolean[maxBitNum];
	private int dataCursor = 0;

	//debug
	private int clkCounter = 0;

	//TemperatureSensore
	private static float refferenceVoltage = 3.0f;

	public void init(){
		Log.printInfoln("Initialized NRF24 - Interface", true);	
		gpio = GpioFactory.getInstance();
		clkPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_DOWN);
		clkPinInit();
		csPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
		csPin.setShutdownOptions(true);
		dataPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_DOWN);	
		dataPin.setShutdownOptions(true);
	}

	private void clkPinInit(){
		clkPin.setShutdownOptions(true);

		// create and register gpio pin listener
		clkPin.addListener(new GpioPinListenerDigital() {
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				if(readData){
					if(event.getEdge() == PinEdge.RISING){
						clkCounter++;
						if(dataCursor < maxBitNum){
							dataStream[maxBitNum-dataCursor-1] = (dataPin.getState() == PinState.HIGH);
						}
						dataCursor++;
						if(dataCursor > maxBitNum){
							readData = false;
							Log.printErrorln("SWSPI_Controller Data Packet to long!");
						}

					}
				}
			}
		});
	}

	private void waitForData(){
		readData = true;
		dataCursor = 0;
		clkCounter = 0;
		Log.println("recived Data!:");
		while(csPin.isLow() && readData){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		}

		if(clkCounter == 40){
			data = interpretByteCode();
		}else{
			data = null;
			Log.println("recived false message != 40 bit!");
		}		
		dataCursor = 0;
		readData = false;
	}

	private SensorData interpretByteCode(){
		if(checkData()){
			int addres = 0;
			for(int i = 7; i >= 4; i--){
				if(dataStream[i]){
					addres+=Math.pow(2, 7-i);
				}
			}
			float temperature = 0;
			for(int i = 23; i >= 14; i--){
				if(dataStream[i]){
					temperature+=Math.pow(2, 23-i);
				}
			}


			float voltage = 0;
			for(int i = 39; i >= 30; i--){
				if(dataStream[i]){
					voltage+=Math.pow(2, 39-i);
				}
			}

			temperature = (calculValue(temperature)-0.111f)*1.056f;
			voltage = (calculValue(voltage)-0.111f)*1.056f;
			temperature = (temperature-0.5f)*100;
			Log.println("Addres: " + addres + " temp: " + temperature + " volt: " + voltage);
			if(addres > 15){
				Log.printErrorln("Data: addres is to big! -> " + addres);
				return null;
			}

			return new SensorData((byte) addres, temperature, voltage);
		}else{
			Log.println("Bit Failure!");
			return null;
		}		
	}

	private boolean checkData(){
		//checkAddres (first Byte: Bit5-8=addres, Bit1-4 = not addres) 
		for(int i = 7; i >= 4; i--){
			if(dataStream[i] == dataStream[i-4]){
				return false;
			}
		}

		//checkTemperature 2nd and 3rd Byte, first 6Bit = check last 10Bits = data
		for(int i = 0; i < 5; i++){
			if(dataStream[23-(i*2)] ^ dataStream[23-(i*2)-1] != dataStream[13-i]){
				return false;
			}
		}

		//check Temperature checkSum
		if(dataStream[8] != dataStream[9] ^ dataStream[10] ^ dataStream[11] ^ dataStream[12] ^ dataStream[13]){
			return false;
		}

		//checkVoltage 4nd and 5rd Byte, first 6Bit = check last 10Bits = data
		for(int i = 0; i < 5; i++){
			if(dataStream[39-(i*2)] ^ dataStream[39-(i*2)-1] != dataStream[29-i]){
				return false;
			}
		}

		//check Temperature checkSum
		if(dataStream[24] != dataStream[25] ^ dataStream[26] ^ dataStream[27] ^ dataStream[28] ^ dataStream[29]){
			return false;
		}

		return true;
	}

	private float calculValue(float temperatureIn){
		return temperatureIn/1024*refferenceVoltage;
	}

	//----------------------------------NRF24Interface----------------------------
	public SensorData getData() {
		if(csPin.isLow()){
			waitForData();
		}
		if(data != null){
			SensorData dataSend = data;
			data = null;
			return dataSend;
		}
		return null;
	}
	
	public static void setReferenceVoltage(float voltage){
		refferenceVoltage = voltage;
	}
}
