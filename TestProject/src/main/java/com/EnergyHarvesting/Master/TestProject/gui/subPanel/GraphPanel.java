package com.EnergyHarvesting.Master.TestProject.gui.subPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.saturn91.JGraph;

import logger.Log;
import controler.Controller;
import controler.TemperaturSensor;

public class GraphPanel extends PanelComponent{

	private Controller controller;

	private JGraph temperature;
	private JGraph voltage;

	private JTextField temperatureXmin, temperatureXmax, temperatureYmin, temperatureYmax;
	private JTextField voltageXmin, voltageXmax, voltageYmin, voltageYmax;
	private JLabel textTemperatureXmin, textTemperatureXmax, textTemperatureYmin, textTemperatureYmax;
	private JLabel textVoltageXmin, textVoltageXmax, textVoltageYmin, textVoltageYmax;

	private int width;
	private int height;

	private int positionMinMaxBox_X = 10;
	private int positionMinMaxBox_Y;

	private TemperaturSensor[] sensors;

	public GraphPanel(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.width = width;
		this.height = height;	
		positionMinMaxBox_Y = height-50;
	}

	@Override
	public void init() {
		setLayout(null);
		setBounds();
		initGraphs();	
		initMaxMinLine();
		initBackgrounds();
	}

	private boolean setVoltageFlag = false;
	private boolean setTemperatureFlag = false;
	private void initMaxMinLine() {
		int cursorX = positionMinMaxBox_X+10;
		int cursorY = positionMinMaxBox_Y;
		//----------Temperature----------------------------------:
		textTemperatureXmin = new JLabel("minX:");
		textTemperatureXmin.setBounds(cursorX, cursorY, 100, 18);	
		temperatureXmin = new JTextField("-");
		temperatureXmin.setBounds(cursorX+40, cursorY, 35, 18);
		temperatureXmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTemperatureFlag = true;				
			}
		});
		cursorY +=20;
		textTemperatureXmax = new JLabel("maxX:");
		textTemperatureXmax.setBounds(cursorX, cursorY, 100, 18);	
		temperatureXmax = new JTextField("-");
		temperatureXmax.setBounds(cursorX+40, cursorY, 35, 18);
		temperatureXmax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTemperatureFlag = true;				
			}
		});

		cursorX += 500;
		cursorY -= 20;
		textTemperatureYmin = new JLabel("minY:");
		textTemperatureYmin.setBounds(cursorX, cursorY, 100, 18);	
		temperatureYmin = new JTextField("-");
		temperatureYmin.setBounds(cursorX+40, cursorY, 35, 18);
		temperatureYmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTemperatureFlag = true;				
			}
		});
		cursorY +=20;
		textTemperatureYmax = new JLabel("maxY:");
		textTemperatureYmax.setBounds(cursorX, cursorY, 100, 18);	
		temperatureYmax = new JTextField("-");
		temperatureYmax.setBounds(cursorX+40, cursorY, 35, 18);
		temperatureYmax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTemperatureFlag = true;				
			}
		});
		add(textTemperatureXmax);
		add(temperatureXmax);
		add(textTemperatureXmin);
		add(temperatureXmin);
		add(textTemperatureYmax);
		add(temperatureYmax);
		add(textTemperatureYmin);
		add(temperatureYmin);

		//----------Voltage----------------------------------:
		cursorX = (width/2)+5+10;
		cursorY = positionMinMaxBox_Y;
		textVoltageXmin = new JLabel("minX:");
		textVoltageXmin.setBounds(cursorX, cursorY, 100, 18);	
		voltageXmin = new JTextField("-");
		voltageXmin.setBounds(cursorX+40, cursorY, 35, 18);
		voltageXmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVoltageFlag = true;				
			}
		});
		cursorY +=20;
		textVoltageXmax = new JLabel("maxX:");
		textVoltageXmax.setBounds(cursorX, cursorY, 100, 18);	
		voltageXmax = new JTextField("-");
		voltageXmax.setBounds(cursorX+40, cursorY, 35, 18);
		voltageXmax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVoltageFlag = true;				
			}
		});

		cursorX += 500;
		cursorY -= 20;
		textVoltageYmin = new JLabel("minY:");
		textVoltageYmin.setBounds(cursorX, cursorY, 100, 18);	
		voltageYmin = new JTextField("-");
		voltageYmin.setBounds(cursorX+40, cursorY, 35, 18);
		voltageYmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVoltageFlag = true;				
			}
		});
		cursorY +=20;
		textVoltageYmax = new JLabel("maxY:");
		textVoltageYmax.setBounds(cursorX, cursorY, 100, 18);	
		voltageYmax = new JTextField("-");
		voltageYmax.setBounds(cursorX+40, cursorY, 35, 18);
		voltageYmax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVoltageFlag = true;				
			}
		});
		add(textVoltageXmax);
		add(voltageXmax);
		add(textVoltageXmin);
		add(voltageXmin);
		add(textVoltageYmax);
		add(voltageYmax);
		add(textVoltageYmin);
		add(voltageYmin);
	}

	private void initBackgrounds(){
		setBackground(Color.GRAY);
		JPanel background = new JPanel();
		background.setBounds(positionMinMaxBox_X, positionMinMaxBox_Y, width-25, 40);
		background.setBackground(Color.WHITE);
		add(background);
		JPanel seperator = new JPanel();
		seperator.setBackground(Color.GRAY);
		seperator.setBounds(0, height-4, width, 4);
		add(seperator);
	}

	private void initGraphs(){
		temperature = new JGraph("Temperature", 10, 10, (width/2)-20, height-70);
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

		voltage = new JGraph("Voltage", (width/2)+5, 10, (width/2)-20, height-70);
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

		updateMinMaxFlags();

		temperature.update();
		voltage.update();
	}

	private void updateMinMaxFlags() {
		//check flags of min and max values axis
		if(setTemperatureFlag){
			boolean notAnumber = false;
			boolean checkInput = false;
			float num = 0;
			try {
				num = Float.parseFloat(temperatureXmin.getText());
				notAnumber = false;
				checkInput = true;
			} catch (Exception e) {
				notAnumber=true;
			}

			if(!notAnumber){
				temperature.setMinValueX(num);
			}

			try {
				num = Float.parseFloat(temperatureXmax.getText());
				notAnumber = false;
			} catch (Exception e) {
				notAnumber=true;
			}

			if(!notAnumber){
				temperature.setMaxValueX(num);
			}

			try {
				num = Float.parseFloat(temperatureYmin.getText());
				notAnumber = false;
				checkInput = true;
			} catch (Exception e) {
				notAnumber=true;
			}

			if(!notAnumber){
				temperature.setMinValueY(num);
			}

			try {
				num = Float.parseFloat(temperatureYmax.getText());
				notAnumber = false;
			} catch (Exception e) {
				notAnumber=true;
			}

			if(!notAnumber){
				temperature.setMaxValueY(num);
			}

			if(!checkInput){
				Log.printErrorln("Wrong Input must be a Float!");
			}
			setTemperatureFlag = false;
		}

		if(setVoltageFlag){
			boolean notAnumber = false;
			boolean checkInput = false;
			float num = 0;
			try {
				num = Float.parseFloat(voltageXmin.getText());
				notAnumber = false;
				checkInput = true;
			} catch (Exception e) {
				notAnumber=true;
			}

			if(!notAnumber){
				voltage.setMinValueX(num);
			}

			try {
				num = Float.parseFloat(voltageXmax.getText());
				notAnumber = false;
			} catch (Exception e) {
				notAnumber=true;
			}

			if(!notAnumber){
				voltage.setMaxValueX(num);
			}

			try {
				num = Float.parseFloat(voltageYmin.getText());
				notAnumber = false;
				checkInput = true;
			} catch (Exception e) {
				notAnumber=true;
			}

			if(!notAnumber){
				voltage.setMinValueY(num);
			}

			try {
				num = Float.parseFloat(voltageYmax.getText());
				notAnumber = false;
			} catch (Exception e) {
				notAnumber=true;
			}

			if(!notAnumber){
				voltage.setMaxValueY(num);
			}

			if(!checkInput){
				Log.printErrorln("Wrong Input must be a Float!");
			}
			setVoltageFlag = false;
		}

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
