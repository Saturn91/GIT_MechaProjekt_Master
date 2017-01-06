package nrf24_Reciver;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinEdge;
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
	
	//TemperatureSensore
	private final static float refferenceVoltage = 3.3f;

	public void init(){
		Log.printInfoln("Initialized NRF24 - Interface", true);	
		gpio = GpioFactory.getInstance();
		clkPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00);
		clkPinInit();
		csPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02);
		dataPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03);		
	}

	private void clkPinInit(){
		clkPin.setShutdownOptions(true);

		// create and register gpio pin listener
		clkPin.addListener(new GpioPinListenerDigital() {
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				if(readData){
					if(event.getEdge() == PinEdge.RISING){
						dataStream[dataCursor] = dataPin.getState() == PinState.HIGH;
						dataCursor++;
						if(dataCursor > maxBitNum-1){
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
		while(csPin.isLow()){
			//wait for Data...
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Log.printErrorln(e.toString());
			}
		}
		data = interpretByteCode();
		dataCursor = 0;
		readData = false;
	}
	
	private SensorData interpretByteCode(){
		int addres = 0;
		for(int i = 32; i < 39; i++){
			if(dataStream[i]){
				addres+=Math.pow(2, i-32);
			}
		}
		float temperature = 0;
		for(int i = 16; i < 26; i++){
			if(dataStream[i]){
				temperature+=Math.pow(2, i-16);
			}
		}
		temperature = calculValue(temperature);
		
		float voltage = 0;
		for(int i = 0; i < 10; i++){
			if(dataStream[i]){
				voltage+=Math.pow(2, i);
			}
		}
		voltage = calculValue(voltage);
		
		if(addres > 15){
			Log.printErrorln("Data: addres is to big! -> " + addres);
			return null;
		}
		
		return new SensorData((byte) addres, temperature, voltage);
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
}
