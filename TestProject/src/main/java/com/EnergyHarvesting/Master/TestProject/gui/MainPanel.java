package com.EnergyHarvesting.Master.TestProject.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.EnergyHarvesting.Master.TestProject.gui.subPanel.CMD_Panel;
import com.EnergyHarvesting.Master.TestProject.gui.subPanel.GraphPanel;
import com.EnergyHarvesting.Master.TestProject.gui.subPanel.PanelComponent;
import com.EnergyHarvesting.Master.TestProject.gui.subPanel.SettingPanel;

import controler.Controller;

/**
 * CostumPanel
 * this class will hold all the physikly existing GUI elements such as labels and graphs, only used 
 * to display objects
 * 
 * @author M.Geissbberger
 *
 */

public class MainPanel extends PanelComponent{
	
	private static final long serialVersionUID = 1L;
	
	private GraphPanel graphPanel;
	private SettingPanel settingPanel;
	private CMD_Panel cmd_Panel;

	public MainPanel(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public void init(){
		setLayout(null);
		graphPanel = new GraphPanel(0, 0, 1350, 540);
		settingPanel = new SettingPanel(0, 540, 675, 360);
		cmd_Panel = new CMD_Panel(675, 540, 675, 360, 22);	//should be: 900, 540, 450, 360, (22 = maxLines)
		
		add(graphPanel);
		graphPanel.init();
		add(settingPanel);
		settingPanel.init();
		add(cmd_Panel);
		cmd_Panel.init();
	}
	
	public void setData(Controller c){
		graphPanel.setData(c);
	}

	@Override
	public void update() {
		graphPanel.update();
		settingPanel.update();
		if(settingPanel.deleteCMDLine()){
			cmd_Panel.clearFirstLine();
			settingPanel.resetDelCMDLine();
		}
		cmd_Panel.update();
	}	
	
	public void addCMD_Line(String line){
		cmd_Panel.addLine(line);
	}
	
	public void addCMD_Error_Line(String error){
		cmd_Panel.addErrorLine(error);
	}
	
	public void addCMD_Info_Line(String info){
		cmd_Panel.addInfoLine(info);
	}
}
