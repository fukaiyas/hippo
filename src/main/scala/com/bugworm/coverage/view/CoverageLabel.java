package com.bugworm.coverage.view;

import java.awt.Graphics;

import javax.swing.JLabel;

import com.bugworm.coverage.CoverageInfo;

@SuppressWarnings("serial")
public class CoverageLabel extends JLabel {

	private static final String FORMAT = " %3d%% %6d/%6d";

	private final String label;

	private final CoverageInfo coverageInfo;

	public CoverageLabel(String t, CoverageInfo info){

		super("---", JLabel.CENTER);
		label = t + FORMAT;
		coverageInfo = info;
	}

	@Override
	public void paintComponent(Graphics g){

		int den = coverageInfo.getDenominator();
		if(den != 0){
			int num = coverageInfo.getNumerator();
			setText(String.format(label, num * 100 / den, num, den));
		}
		super.paintComponent(g);
	}
}
