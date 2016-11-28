package com.EnergyHarvesting.Master.TestProject.gui.subPanel;

import javax.swing.JPanel;

public abstract class PanelComponent extends JPanel{
	
	private int x;
	private int y; 
	private int width;
	private int height;
	
	public PanelComponent(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public abstract void init();
	public abstract void update();

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void setBounds(){
		setBounds(x, y, width, height);
	}
}
