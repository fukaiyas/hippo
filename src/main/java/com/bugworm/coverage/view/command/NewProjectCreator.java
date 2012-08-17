package com.bugworm.coverage.view.command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.bugworm.coverage.view.Hippo;

public class NewProjectCreator implements ActionListener{

	public final JTextField projectDir = new JTextField();

	public final JTextField sourceDir = new JTextField();

	public final JTextField classDir = new JTextField();

	private final JDialog dialog;

	private final Hippo hippo;

	public NewProjectCreator(JDialog dl, Hippo hp){

		dialog = dl;
		hippo = hp;
	}

	public void actionPerformed(ActionEvent ae) {

		String com = ae.getActionCommand();
		if("dialog.cancel".equals(com)){
			dialog.setVisible(false);

		}else if("dialog.ok".equals(com)){
			File project = new File(projectDir.getText());
			File src = new File(sourceDir.getText());
			File clazz = new File(classDir.getText());
			if(!project.isDirectory() || !src.isDirectory() || !clazz.isDirectory()){
				JOptionPane.showMessageDialog(hippo.mainWindow,
						"存在するディレクトリを指定してください", "エラー", JOptionPane.ERROR_MESSAGE);

			}else{
				try{
					hippo.createNewProject(project, src, clazz);
					dialog.setVisible(false);

				}catch(Exception e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(hippo.mainWindow,
							"プロジェクトの生成に失敗しました", "エラー", JOptionPane.ERROR_MESSAGE);
				}
				hippo.mainWindow.repaint();
			}
		}
	}
}
