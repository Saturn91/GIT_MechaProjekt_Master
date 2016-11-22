package com.EnergyHarvesting.Master.TestProject.gui;

import javax.swing.*;

/**
 * GUI Class -> View of our application
 * @author M.Geissbberger
 *
 */

public class GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private CostumPanel panel = new CostumPanel();

	public GUI() {
		setTitle("Energie Harvesting Project - Master");
		setSize(1020,720);
		setLocation(50,50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(panel);
	}
	
	public void setTextBox(String text){
		panel.setLabelText(text);
	}




}