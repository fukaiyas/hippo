package com.bugworm.coverage.view.command;

import com.bugworm.coverage.view.Hippo;

public class ProjectSaveCommand implements HippoCommand {

	public void execute(Hippo hippo) {

		try{
			hippo.saveProject();
		}catch(Exception e){
			//FIXME エラー処理
			e.printStackTrace();
		}
	}
}
