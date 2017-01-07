package com.EnergyHarvesting.Master.TestProject.gui.subPanel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.EnergyHarvesting.Master.TestProject.App;
import com.saturn91.saveToFile.SaveToFile;

import controler.Controller;
import logger.Log;

public class SettingPanel extends PanelComponent{

	private SettingPanel settingPanel;

	private JLabel titleSettings;
	private JCheckBox log;
	private JLabel logLabel;
	private JCheckBox logFile;
	private JLabel logFileLabel;
	private JTextField saveTime;
	private JLabel saveTimeLabel;

	//Clear Controller
	private JButton clearControllerBtn;
	private boolean clearControllerFlag = false;

	//Save Button
	private JButton saveDataBtn;
	private boolean saveDataFlag = false;

	//Filechooser
	private JButton loadBtn;
	private JFileChooser chooser = new JFileChooser();
	private String loadPath;
	private boolean load = false;

	//Record
	private JCheckBox recordBox;
	private JLabel recordLabel;
	private boolean recordFlag = false;
	private boolean recordState = false;

	private JButton clearLineInCMDBtn;
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
		settingPanel = this;		
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
		initRecord(20, 120);
		initClearCMDBtn(20, 145);
		initLoadBtn(20, 170);
		initSaveDataBtn(20, 195);
		initClearControllerBtn(20, 220);
		initFileChooser();
		initSensorNames(280, 20);		
	}

	@Override
	public void update() {

		if(clearControllerFlag){
			App.clearController();
			Log.printInfoln("cleared Data", true);
			clearControllerFlag = false;
		}

		if(load){
			load();
			load = false;
		}
		
		if(saveDataFlag){
			App.save();
			saveDataFlag = false;
		}

		if(saveTimeFlag){
			updateSaveTime();
			saveTimeFlag = false;
		}

		if(recordFlag){
			App.setRecord(recordState);
			if(recordState){
				Log.printInfoln("recording = true", true);
			}else{
				Log.printInfoln("recording = false", true);
			}
			recordFlag = false;
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

	private void initLoadBtn(int x, int y){
		loadBtn = new JButton("Load Data");
		loadBtn.setFont(font);
		loadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = chooser.showOpenDialog(settingPanel);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					loadPath = selectedFile.getPath();
					load = true;
				}				
			}
		});
		add(loadBtn);
		loadBtn.setBounds(x, y, 110, 20);
	}

	private void initFileChooser(){	
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Load Old Data");
		chooser.setFileFilter(new FileNameExtensionFilter("Old Sensordata", "data"));
		chooser.setBounds(20, 20, 500, 300);
	}

	private void initClearControllerBtn(int x, int y){
		clearControllerBtn = new JButton("Clear Graph");
		clearControllerBtn.setFont(font);
		clearControllerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearControllerFlag = true;				
			}
		});
		add(clearControllerBtn);
		clearControllerBtn.setBounds(x, y, 110, 20);
	}

	private void initSaveDataBtn(int x, int y){
		saveDataBtn = new JButton("Save Data");
		saveDataBtn.setFont(font);
		saveDataBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveDataFlag = true;				
			}
		});
		add(saveDataBtn);
		saveDataBtn.setBounds(x, y, 110, 20);
	}

	private void initClearCMDBtn(int x, int y){
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

	private void initRecord(int x, int y){
		recordBox = new JCheckBox();
		recordBox.setFont(font);
		recordLabel = new JLabel("record: ");
		recordLabel.setFont(font);
		add(recordLabel);
		add(recordBox);
		recordBox.setBounds(x+x_Devider1+10, y, 20, 20);
		recordLabel.setBounds(x, y, x_Devider1, 20);

		recordBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
					recordFlag = true;
					recordState = true;
				} else {//checkbox has been deselected
					recordFlag = true;
					recordState = false;
				};				
			}
		});
	}

	public boolean deleteCMDLine(){
		return deleteLineInCMD;
	}

	public void resetDelCMDLine(){
		deleteLineInCMD = false;
	}

	public void load(){
		Log.printInfoln("Start load Data from: " + loadPath, true);
		Controller loadController = null;
		boolean loadError = false;
		try {
			long startTime = System.currentTimeMillis();
			while(loadController == null){
				loadController = (Controller) SaveToFile.getDataFromBinaryFile(loadPath);
				if(System.currentTimeMillis()-startTime >= 2000){
					Log.printErrorln("Waitetd to long to load: "+ loadPath);
					break;
				}
			}
			Log.printInfoln("successfully loaded Data from: " + loadPath, true);
		} catch (Exception e2) {
			loadError = true;
		}

		if(loadController != null){
			App.setController(loadController);
		}else{
			loadError = true;
		}			

		if(loadError){
			Log.printErrorln("not able to load: " + loadPath);
		}
	}

	public static String[] getSensorNames(){
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
