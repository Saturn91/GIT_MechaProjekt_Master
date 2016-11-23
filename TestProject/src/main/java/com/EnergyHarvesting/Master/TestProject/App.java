/*
 * --------------------------------------------------------------------------------------------------
 * ProjectName: EnergyHervesting-Master
 * Author:		M.Geissberger alias Saturn91
 * Hardware:	RaspberryPI 2B+ 1.1
 * Git: https://github.com/Saturn91/GIT_MechaProjekt_Master (private)
 * --------------------------------------------------------------------------------------------------
 * Description:
 * 
 * this project was made to implement the Master of a EnergyHarvestingNetwork which measures
 * temperaturdata in our classroom. At the SPI-Interface of the PI a radio-reciever is connected.
 * In the room are 3 different PIC-uProcesors with temperaturesensors and radio-beacon, those are the
 * energy harvesting driven Slaves. 
 * 
 * The master (Raspberry PI) displays the received data in a Graph for each Slave, one graph showing
 * the temperature, the other the current voltage provided by the energyharvesting Device connected
 * to the slave.
 * -------------------------------------------------------------------------------------------------- 
 */

package com.EnergyHarvesting.Master.TestProject;

import com.EnergyHarvesting.Master.TestProject.gui.GUI;
import com.EnergyHarvesting.Master.TestProject.spi.SPI_Manager;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

import controler.Controller;

/**
 * Main Application, which runs on a RaspberryPI
 * @author M.Geissbberger
 *
 */

public class App 
{
	//---------Config-----------
	private static int saveTimeMIN = 5;
	//--------\Config-----------
	
	
	private static final int calculateStoMin = 60000;
	
    public static void main( String[] args )
    {

    	//Setup Gui
    	GUI gui = new GUI();
    	gui.setVisible(true);
        
        //Setup Controller
        Controller controller = new Controller();
        
        //Main loop of application
        while(true){
        	//---------read SPI-Data-----------
        	byte[] data = SPI_Manager.read();
        	controller.handleData(data);
        	
        	//-------Calculate new Data---------
        	controller.calculate();
        	
        	//------Display Changes in Gui------
        	gui.setData(controller);
        	
        	//---save Data after n-minutes------
        	save(saveTimeMIN, controller);
        }
    }
    
    private static long lastTime = 0;
    /**
     * void: save(n-minutes, controller)
     * saves the Data stored in Controller all  n-minutes with controller.save()
     */    
    private static void save(int deltaMin, Controller controller){
    	long nowTime = System.currentTimeMillis();
    	if(lastTime == 0){
    		lastTime = nowTime;
    	}
    	if((nowTime - lastTime)/calculateStoMin >= deltaMin){
    		controller.save();
    		lastTime = nowTime;
    	}
    	
    }
}
