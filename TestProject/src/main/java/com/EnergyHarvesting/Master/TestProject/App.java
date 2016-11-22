package com.EnergyHarvesting.Master.TestProject;

import com.EnergyHarvesting.Master.TestProject.gui.GUI;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;


public class App 
{
    public static void main( String[] args )
    {

    	//Setup Gui
    	GUI gui = new GUI();
    	gui.setVisible(true);
    	
    	 //LED_PRG
        button1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07);
        led1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);
        
       
        boolean toggle = false;
        
        while(true){
        	if(button1.isHigh() &! toggle){
            	System.out.println("is High");
            	led1.setState(true);
            	gui.setTextBox("is High");
            	toggle = true;
            }else{
            	if(button1.isLow() && toggle){
            		System.out.println("is Low");
            		led1.setState(false);
            		gui.setTextBox("is Low");
            		toggle = false;
            	}            	        	
            }
        }
    }
}
