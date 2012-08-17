package com.bugworm.coverage.view.command;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;

import com.bugworm.coverage.view.Hippo;
import com.bugworm.coverage.view.ViewUtil;

public class NewProjectCommand implements HippoCommand{

	public void execute(Hippo hippo) {

		JDialog dialog = new JDialog(hippo.mainWindow, "New Project", true);
		Box root = new Box(BoxLayout.Y_AXIS);
		NewProjectCreator sel = new NewProjectCreator(dialog, hippo);
		root.add(ViewUtil.createDirectorySelectPanel("Project", sel.projectDir, dialog));
		root.add(ViewUtil.createDirectorySelectPanel("Source", sel.sourceDir, dialog));
		root.add(ViewUtil.createDirectorySelectPanel("Class", sel.classDir, dialog));
		root.add(ViewUtil.createOkCancelPanel(sel));
		dialog.add(root);
		dialog.pack();
		dialog.setVisible(true);
	}
}
