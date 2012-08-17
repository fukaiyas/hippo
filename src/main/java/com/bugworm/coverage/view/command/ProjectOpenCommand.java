package com.bugworm.coverage.view.command;

import javax.swing.JFileChooser;

import com.bugworm.coverage.view.Hippo;

public class ProjectOpenCommand implements HippoCommand {

	public void execute(Hippo hippo) {

		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int res = chooser.showOpenDialog(hippo.mainWindow);
		if(res == JFileChooser.APPROVE_OPTION){
			try{
				hippo.loadProject(chooser.getSelectedFile());
			}catch(Exception e){
				//FIXME エラー処理
				e.printStackTrace();
			}
			hippo.mainWindow.repaint();
		}
	}
}
