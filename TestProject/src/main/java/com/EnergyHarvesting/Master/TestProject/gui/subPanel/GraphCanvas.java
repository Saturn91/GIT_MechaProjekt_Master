package com.EnergyHarvesting.Master.TestProject.gui.subPanel;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import logger.Log;
import controler.TemperaturSensor;

public class GraphCanvas extends JPanel {
	
	private TemperaturSensor data;
	
	public GraphCanvas(TemperaturSensor data) {
		this.data = data;
		Log.printInfoln("Create Graph for " + data.getName());
		setLayout(null);
		setBackground(Color.white);
		repaint();
	}
	
	public void paint(Graphics g){		//gets called automaticly!
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(5, 5, 30, 30);
		repaint();
	}
	
	private int lastSize = 0;
	public void update(){
		int size = data.getTemperatur().getPoints().size();
		if(lastSize != size){
			Log.println("dataLenght: " + size);
			lastSize = size;
		}		
	}
	
}
