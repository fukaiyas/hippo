package com.bugworm.coverage.jdi;

import java.util.List;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.ListeningConnector;

public class JdiUtil {

	public static ListeningConnector getConnector(){

		List<Connector> connectors = Bootstrap.virtualMachineManager().allConnectors();
		for(Connector con: connectors){
            if (con.name().equals("com.sun.jdi.SocketListen")) {
            	return (ListeningConnector)con;
            }
		}
		throw new IllegalStateException("Can't find ListeningConnector.");
	}
}
