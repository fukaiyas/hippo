package com.bugworm.coverage.view.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

public class DirectorySelector implements ActionListener {

	private final Component parent;

	private final JTextField textField;

	public DirectorySelector(JDialog dl, JTextField tf){

		parent = dl;
		textField = tf;
	}

	public void actionPerformed(ActionEvent e) {

		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int res = chooser.showOpenDialog(parent);
		if(res == JFileChooser.APPROVE_OPTION){
			File file = chooser.getSelectedFile();
			if(file.isDirectory()){
				textField.setText(file.getAbsolutePath());
			}
		}
	}
}
