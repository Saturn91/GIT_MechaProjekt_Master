package com.EnergyHarvesting.Master.TestProject.gui.subPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.EnergyHarvesting.Master.TestProject.App;
import com.saturn91.saveToFile.SaveToFile;

import controler.Controller;
import logger.Log;

public class SettingPanel extends PanelComponent{
	
	private JLabel titleSettings;
	private JCheckBox log;
	private JLabel logLabel;
	private JCheckBox logFile;
	private JLabel logFileLabel;
	private JTextField saveTime;
	private JLabel saveTimeLabel;	
	private JButton loadBtn;
	private JButton clearLineInCMDBtn;
	private JTextField loadPath;
	private static JTextField[] sensorNamesFields = new JTextField[16];
	private JLabel[] sensorNamesLabels = new JLabel[16];
	
	private int textSize = 10;
	private Font font = new Font("SansSerif", Font.BOLD, textSize);
	
	private int x_Devider1 = 175;
	
	private boolean deleteLineInCMD = false;
	
	private static String[] sensorNames = new String[16];
	private static boolean updateSensorNames = false;

	public SettingPanel(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void init() {
		setLayout(null);
		setBounds();
		titleSettings = new JLabel("Settings:");
		add(titleSettings);
		titleSettings.setBounds(20, 20, 150, 20);
		initDebug(20, 45);	
		initWriteLogFile(20, 70);
		initSaveTime(20, 95, ""+App.saveTimeMIN);
		initClearButton(20, 120);
		initLoadBtn(20, 145);
		initSensorNames(280, 20);		
	}

	@Override
	public void update() {
		if(load){
			load();
			load = false;
		}
		
		if(saveTimeFlag){
			updateSaveTime();
			saveTimeFlag = false;
		}
		
		Log.debug = loggerFlag;
		Log.writeLogFile = writeLogFileFlag;
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
	
	private boolean loggerFlag = false;
	private void initDebug(int x, int y){
		log = new JCheckBox();
		log.setFont(font);
		logLabel = new JLabel("debug: ");
		logLabel.setFont(font);
		add(log);
		add(logLabel);
		log.setBounds(x+x_Devider1+10, y, 20, 20);
		logLabel.setBounds(x, y, x_Devider1, 20);
		
		log.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
					loggerFlag = true;
		        } else {//checkbox has been deselected
		        	loggerFlag = false;
		        };				
			}
		});
	}
	
	private boolean writeLogFileFlag = false;
	private void initWriteLogFile(int x, int y){
		logFile = new JCheckBox();
		logFile.setFont(font);
		logFileLabel = new JLabel("write Logfile: ");
		logFileLabel.setFont(font);
		add(logFile);
		add(logFileLabel);
		logFile.setBounds(x+x_Devider1+10, y, 20, 20);
		logFileLabel.setBounds(x, y, x_Devider1, 20);
		
		logFile.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {	//checkbox has been selected
					writeLogFileFlag = true;
		        } else {										//checkbox has been deselected
		        	writeLogFileFlag = false;
		        };				
			}
		});
	}
	
	private boolean saveTimeFlag = false;
	private void initSaveTime(int x, int y, String text){
		saveTime = new JTextField(text);
		saveTime.setFont(font);
		saveTimeLabel = new JLabel("time between saves [min]:");
		saveTimeLabel.setFont(font);
		saveTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveTimeFlag = true;
			}
		});
		
		add(saveTime);
		add(saveTimeLabel);
		
		saveTime.setBounds(x+x_Devider1+15, y, 40, 20);
		saveTimeLabel.setBounds(x, y, x_Devider1, 20);		
	}
	
	private boolean load = false;
	private void initLoadBtn(int x, int y){
		loadBtn = new JButton("Load Data");
		loadBtn.setFont(font);
		loadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load = true;
			}
		});
		
		loadPath = new JTextField("Filepath.data");
		loadPath.setFont(font);
		add(loadBtn);
		add(loadPath);
		loadBtn.setBounds(x, y, 110, 20);
		loadPath.setBounds(x, y+25, 250, 20);
		
	}
	
	private void initClearButton(int x, int y){
		clearLineInCMDBtn = new JButton("Clear CMD");
		clearLineInCMDBtn.setFont(font);
		clearLineInCMDBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				deleteLineInCMD = true;			
			}
		});
		add(clearLineInCMDBtn);
		clearLineInCMDBtn.setBounds(x, y, 110, 20);
	}
	
	public boolean deleteCMDLine(){
		return deleteLineInCMD;
	}
	
	public void resetDelCMDLine(){
		deleteLineInCMD = false;
	}
	
	public void load(){
		Log.printInfoln("Start load Data from: " + loadPath.getText(), true);
		Controller loadController = null;
		boolean loadError = false;
		try {
			while(loadController == null){
				loadController = (Controller) SaveToFile.getDataFromBinaryFile(loadPath.getText());
			}
			Log.printInfoln("successfully loaded Data from: " + loadPath.getText(), true);
		} catch (Exception e2) {
			loadError = true;
		}
		
		if(loadController != null){
			App.setController(loadController);
		}else{
			loadError = true;
		}			
		
		if(loadError){
			Log.printErrorln("not able to load: " + loadPath.getText());
		}
	}
	
	public static String[] getSensorNames(){
		//update SensorNames
		return sensorNames;
	}
	
	public static boolean updateSensorNames(){
		for(int i = 0; i < 16; i++){
			if(!sensorNamesFields[i].getText().isEmpty() |! sensorNamesFields[i].getText().equals("")){
				sensorNames[i] = sensorNamesFields[i].getText();
			}else{
				sensorNames[i] = null;
			}
		}
		return updateSensorNames;
	}
	
	public static void resetUpdateSensorNames(){
		updateSensorNames = false;
	}
	
	public void initSensorNames(int x, int y){
		for(int i = 0; i < 16; i++){
			sensorNamesFields[i] = new JTextField();
			sensorNamesFields[i].setFont(font);
			sensorNamesLabels[i] = new JLabel("Sensor " + i + ":");
			sensorNamesLabels[i].setFont(font);
			sensorNamesFields[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateSensorNames = true;
				}
			});
			
			add(sensorNamesFields[i]);
			add(sensorNamesLabels[i]);
			if(i < 8){
				sensorNamesFields[i].setBounds(x+65, y+i*30, 100, 20);
				sensorNamesLabels[i].setBounds(x, y+i*30, 65, 20);	
			}else{
				sensorNamesFields[i].setBounds(x+65+180, y+(i-8)*30, 100, 20);
				sensorNamesLabels[i].setBounds(x+180, y+(i-8)*30, 65, 20);	
			}
				
		}
	}
}
