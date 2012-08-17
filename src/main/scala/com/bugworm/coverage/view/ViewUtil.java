package com.bugworm.coverage.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bugworm.coverage.CoverageInfo;
import com.bugworm.coverage.view.listener.DirectorySelector;

public class ViewUtil {

	public static final Font LABEL_FONT = new Font(Font.MONOSPACED, Font.BOLD, 12);

	public static Box createOkCancelPanel(ActionListener al){

		Box pan = new Box(BoxLayout.X_AXIS);
		pan.setPreferredSize(new Dimension(512, 24));
		pan.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		pan.add(Box.createGlue());
		pan.add(createButton("OK", al, "dialog.ok"));
		pan.add(Box.createHorizontalStrut(2));
		pan.add(createButton("Cancel", al, "dialog.cancel"));
		return pan;
	}

	public static JButton createButton(String text, ActionListener al, String command){

		JButton button = new JButton(text);
		button.addActionListener(al);
		if(command != null){
			button.setActionCommand(command);
		}
		return button;
	}

	public static JPanel createDirectorySelectPanel(String title, JTextField field, JDialog dialog){

		JPanel pan = new JPanel(new BorderLayout(2, 2));
		pan.setBorder(BorderFactory.createEmptyBorder(2, 2, 0, 2));
		pan.setPreferredSize(new Dimension(512, 24));
		JLabel lab = new JLabel(title);
		lab.setPreferredSize(new Dimension(64, 24));
		pan.add(lab, BorderLayout.WEST);
		pan.add(field, BorderLayout.CENTER);
		JButton button = new JButton("Select");
		button.addActionListener(new DirectorySelector(dialog, field));
		pan.add(button, BorderLayout.EAST);
		return pan;
	}

	public static JPanel createPanel(String title, CoverageInfo info){
	
		JLabel lab = new CoverageLabel(title, info);
		lab.setPreferredSize(new Dimension(180, 16));
		lab.setFont(LABEL_FONT);
	
		JPanel pan = new JPanel(new BorderLayout(), true);
		pan.setPreferredSize(new Dimension(0, 16));
		pan.add(lab, BorderLayout.WEST);
		pan.add(new CoverageGraph(info));
		pan.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		return pan;
	}
}
