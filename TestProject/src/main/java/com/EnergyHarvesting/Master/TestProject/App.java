package com.EnergyHarvesting.Master.TestProject;

import com.EnergyHarvesting.Master.TestProject.gui.GUI;
import com.EnergyHarvesting.Master.TestProject.spi.SPI_Manager;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

import controler.Controler;


public class App 
{
	private static GpioController gpio = GpioFactory.getInstance();
	
    public static void main( String[] args )
    {

    	//Setup Gui
    	GUI gui = new GUI();
    	gui.setVisible(true);
        
        //Setup Controler
        Controler controler = new Controler();
        
        //Main loop of application
        while(true){
        	//---------read SPI-Data-----------
        	byte[] data = SPI_Manager.read();
        	controler.handleData(data);
        	
        	//-------Calculate new Graphs-------
        	controler.calcul();
        	
        	//------Display Changes in Guy------
        	gui.setData(controler);
        }
    }
}
