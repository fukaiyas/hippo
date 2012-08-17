package com.bugworm.coverage.view.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import com.bugworm.coverage.view.ClassTreeNode;
import com.bugworm.coverage.view.ProjectCoverageView;
import com.bugworm.coverage.view.command.ClassCoverageOpenCommand;

public class TreeMouseAdapter extends MouseAdapter {

	private final ProjectCoverageView projectView;

	public TreeMouseAdapter(ProjectCoverageView pv){
		projectView = pv;
	}

	@Override
	public void mouseClicked(MouseEvent me){

		if(me.getClickCount() == 2){
			JTree tree = (JTree)me.getSource();
			TreePath path = tree.getPathForLocation(me.getX(), me.getY());
			if(path != null){
				Object obj = path.getLastPathComponent();
				if(obj instanceof ClassTreeNode){
					ClassTreeNode node = (ClassTreeNode)obj;
					ClassCoverageOpenCommand.open(node.name, projectView.classTab, node.coverage);
				}else{
					//TODO パッケージまとめてとか？
				}
			}
		}
	}
}
