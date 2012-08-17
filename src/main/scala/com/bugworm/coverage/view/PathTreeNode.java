package com.bugworm.coverage.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

public class PathTreeNode implements TreeNode {

	private final String name;

	private final TreeNode parent;

	private final List<TreeNode> children = new ArrayList<TreeNode>();

	public PathTreeNode(TreeNode pr, String nm){
		parent = pr;
		name = nm;
	}

	public Enumeration<TreeNode> children() {
		return Collections.enumeration(children);
	}

	public boolean getAllowsChildren() {
		return true;
	}

	public TreeNode getChildAt(int childIndex) {
		return children.get(childIndex);
	}

	public int getChildCount() {
		return children.size();
	}

	public int getIndex(TreeNode node) {
		return children.indexOf(node);
	}

	public TreeNode getParent() {
		return parent;
	}

	public boolean isLeaf() {
		return false;
	}

	public void addChild(TreeNode node){
		children.add(node);
	}

	@Override
	public String toString(){
		return name;
	}
}
