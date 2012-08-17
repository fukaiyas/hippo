package com.bugworm.coverage.view.command;

import javax.swing.JTabbedPane;
import javax.swing.tree.TreePath;

import com.bugworm.coverage.ClassCoverage;
import com.bugworm.coverage.view.ClassCoverageView;
import com.bugworm.coverage.view.ClassTreeNode;
import com.bugworm.coverage.view.Hippo;

public class ClassCoverageOpenCommand implements HippoCommand {

	public void execute(Hippo hippo) {

		//TODO 本当は複数対応したい
		TreePath path = hippo.projectView.classTree.getSelectionPath();
		if(path == null){
			return;
		}
		Object obj = path.getLastPathComponent();
		if(!(obj instanceof ClassTreeNode)){
			return;
		}
		ClassTreeNode node = (ClassTreeNode)obj;
		open(node.name, hippo.projectView.classTab, node.coverage);
	}

	public static void open(String name, JTabbedPane tab, ClassCoverage cov){

		int count = tab.getTabCount();
		String classname = cov.getSrcPath();
		for(int i = 0; i < count; i++){
			ClassCoverageView view = (ClassCoverageView)tab.getComponentAt(i);
			if(classname.equals(view.classCoverage.getSrcPath())){
				tab.setSelectedIndex(i);
				return;
			}
		}
		tab.addTab(name, new ClassCoverageView(cov));
		tab.setSelectedIndex(tab.getTabCount() - 1);
	}
}
