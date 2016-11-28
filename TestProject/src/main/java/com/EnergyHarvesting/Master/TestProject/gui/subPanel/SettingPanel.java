package com.EnergyHarvesting.Master.TestProject.gui.subPanel;

import java.awt.Color;

import javax.swing.JPanel;

public class SettingPanel extends PanelComponent{

	public SettingPanel(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void init() {
		setBounds();
		setBackground(Color.GREEN);
	}

	@Override
	public void update() {
		
	}

}
