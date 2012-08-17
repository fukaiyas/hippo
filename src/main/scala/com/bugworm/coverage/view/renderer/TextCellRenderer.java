package com.bugworm.coverage.view.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class TextCellRenderer extends DefaultTableCellRenderer {

	private final static Font NORMAL = new Font(Font.MONOSPACED, Font.PLAIN, 12);

	private final static Color BACK = new Color(240, 255, 255);

	public TextCellRenderer(int alignment){

		setFont(NORMAL);
		setHorizontalAlignment(alignment);
	}

	@Override
	public Component getTableCellRendererComponent(
			JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){

		setText(value.toString());
		setBackground(isSelected ? Color.DARK_GRAY: BACK);
		setForeground(isSelected ? Color.WHITE: Color.BLACK);
		return this;
	}
}
