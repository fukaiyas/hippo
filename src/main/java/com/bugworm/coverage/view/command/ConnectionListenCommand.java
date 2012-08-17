package com.bugworm.coverage.view.command;

import com.bugworm.coverage.server.HippoServer;
import com.bugworm.coverage.view.Hippo;

public class ConnectionListenCommand implements HippoCommand {

	public void execute(final Hippo hippo) {

		new Thread(){
			public void run(){
				try{
					new HippoServer().listen(hippo.projectView.getProject(), 10034);
//					hippo.connectionManager.listen();
				}catch(Exception e){
					//TODO
					e.printStackTrace();
				}
			}
		}.start();
	}
}
