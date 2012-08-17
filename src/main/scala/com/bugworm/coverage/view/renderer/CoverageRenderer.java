package com.bugworm.coverage.view.renderer;

import static com.bugworm.coverage.view.CoverageTableModel.C0_EXECUTED;
import static com.bugworm.coverage.view.CoverageTableModel.C0_NOT_EXECUTED;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class CoverageRenderer extends DefaultTableCellRenderer {

	private final static Font NORMAL = new Font(Font.MONOSPACED, Font.PLAIN, 12);

	public CoverageRenderer(){

		setFont(NORMAL);
		setHorizontalAlignment(JLabel.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(
			JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){

		setText(value.toString());
		if(C0_EXECUTED.equals(value)){
			setBackground(isSelected ? Color.BLUE : Color.GREEN);
		}else if(C0_NOT_EXECUTED.equals(value)){
			setBackground(isSelected ? Color.ORANGE : Color.RED);
		}else{
			setBackground(isSelected ? Color.DARK_GRAY: Color.LIGHT_GRAY);
		}
		return this;
	}
}
