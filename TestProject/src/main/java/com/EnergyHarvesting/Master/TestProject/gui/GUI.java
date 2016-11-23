package com.EnergyHarvesting.Master.TestProject.gui;

import javax.swing.*;

import controler.Controller;

/**
 * GUI Class -> View of our application
 * @author M.Geissbberger
 *
 */

public class GUI extends JFrame {
	
	//-----------------Swing---------------
	private static final long serialVersionUID = 1L;
	
	//------------Window Config------------
	private static final String title = "Energie Harvesting Project - Master";
	
	private static final int width = 1020;
	private static final int height = 720;
	private static final int xPos = 50;
	private static final int yPos = 50;
	//-----------\Window Config-------------
	
	private CostumPanel panel = new CostumPanel();

	public GUI() {
		setTitle(title);
		setSize(width,height);
		setLocation(xPos, yPos);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(panel);
	}
	
	public void setTextBox(String text){
		panel.setLabelText(text);
	}
	
	public void setData(Controller controler){
		//TODO get Data from controler
	}




}