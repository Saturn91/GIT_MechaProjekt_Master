package com.EnergyHarvesting.Master.TestProject.gui.subPanel;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import controler.Controller;

public class GraphPanel extends PanelComponent{

	public GraphPanel(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	@Override
	public void init() {
		setBounds();
		setBackground(Color.red);
	}

	@Override
	public void update() {
		
	}
	
	public void setData(Controller controller){
		
	}
	
	private void paintGraph(Graphics g, int[] x, int[] y){
		super.paint(g);
		for(int i = 1; i < x.length && i < y.length; i++){
			g.drawLine(x[i-1],y[i-1],x[i],y[i]); 
		}		   
	}

}
