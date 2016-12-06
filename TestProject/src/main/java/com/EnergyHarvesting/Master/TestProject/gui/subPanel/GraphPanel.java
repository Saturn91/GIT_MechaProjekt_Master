package com.EnergyHarvesting.Master.TestProject.gui.subPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

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
		temperature.setMaxValue(20, 50);
		temperature.setxSeperator(10);
		temperature.setySeperator(12);
		temperature.setGraphNameTextSize(10);
		temperature.setxAxisText("[h]");
		temperature.setyAxisText("[C°]");
		temperature.setDotSize(10);
		temperature.setArrowSize(5);
		add(temperature);

		voltage = new JGraph("Voltage", (width/2)+5, 10, (width/2)-20, height-20);
		voltage.setBorder(50);
		voltage.setMinValue(0, 0);
		voltage.setMaxValue(5, 10);
		voltage.setxSeperator(10);
		voltage.setySeperator(10);
		voltage.setGraphNameTextSize(10);
		voltage.setxAxisText("[h]");
		voltage.setyAxisText("[V]");
		voltage.setDotSize(10);
		voltage.setArrowSize(5);
		add(voltage);
	}

	@Override
	public void update() {
		sensors = controller.getSensors();

		for(int i = 0; i < sensors.length; i++){
			if(sensors[i]!=null){
				if(sensors[i].hasNewData()){
					int index = sensors[i].getTemperatur().getPoints().size()-1;
					temperature.addPoint(i, index , sensors[i].getTemperatur().getPoints().get(index).value);
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
}
