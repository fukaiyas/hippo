package com.bugworm.coverage.server;

import java.io.IOException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.bugworm.coverage.ProjectCoverage;
import com.bugworm.coverage.jdi.DebugEventHandler;
import com.bugworm.coverage.jdi.JdiUtil;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.ListeningConnector;

@SuppressWarnings("serial")
public class HippoServer extends UnicastRemoteObject implements HippoRemote{

	private static HippoServer instance;

	private DebugEventHandler handler = null;

	public HippoServer()throws RemoteException{
	}

	public void listen(ProjectCoverage cov, int port)throws IOException, IllegalConnectorArgumentsException{

		if(handler != null){
			throw new IOException("Already listening.");
		}
		ListeningConnector liscon = JdiUtil.getConnector();
		handler = new DebugEventHandler(liscon, port, cov);
		handler.start();
	}

	public void terminate(){

		if(handler != null){
			handler.terminate();
		}
		try{
			unexportObject(this, true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
