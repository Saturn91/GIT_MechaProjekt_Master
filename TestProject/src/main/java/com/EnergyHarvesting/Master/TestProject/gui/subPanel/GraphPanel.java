package com.EnergyHarvesting.Master.TestProject.gui.subPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;

import com.saturn91.JGraph;

import logger.Log;
import controler.Controller;
import controler.TemperaturSensor;

public class GraphPanel extends PanelComponent{

	private Controller controller;

	private JGraph temperature;
	private JGraph voltage;

	private int width;
	private int height;

	private TemperaturSensor[] sensors;

	public GraphPanel(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.width = width;
		this.height = height;		
	}

	@Override
	public void init() {
		setBounds();
		setBackground(Color.GRAY);
		setLayout(null);
		temperature = new JGraph("Temperature", 10, 10, (width/2)-20, height-20);
		temperature.setBorder(50);
		temperature.setMinValue(0, -10);
		temperature.setMaxValue(24, 50);
		temperature.setxSeperator(12);
		temperature.setySeperator(12);
		temperature.setGraphNameTextSize(12);
		temperature.setxAxisText("[h]");
		temperature.setyAxisText("[C°]");
		temperature.setDotSize(10);
		temperature.setArrowSize(5);
				temperature.setShowDots(false);	//--> only for testing
		add(temperature);

		voltage = new JGraph("Voltage", (width/2)+5, 10, (width/2)-20, height-20);
		voltage.setBorder(50);
		voltage.setMinValue(0, 0);
		voltage.setMaxValue(24, 10);
		voltage.setxSeperator(12);
		voltage.setySeperator(10);
		voltage.setGraphNameTextSize(12);
		voltage.setxAxisText("[h]");
		voltage.setyAxisText("[V]");
		voltage.setDotSize(10);
		voltage.setArrowSize(5);
				voltage.setShowDots(false);		//only for testing
		add(voltage);
	}

	@Override
	public void update() {
		sensors = controller.getSensors();

		for(int i = 0; i < sensors.length; i++){
			if(sensors[i]!=null){
				if(sensors[i].hasNewData()){
					float hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
					float minutes = Calendar.getInstance().get(Calendar.MINUTE);
					float time = hour + minutes/60;
					int index = sensors[i].getTemperatur().getPoints().size()-1;
					temperature.addPoint(i, time , sensors[i].getTemperatur().getPoints().get(index).value);
					voltage.addPoint(i, time , sensors[i].getVoltage().getPoints().get(index).value);
					temperature.addGraphName(i, sensors[i].getName());
					voltage.addGraphName(i, sensors[i].getName());
					sensors[i].resetNewData();
				}
			}
		}

		temperature.update();
		voltage.update();
	}

	public void setData(Controller controller){
		this.controller = controller;
	}
	
	/**
	 * Repaint Graphs for new Data
	 */
	public void clear(){
		temperature.clear();
		voltage.clear();
		sensors = controller.getSensors();

		for(int i = 0; i < sensors.length; i++){
			if(sensors[i]!=null){
				temperature.addGraphName(i, sensors[i].getName());
				voltage.addGraphName(i, sensors[i].getName());
				for(int j = 0; j < sensors[i].getVoltage().getPoints().size(); j++){
					int index = j;
					long oldTime = sensors[i].getTime(index);
					Date date = new Date(oldTime);
					Calendar calender = Calendar.getInstance();
					calender.setTime(date);
					float hour = calender.get(Calendar.HOUR_OF_DAY);
					float minutes = calender.get(Calendar.MINUTE);
					float time = hour + minutes/60;
					temperature.addPoint(i, time , sensors[i].getTemperatur().getPoints().get(index).value);
					voltage.addPoint(i, time , sensors[i].getVoltage().getPoints().get(index).value);
				}				
			}
		}
	}
}
