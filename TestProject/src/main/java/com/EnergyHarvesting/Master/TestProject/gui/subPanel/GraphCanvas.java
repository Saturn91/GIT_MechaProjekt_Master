package com.EnergyHarvesting.Master.TestProject.gui.subPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import logger.Log;
import controler.Point;
import controler.TemperaturSensor;

public class GraphCanvas extends JPanel {
	
	private TemperaturSensor data;
	private int x;
	private int y;
	private int height;
	private int width;
	
	private boolean added = false;
	
	private float highestTempValue = -99;
	private float lowestTempValue = -99;
	
	private float highestVoltageValue = -99;
	private float lowestVoltageValue = -99;
	
	private long highestTimeValue = 0;
	private long lowestTimeValue = 0;
	
	public GraphCanvas(int x, int y, int width, int height, TemperaturSensor data) {
		this.data = data;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;	
		init();
	}
	
	public void init(){
		setBounds(x, y, width, height);
		Log.printInfoln("Create Graph for " + data.getName(), true);
		setLayout(null);
		setBackground(Color.white);
		repaint();
	}
	
	private int lastSize = 0;
	private boolean repaint = false;
	public void update(){
		int size = data.getTemperatur().getPoints().size();
		if(lastSize != size){
			highestTempValue = getHighestFloatValue(data.getTemperatur().getPoints().toArray());
			lowestTempValue = getLowestFloatValue(data.getTemperatur().getPoints().toArray());
			highestVoltageValue = getHighestFloatValue(data.getVoltage().getPoints().toArray());
			lowestVoltageValue = getLowestFloatValue(data.getVoltage().getPoints().toArray());
			highestTimeValue = getHighestLongValue(data.getVoltage().getPoints().toArray());
			lowestTimeValue = getLowestLongValue(data.getVoltage().getPoints().toArray());
			repaint = true;
			lastSize = size;
		}		
	}
	
	public ArrayList<Integer> temperaturValues = new ArrayList<Integer>();
	public ArrayList<Integer> voltageValues = new ArrayList<Integer>();
	
	public ArrayList<Integer> xValues = new ArrayList<Integer>();
	
	public void paint(Graphics g){		//gets called automaticly!
		if(repaint && data.getTemperatur().getPoints().size() > 1){
			temperaturValues.clear();
			voltageValues.clear();
			xValues.clear();
			ArrayList<Point> tempPoints = data.getTemperatur().getPoints();
			ArrayList<Point> voltPoints = data.getTemperatur().getPoints();
			for(int i = 0; i < tempPoints.size(); i++){
				temperaturValues.add(getGraphY(tempPoints.get(i), true));
				voltageValues.add(getGraphY(voltPoints.get(i), false));
				xValues.add(getGraphX(tempPoints.get(i)));
			}
			paintTemperaturGraph(g, xValues, temperaturValues);
			paintVoltageGraph(g, xValues, voltageValues);
			repaint();
			repaint = false;
		}		
	}
	
	public int getGraphX(Point point){
		return (int) (((point.time-lowestTimeValue)*width)/(highestTimeValue - lowestTimeValue));	
	}
	
	public int getGraphY(Point point, boolean temperatur){
		if(temperatur){
			return (int) (((point.value - lowestTempValue)*height)/(highestTempValue-lowestTempValue));
		}else{
			return (int) (((point.value - lowestVoltageValue)*height)/(highestVoltageValue-lowestVoltageValue));
		}
	}	
	
	private void paintVoltageGraph(Graphics g, ArrayList<Integer> x, ArrayList<Integer> y){		
		g.setColor(Color.red);
		printGraph(g, x, y);			   
	}	
	
	private void paintTemperaturGraph(Graphics g, ArrayList<Integer> x, ArrayList<Integer> y){		
		g.setColor(Color.blue);
		printGraph(g, x, y);			   
	}
	
	private void printGraph(Graphics g, ArrayList<Integer> x, ArrayList<Integer> y){
		if(x.size() > 1){
			for(int i = 0; i < x.size() && i < y.size(); i++){
				if(i > 0){
					g.drawLine(x.get(i-1),y.get(i-1),x.get(i),y.get(i)); 
				}			
			}	
		}
	}
	
	private float getHighestFloatValue(Object[] objects){
		float maxValue = Float.MIN_VALUE;
		for(int i = 0; i < objects.length; i++){
			if(((Point) objects[i]).value > maxValue){
				maxValue = ((Point) objects[i]).value;
			}
		}
		
		return maxValue;
	}
	
	private float getLowestFloatValue(Object[] objects){
		float minValue = Float.MAX_VALUE;
		for(int i = 0; i < objects.length; i++){
			if(((Point) objects[i]).value < minValue){
				minValue = ((Point) objects[i]).value;
			}
		}
		
		return minValue;
	}
	
	private long getHighestLongValue(Object[] objects){
		long maxValue = Long.MIN_VALUE;
		for(int i = 0; i < objects.length; i++){
			if(((Point)objects[i]).time > maxValue){
				maxValue = ((Point)objects[i]).time;
			}
		}
		
		return maxValue;
	}
	
	private long getLowestLongValue(Object[] objects){
		long minValue = Long.MAX_VALUE;
		for(int i = 0; i < objects.length; i++){
			if(((Point)objects[i]).time < minValue){
				minValue = ((Point)objects[i]).time;
			}
		}
		
		return minValue;
	}
	
	public void setAdded(){
		added = true;
	}
	
	public boolean isAdded(){
		return added;
	}
}
