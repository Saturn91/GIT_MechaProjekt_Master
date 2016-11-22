package com.EnergyHarvesting.Master.TestProject.gui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * CostumPanel
 * this class will hold all the physikly existing GUI elements such as labels and graphs, only used 
 * to display objects
 * 
 * @author M.Geissbberger
 *
 */

public class CostumPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private JLabel testLabel;
	private Canvas graph;

	public CostumPanel() {
		super(new GridLayout(3,1));  //3 rows, 1 column

		testLabel = new JLabel("Text-Only Label");

		//Create tool tips, for the heck of it.
		testLabel.setToolTipText("A label containing only text");
		add(testLabel);
	}

	public void setLabelText(String text){
		testLabel.setText(text);
	}

	public void paint(Graphics g, int[] x, int[] y){
		super.paint(g);
		for(int i = 1; i < x.length && i < y.length; i++){
			g.drawLine(x[i-1],y[i-1],x[i],y[i]); 
		}		   
	}
}
