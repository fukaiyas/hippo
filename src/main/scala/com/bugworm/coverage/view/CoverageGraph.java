package com.bugworm.coverage.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import com.bugworm.coverage.CoverageInfo;

@SuppressWarnings("serial")
public class CoverageGraph extends JComponent {

	private static final Color BASE_COLOR = Color.RED;

	private static final Color DRAW_COLOR = Color.GREEN;

	private static final Color DISABLE_COLOR = Color.LIGHT_GRAY;

	private static final Color FRAME_COLOR = Color.BLACK;

	private static final Dimension SIZE = new Dimension(0, 16);

	protected final CoverageInfo coverageInfo;

	public CoverageGraph(CoverageInfo info){

		coverageInfo = info;
		
		setMaximumSize(SIZE);
		setMinimumSize(SIZE);
		setPreferredSize(SIZE);
	}

	@Override
	public void paintComponent(Graphics g){

		int w = getWidth();
		int h = getHeight();
		int den = coverageInfo.getDenominator();
		g.setColor(den == 0 ? DISABLE_COLOR : BASE_COLOR);
		g.fillRect(0, 0, w, h);
		if(den != 0){
			g.setColor(DRAW_COLOR);
			g.fillRect(0, 0, w * coverageInfo.getNumerator() / den, h);
		}
		g.setColor(FRAME_COLOR);
		g.drawRect(0, 0, w - 1, h - 1);
	}
}
