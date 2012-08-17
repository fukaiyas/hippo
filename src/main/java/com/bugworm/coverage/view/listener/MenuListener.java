package com.bugworm.coverage.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import com.bugworm.coverage.view.Hippo;
import com.bugworm.coverage.view.command.ClassCoverageCloseCommand;
import com.bugworm.coverage.view.command.ClassCoverageOpenCommand;
import com.bugworm.coverage.view.command.ConnectionListenCommand;
import com.bugworm.coverage.view.command.HippoCommand;
import com.bugworm.coverage.view.command.NewProjectCommand;
import com.bugworm.coverage.view.command.ProjectOpenCommand;
import com.bugworm.coverage.view.command.ProjectSaveCommand;

public class MenuListener implements ActionListener {

	public static final String PROJECT_NEW = "project.new";

	public static final String PROJECT_OPEN = "project.open";

	public static final String PROJECT_SAVE = "project.save";

	public static final String PROJECT_CLOSE = "project.close";

	public static final String FILE_OPEN = "file.open";

	public static final String FILE_CLOSE = "file.close";

	public static final String CONNECTION_LISTEN = "connection.listen";

	public static final String CONNECTION_CONNECT = "connection.connect";

	private final Hippo hippo;

	private final Map<String, HippoCommand> commands = createCommandMap();

	private static Map<String, HippoCommand> createCommandMap(){

		Map<String, HippoCommand> map = new HashMap<String, HippoCommand>();
		map.put(PROJECT_NEW, new NewProjectCommand());
		map.put(PROJECT_OPEN, new ProjectOpenCommand());
		map.put(PROJECT_SAVE, new ProjectSaveCommand());
		map.put(PROJECT_CLOSE, null);
		map.put(FILE_OPEN, new ClassCoverageOpenCommand());
		map.put(FILE_CLOSE, new ClassCoverageCloseCommand());
		map.put(CONNECTION_LISTEN, new ConnectionListenCommand());
		map.put(CONNECTION_CONNECT, null);
		return map;
	}

	public MenuListener(Hippo hp){

		hippo = hp;
	}

	public void actionPerformed(ActionEvent ae) {

		HippoCommand comm = commands.get(ae.getActionCommand());
		if(comm != null){
			comm.execute(hippo);
		}else{
			System.err.println("bad menu command : " + ae.getActionCommand());
		}
	}
}
