package com.EnergyHarvesting.Master.TestProject.gui;

import java.awt.BorderLayout;

import javax.swing.*;

import com.EnergyHarvesting.Master.TestProject.gui.subPanel.GraphPanel;

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
	private static final String title = "Energie Harvesting Project - Master: READ ONLY!!!!";
	
	private static final int width = 1350;
	private static final int height = 950;
	private static final int xPos = 50;
	private static final int yPos = 50;
	//-----------\Window Config-------------
	
	private static MainPanel panel = new MainPanel(0, 0, width, height);

	public GUI() {
		
	}
	
	public void init(){
		setTitle(title);
		setSize(width,height);
		setLocation(xPos, yPos);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		add(panel);
		panel.init();
		panel.setBounds();
		setVisible(true);
		
	}
	
	public void setData(Controller controler){
		panel.setData(controler);
	}
	
	public static void appendLogLine(String lastLogLine, int type){
		switch (type){
		default: 
				panel.addCMD_Line(lastLogLine);
				break;
		case 1: 
				panel.addCMD_Info_Line(lastLogLine);
				break;
		case -1: 
			panel.addCMD_Error_Line(lastLogLine);
			break;
		}		
	}
	
	public void update(){
		panel.update();
	}
	
	public void clearGraphPanel(){
		panel.clearGraphs();
	}




}