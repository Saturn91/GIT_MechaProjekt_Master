package com.EnergyHarvesting.Master.TestProject.gui;

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
}
