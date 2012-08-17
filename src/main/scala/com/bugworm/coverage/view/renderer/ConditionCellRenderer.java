package com.bugworm.coverage.view.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bugworm.coverage.view.Fraction;

@SuppressWarnings("serial")
public class ConditionCellRenderer extends DefaultTableCellRenderer {

	private final static Font NORMAL = new Font(Font.MONOSPACED, Font.PLAIN, 12);

	public ConditionCellRenderer(){

		setFont(NORMAL);
		setHorizontalAlignment(JLabel.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(
			JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){

		if(value instanceof Fraction){
			Fraction frac = (Fraction)value;
			if(frac.numerator == frac.denominator){
				setBackground(isSelected ? Color.BLUE : Color.GREEN);
			}else if(frac.numerator > 0){
				setBackground(isSelected ? Color.MAGENTA : Color.YELLOW);
			}else{
				setBackground(isSelected ? Color.ORANGE : Color.RED);
			}
		}else{
			setBackground(isSelected ? Color.DARK_GRAY: Color.LIGHT_GRAY);
		}
		setText(value.toString());
		return this;
	}
}
