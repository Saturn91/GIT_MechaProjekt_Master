package com.EnergyHarvesting.Master.TestProject;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Testapp to check Pins
 * @author M.Geissbberger
 *
 */
public class App 
{
	private static GpioPinDigitalInput button1;
	private static GpioPinDigitalOutput led1;
	private static GpioController gpio = GpioFactory.getInstance();
	
    public static void main( String[] args )
    {
        button1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07);
        led1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);
        
        boolean toggle = false;
        
        while(true){
        	if(button1.isHigh() &! toggle){
            	System.out.println("is High");
            	led1.setState(true);
            	toggle = true;
            }else{
            	if(button1.isLow() && toggle){
            		System.out.println("is Low");
            		led1.setState(false);
            		toggle = false;
            	}            	        	
            }
        }
    }
}
