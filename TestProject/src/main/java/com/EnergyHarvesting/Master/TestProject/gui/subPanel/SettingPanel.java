package com.EnergyHarvesting.Master.TestProject.gui.subPanel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.EnergyHarvesting.Master.TestProject.App;

import logger.Log;

public class SettingPanel extends PanelComponent{
	
	private JCheckBox log;
	private JLabel logLabel;
	private JCheckBox logFile;
	private JLabel logFileLabel;
	private JTextField saveTime;
	private JLabel saveTimeLabel;	
	
	private int x_Devider1 = 200;

	public SettingPanel(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void init() {
		setLayout(null);
		setBounds();
		initDebug(20, 20);	
		initWriteLogFile(20, 40);
		initSaveTime(20, 60, ""+App.saveTimeMIN);
	}

	@Override
	public void update() {
				
	}
	
	private String lastSaveTimeString = "";
	private void updateSaveTime(){
		if(!lastSaveTimeString.equals(saveTime.getText())){
			String output = "";
			int saveTimeNum = 0;
			try {
				saveTimeNum = Integer.parseInt(saveTime.getText());
				if(saveTimeNum != 0){
					App.saveTimeMIN = saveTimeNum;
					output = saveTime.getText();
					Log.printInfoln("changed Time between saves to " + saveTimeNum + "min", true);
				}else{
					output = "INVALID";
					Log.printErrorln("can't change Time between saves to " + saveTimeNum);
				}
			} catch (Exception e) {
				Log.printErrorln("SettingPanel: Time between saves: input is not a number!");
				output = "INVALID";
			}
			lastSaveTimeString = output;
			saveTime.setText(output);
		}		
	}
	
	private void initDebug(int x, int y){
		log = new JCheckBox();
		logLabel = new JLabel("debug: ");
		add(log);
		add(logLabel);
		log.setBounds(x+x_Devider1+10, y, 20, 20);
		logLabel.setBounds(x, y, x_Devider1, 20);
		
		log.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
		            Log.debug = true;
		        } else {//checkbox has been deselected
		        	Log.debug = false;
		        };				
			}
		});
	}
	
	private void initWriteLogFile(int x, int y){
		logFile = new JCheckBox();
		logFileLabel = new JLabel("write Logfile: ");
		add(logFile);
		add(logFileLabel);
		logFile.setBounds(x+x_Devider1+10, y, 20, 20);
		logFileLabel.setBounds(x, y, x_Devider1, 20);
		
		logFile.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {	//checkbox has been selected
		            Log.writeLogFile = true;
		        } else {										//checkbox has been deselected
		        	Log.writeLogFile = false;
		        };				
			}
		});
	}
	
	private void initSaveTime(int x, int y, String text){
		saveTime = new JTextField(text);
		
		saveTimeLabel = new JLabel("time between saves [min]:");
		saveTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateSaveTime();
			}
		});
		
		add(saveTime);
		add(saveTimeLabel);
		
		saveTime.setBounds(x+x_Devider1+15, y, 40, 20);
		saveTimeLabel.setBounds(x, y, x_Devider1, 20);		
	}
}
