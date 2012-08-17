package com.bugworm.coverage.jdi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.bugworm.coverage.ClassCoverage;
import com.bugworm.coverage.ProjectCoverage;
import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Location;
import com.sun.jdi.Method;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.ListeningConnector;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventIterator;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;

public class DebugEventHandler extends Thread {

	private static final String TYPE_KEY = "BP_TYPE";
	private static final String LINE = "LINE";
	private static final String BRANCH = "BRANCH";

	private final Map<ThreadReference, int[]> currentBranch =
			new HashMap<ThreadReference, int[]>();

	private final ProjectCoverage project;

	private final ListeningConnector connector;

	private final int port;

	private EventRequestManager eventRequestManager;

	private volatile boolean active = true;

	/**
	 * ProjectCoverageのすべてのソースを対象にするコンストラクタ。
	 * 
	 * @param con コネクタ
	 * @param p プロジェクトカバレジ
	 */
	public DebugEventHandler(ListeningConnector con, int pt, ProjectCoverage p){

		project = p;
		connector = con;
		port = pt;
	}

	@Override
	public void run(){

		Map<String, Connector.Argument> map = connector.defaultArguments();
		Connector.Argument arg = map.get("port");
		arg.setValue(String.valueOf(port));

		try{
			EventQueue queue = getQueue(map);
			while(active){
				try{
					EventSet eventSet = queue.remove();
					EventIterator it = eventSet.eventIterator();
					while(it.hasNext()){
						Event event = it.nextEvent();
						handleEvent(event);
					}
					eventSet.resume();

				}catch(VMDisconnectedException vdEx){
					System.out.println("VM disconnected.");
					//次のコネクトを待つ
					queue = getQueue(map);

				}catch(InterruptedException e){}
			}

		}catch(Exception e){
			e.printStackTrace();

		}finally{
			active = false;
		}
	}

	private EventQueue getQueue(Map<String, Connector.Argument> map)
			throws IOException, IllegalConnectorArgumentsException{

		VirtualMachine vm = connector.accept(map);
		eventRequestManager = vm.eventRequestManager();
		ClassPrepareRequest cpreq = eventRequestManager.createClassPrepareRequest();
		cpreq.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
		cpreq.enable();
		return vm.eventQueue();
	}

	private void handleEvent(Event event)throws Exception{

		if(event instanceof ClassPrepareEvent){
			ClassPrepareEvent cleve = (ClassPrepareEvent)event;
			ReferenceType type = cleve.referenceType();
			ClassCoverage cov = null;
			try {
				String srcpath = type.sourcePaths(null).get(0).replace('\\', '/');
				cov = project.getClassCoverageBySrcPath(srcpath);
			}catch (AbsentInformationException e){
				return;
			}

			//カバレジ完了のクラスならやらなくていい
			if(cov == null || cov.isCompleted()){
				return;
			}
			System.out.println("ロード：" + type.sourceName());
			for(Location lineloc : type.allLineLocations()){
				createBreakPoint(lineloc, LINE);
			}
			for(Method m : type.allMethods()){
				int size = m.bytecodes().length;
				for(int i = 0; i < size; i++){
					Location loc = m.locationOfCodeIndex(i);
					if(loc != null && cov.existsBranchIndex(loc.lineNumber(), (int)loc.codeIndex())){
						createBreakPoint(loc, BRANCH);
					}
				}
			}

		}else if(event instanceof BreakpointEvent){
			BreakpointEvent breve = (BreakpointEvent)event;
			Location loc = breve.location();
			String path = loc.sourcePath().replace('\\', '/');
			ClassCoverage cov = project.getClassCoverageBySrcPath(path);
			System.out.println(path);
			if(cov != null){
				BreakpointRequest req = (BreakpointRequest)breve.request();
				ThreadReference th = breve.thread();
				int line = loc.lineNumber();
				Object type = req.getProperty(TYPE_KEY);
				if(LINE.equals(type)){
					cov.executed(line);
					req.disable();

				}else if(BRANCH.equals(type)){
					int[] org = currentBranch.remove(th);
					int codein = (int)loc.codeIndex();
					if(org != null){
						System.out.println("  EXEC:" + line + ":" + codein);
						cov.getBranch(org[0]).branched(org[1], line, codein);
					}
					if(cov.isCompleted()){
						System.out.println("COMPLETE:" + cov.getSrcPath());
						req.disable();

					}else if(cov.existsDetailBranch(line, codein)){
						System.out.println("  PUT:" + line + ":" + codein);
						currentBranch.put(th, new int[]{line, codein});
					}
				}
			}
		}
	}

	public void createBreakPoint(Location lc, String type){

		if(lc == null){
			System.out.println("Location is null.");
			return;
		}
		BreakpointRequest breq = eventRequestManager.createBreakpointRequest(lc);
		breq.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
		breq.putProperty(TYPE_KEY, type);
		breq.enable();
	}

	public void terminate(){
		active = false;
	}
	public boolean isActive(){
		return active;
	}
}
