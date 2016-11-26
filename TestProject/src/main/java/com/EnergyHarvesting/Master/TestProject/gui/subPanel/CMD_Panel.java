package com.EnergyHarvesting.Master.TestProject.gui.subPanel;

import java.awt.Color;
import java.awt.TextArea;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class CMD_Panel extends PanelComponent{
	
	private JTextPane cmd_Output;
	private JLabel title;

	public CMD_Panel(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void init() {
		setBounds();
		setLayout(null);
		title = new JLabel("Commandline Output");
		title.setBounds(0,0, getWidth(), 18);
		cmd_Output = new JTextPane();
		cmd_Output.setBounds(0, 20, getWidth(), getHeight()-25);
		cmd_Output.setBackground(Color.black);
		cmd_Output.setFocusable(false);
		add(cmd_Output);
		add(title);
	}

	@Override
	public void update() {
		
	}
	
	public void addLine(String text){
		appendToPane(cmd_Output, text, Color.WHITE);
	}
	
	public void addInfoLine(String info){
		appendToPane(cmd_Output, info, Color.YELLOW);
	}
	
	public void addErrorLine(String error){
		appendToPane(cmd_Output, error, Color.RED);
	}
	
	private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
}
