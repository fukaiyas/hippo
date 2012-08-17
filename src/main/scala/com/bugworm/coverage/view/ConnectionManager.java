package com.bugworm.coverage.view;

import java.io.IOException;

import com.bugworm.coverage.jdi.DebugEventHandler;
import com.bugworm.coverage.jdi.JdiUtil;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.ListeningConnector;

public class ConnectionManager {

	private final Hippo hippo;

	private DebugEventHandler handler;

	public ConnectionManager(Hippo h){

		hippo = h;
	}

	public void listen()throws IOException, IllegalConnectorArgumentsException{

		if(handler != null && handler.isActive()){
			throw new IOException("Already listening.");
		}
		ListeningConnector listenCon = JdiUtil.getConnector();
		handler = new DebugEventHandler(listenCon, 10034, hippo.projectView.getProject());
		handler.start();
		System.out.println("Start handler.");
	}
}
