/*
 * --------------------------------------------------------------------------------------------------
 * ProjectName: EnergyHervesting-Master
 * Author:		M.Geissberger alias Saturn91
 * Hardware:	RaspberryPI 2B+ 1.1
 * Github: https://github.com/Saturn91/GIT_MechaProjekt_Master (private)
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

import java.sql.Date;
import java.text.SimpleDateFormat;

import logger.Log;
import nrf24_Reciver.NRF24;
import nrf24_Reciver.NRF24_ReciverInterface;

import com.EnergyHarvesting.Master.TestProject.gui.GUI;

import controler.Controller;

/**
 * Main Application, which runs on a RaspberryPI
 * 
 * args[]:
 * 	1: with or without gui? | 1 or 0
 * 	2: debug?				| 1 or 0
 *  3: write Log file?		| 1 or 0
 *  4: time between saves	| 0 ... n minutes [int]
 * @author M.Geissbberger
 *
 */
public class App 
{
	//---------Config-----------
	public static int saveTimeMIN = 5;
	private static String fileName = "DataFile.data";
	private static boolean withGui = false;
	//--------\Config-----------
	
	//---------Time/Date--------
	private static Date date = new Date(System.currentTimeMillis());
	private static final int calculateMStoMin = 60000;
	//--------\TimeDate--------
	
	//---------Controller------
	private static Controller _controller;
	private static boolean _record = false;
	private static GUI gui;
	//--------\Controller------
	
    public static void main( String[] args )
    {    	
    	//-------Setup Gui-------
    	withGui = true;
    	
    	if(args.length > 0){
    		withGui = args[0].equals("1");
    	}
    	
    	//-------Setup Logger--------
    	Log.debug = false;
    	Log.writeLogFile = false;
    	
    	if(args.length > 1){
    		Log.debug = args[1].equals("1");
    	}
    	if(args.length > 2){
    		Log.writeLogFile = args[2].equals("1");
    	}
    	
    	if(args.length > 3){
    		try {
    			saveTimeMIN = Integer.parseInt(args[3]);
			} catch (Exception e) {
				System.err.println("Argument 4 must be a number!");
				System.exit(-1);
			}
    	}
    	
    	gui = null;    	
    	if(withGui){
    		try {
    			gui = new GUI();
            	gui.init();
			} catch (Exception e) {
				withGui = false;
				Log.printErrorln(e.getMessage());
				Log.printErrorln("Failed to start with GUI - starting without");
			}    		
    	}else{
    		Log.printInfoln("Start without GUI");
    	}
    	
    	//Setup NRF24_Recierver
    	NRF24_ReciverInterface nrf24 = new NRF24();
    	nrf24.init();
        
        //Setup Controller
        _controller = new Controller();
        
        Log.printInfoln("started Masterprogram", true);
        
        //Main loop of application (exits if program or window gets closed)
        while(true){
        	//---------read SPI-Data-----------
        	_controller.update();
        	if(_record){
        		_controller.handleData(nrf24.getData());
        	}        	
        	
        	//------Display Changes in Gui------
        	if(withGui){
        		gui.setData(_controller);
        		gui.update();
        	}        	
        	
        	//---save Data after n-minutes------
        	save(saveTimeMIN, _controller);
        }
    }
    
    private static long lastTime = 0;
    private static long nowTime;
    /**
     * void: save(n-minutes, controller)
     * saves the Data stored in Controller all  n-minutes with controller.save()
     */    
    private static void save(int deltaMin, Controller controller){
    	nowTime = System.currentTimeMillis();
    	if(lastTime == 0){
    		lastTime = nowTime;
    	}
    	if((nowTime - lastTime)/calculateMStoMin >= deltaMin){
    		controller.save(fileName);
    		Log.saveLogFile();
    		date = new Date(nowTime);
    		lastTime = nowTime;
    	}    	
    }
    
    public static void clearController(){
    	_controller = new Controller();
    	setController(_controller);
    }
    
    public static String getDate(){
    	nowTime = System.currentTimeMillis();
    	date.setTime(nowTime);
    	return new SimpleDateFormat("dd:MM:yy : HH:mm:ss").format(date);
    }
    
    public static boolean isWithGui(){
    	return withGui;
    }
    
    public static void setRecord(boolean record){
    	_record = record;
    }
    
    public static void setController(Controller controller){
    	_controller = controller;
    	if(withGui){
    		gui.setData(_controller);
    		gui.clearGraphPanel();
    	}  
    }
    
    public static void save(){
		String date = getDate().replaceAll(":", "");
		date = date.replaceAll(" ", "_");
		_controller.save(date +"Data"+".data");
		Log.saveLogFile();    	
    }
}
