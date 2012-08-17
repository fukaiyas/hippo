package com.bugworm.coverage.view;

import java.util.TimerTask;

import javax.swing.JFrame;

public class ViewUpdateThread extends TimerTask {

	private final JFrame targetWindow;

	public ViewUpdateThread(JFrame target){
		targetWindow = target;
	}

	@Override
	public void run(){
		targetWindow.repaint();
	}
}
