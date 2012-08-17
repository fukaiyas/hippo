package com.bugworm.coverage.view.command;

import javax.swing.JTabbedPane;

import com.bugworm.coverage.view.Hippo;

public class ClassCoverageCloseCommand implements HippoCommand {

	public void execute(Hippo hippo) {

		JTabbedPane tab = hippo.projectView.classTab;
		int index = tab.getSelectedIndex();
		if(index >= 0){
			tab.remove(index);
		}
	}
}
