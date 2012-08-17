package com.bugworm.coverage.view;

import static com.bugworm.coverage.view.listener.MenuListener.CONNECTION_LISTEN;
import static com.bugworm.coverage.view.listener.MenuListener.FILE_CLOSE;
import static com.bugworm.coverage.view.listener.MenuListener.FILE_OPEN;
import static com.bugworm.coverage.view.listener.MenuListener.PROJECT_CLOSE;
import static com.bugworm.coverage.view.listener.MenuListener.PROJECT_NEW;
import static com.bugworm.coverage.view.listener.MenuListener.PROJECT_OPEN;
import static com.bugworm.coverage.view.listener.MenuListener.PROJECT_SAVE;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import com.bugworm.coverage.ProjectCoverage;
import com.bugworm.coverage.util.CoverageResult;
import com.bugworm.coverage.util.SourceFileFilter;
import com.bugworm.coverage.util.ProjectConfig;
import com.bugworm.coverage.util.Util;
import com.bugworm.coverage.view.listener.MenuListener;

public class Hippo extends WindowAdapter implements Runnable{

	public final JFrame mainWindow = new JFrame("Hippo");

	public final ProjectCoverageView projectView = new ProjectCoverageView();

	//public final ConnectionManager connectionManager = new ConnectionManager(this);

	public ProjectConfig config;

	private final  Map<String, CoverageResult> savedResult = new HashMap<String, CoverageResult>();

	private File projectDirectory;

	private Timer timer = new Timer();

	//FIXME Swing系処理とファイルIO系処理を分けて、スレッドを分離する。
	//FIXME ファイルIOを管理する独立スレッドを作ったほうがよさそう(シングルスレッドでがんばれるように)
	//FIXME Impl系クラスの中身を、SwingスレッドとIOスレッドで同時にアクセスすることになりそうなので注意
	public void createNewProject(File project, File src, File clazz)throws IOException{

		projectDirectory = project;
		config = new ProjectConfig();
		config.setSourceDir(src.getAbsolutePath());
		config.setClassDir(clazz.getAbsolutePath());
		ProjectCoverage cov = Util.getProjectCoverage(clazz, src, new SourceFileFilter());
		projectView.setProject(cov);

		//TODO この辺もUtil系に移動・Viewですべき処理と、「カバレッジプロジェクト」としてすべき処理をきちんと分ける
		File xml = new File(projectDirectory, "project.xml");
		XMLEncoder en = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(xml)));
		try{
			en.writeObject(config);
		}finally{
			en.close();
		}
		saveProject();
	}

	public void saveProject()throws IOException{

		Util.saveClassesResult(projectDirectory, projectView.getProject(), savedResult);
	}

	public void loadProject(File project)throws IOException{

		projectDirectory = project;
		//TODO この辺もUtil系に移動
		File xml = new File(projectDirectory, "project.xml");
		XMLDecoder de = new XMLDecoder(new BufferedInputStream(new FileInputStream(xml)));
		try{
			config = (ProjectConfig)de.readObject();
		}finally{
			de.close();
		}
		File clazz = new File(config.getClassDir());
		File src = new File(config.getSourceDir());
		ProjectCoverage cov = Util.getProjectCoverage(clazz, src, new SourceFileFilter());
		Util.loadClassesResult(project, cov, savedResult);
		projectView.setProject(cov);
	}

	public void run() {

		//hippopotamus
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.getContentPane().setLayout(new BorderLayout());
		mainWindow.setSize(800, 480);
		mainWindow.getContentPane().add(projectView);
		mainWindow.setJMenuBar(createMenu());
		mainWindow.addWindowListener(this);
		mainWindow.setVisible(true);
		timer.schedule(new ViewUpdateThread(mainWindow), 100, 100);
	}

	private JMenuBar createMenu(){

		MenuListener listener = new MenuListener(this);
		JMenuBar menubar = new JMenuBar();
		menubar.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		JMenu project = new JMenu("Project");
		project.add(createMenu("New", PROJECT_NEW, listener, KeyEvent.VK_N, InputEvent.CTRL_MASK));
		project.add(createMenu("Open", PROJECT_OPEN, listener));
		project.add(createMenu("Save", PROJECT_SAVE, listener, KeyEvent.VK_S, InputEvent.CTRL_MASK));
		project.add(createMenu("Close", PROJECT_CLOSE, listener));
		menubar.add(project);

		JMenu file = new JMenu("File");
		file.add(createMenu("Open", FILE_OPEN, listener));
		file.add(createMenu("Close", FILE_CLOSE, listener, KeyEvent.VK_F4, 0));
		menubar.add(file);

		JMenu connection = new JMenu("Connection");
		connection.add(createMenu("Listen", CONNECTION_LISTEN, listener));
		menubar.add(connection);
		return menubar;
	}

	private static JMenuItem createMenu(String text, String command, ActionListener listener, int key, int mod){

		JMenuItem menuitem = createMenu(text, command, listener);
		menuitem.setAccelerator(KeyStroke.getKeyStroke(key, mod));
		return menuitem;
	}

	private static JMenuItem createMenu(String text, String command, ActionListener listener){

		JMenuItem menuitem = new JMenuItem(text);
		menuitem.setActionCommand(command);
		menuitem.addActionListener(listener);
		return menuitem;
	}

	@Override
	public void windowClosing(WindowEvent we){
		//TODO クローズ時のいろいろを実装したほうがいい(セーブチェックとか)
		timer.cancel();
	}

	public static void main(String[] args){

		SwingUtilities.invokeLater(new Hippo());
	}
}
