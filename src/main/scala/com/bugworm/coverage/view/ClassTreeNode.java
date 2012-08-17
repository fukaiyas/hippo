package com.bugworm.coverage.view;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import com.bugworm.coverage.ClassCoverage;

public class ClassTreeNode implements TreeNode {

	private static final Enumeration<Object> EMPTY_ENUM = new Vector<Object>().elements();

	public final ClassCoverage coverage;

	public final String name;

	private final TreeNode parent;

	public ClassTreeNode(TreeNode pr, ClassCoverage cov){
		parent = pr;
		coverage = cov;
		String srcName = cov.getSrcPath();
		int pos = srcName.lastIndexOf('/');
		if(pos > 0){
			srcName = srcName.substring(pos + 1);
		}
		name = srcName;
	}

	public Enumeration<Object> children() {
		return EMPTY_ENUM;
	}

	public boolean getAllowsChildren() {
		return false;
	}

	public TreeNode getChildAt(int childIndex) {
		return null;
	}

	public int getChildCount() {
		return 0;
	}

	public int getIndex(TreeNode node) {
		return -1;
	}

	public TreeNode getParent() {
		return parent;
	}

	public boolean isLeaf() {
		return true;
	}

	@Override
	public String toString(){
		//TODO できれば桁が変わらないほうがよい
		//TODO パーセンテージのほうがよいかも？
		//TODO ていうかRenderer作るのかな．．．
		StringBuilder str = new StringBuilder(name).append(" ");
		str.append(coverage.getNumberOfExecutedLines()).append("/");
		str.append(coverage.getNumberOfAvailableLines()).append(" ");
		str.append(coverage.getNumberOfExecutedConditions()).append("/");
		str.append(coverage.getNumberOfConditions());
		return str.toString();
	}
}
