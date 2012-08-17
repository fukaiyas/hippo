package com.bugworm.coverage.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HippoRemote extends Remote {

	String BIND_NAME = "";

	void terminate()throws RemoteException;
}
