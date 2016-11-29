package com.EnergyHarvesting.Master.TestProject.gui.subPanel;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import logger.Log;
import controler.Controller;
import controler.TemperaturSensor;

public class GraphPanel extends PanelComponent{
	
	private Controller controller;
	private GraphCanvas[] sensorCanvas;

	public GraphPanel(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	@Override
	public void init() {
		setBounds();
		setBackground(Color.red);
		sensorCanvas = new GraphCanvas[16];
	}
	
	private int initializedSensors = 0;
	private void initGraphs() {
		TemperaturSensor[] sensors = controller.getSensors();
		for(int i = 0; i < sensors.length; i++){
			if(sensors[i]!=null && sensorCanvas[i]==null){
				sensorCanvas[i] = new GraphCanvas(sensors[i]);
				initializedSensors++;
				Log.printInfoln(initializedSensors + " Sensors are now listed");
			}
		}
	}

	@Override
	public void update() {
		for(GraphCanvas gc: sensorCanvas){
			if(gc != null){
				gc.update();
			}
		}
	}
	
	public void setData(Controller controller){
		this.controller = controller;
		initGraphs();
	}
	
	private void paintGraph(Graphics g, int[] x, int[] y){
		super.paint(g);
		for(int i = 1; i < x.length && i < y.length; i++){
			g.drawLine(x[i-1],y[i-1],x[i],y[i]); 
		}		   
	}

}
