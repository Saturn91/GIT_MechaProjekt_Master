package com.EnergyHarvesting.Master.TestProject.gui.subPanel;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import logger.Log;
import controler.Controller;
import controler.TemperaturSensor;

public class GraphPanel extends PanelComponent{
	
	private Controller controller;

	public GraphPanel(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	@Override
	public void init() {
		setBounds();
		setBackground(Color.GRAY);
	}

	@Override
	public void update() {
		
	}
	
	public void setData(Controller controller){
		this.controller = controller;
	}
}
