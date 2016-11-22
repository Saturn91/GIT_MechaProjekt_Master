package com.EnergyHarvesting.Master.TestProject;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Testapp to check Pins
 * @author M.Geissbberger
 *
 */
public class App 
{
	private static GpioPinDigitalInput button1;
	private static GpioController gpio = GpioFactory.getInstance();
	
    public static void main( String[] args )
    {
        button1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07);
                
        while(true){
        	if(button1.isHigh()){
            	System.out.println("is High");
            	try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }else{
            	if(button1.isLow()){
            		System.out.println("is Low");
            		try {
    					Thread.sleep(500);
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
            	}else{
            		System.out.println("no data!");
            		try {
    					Thread.sleep(500);
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
            	}        	
            }
        }
    }
}
