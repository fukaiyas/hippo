package com.bugworm.coverage.impl;

import java.util.HashSet;
import java.util.Set;

import com.bugworm.coverage.Branch;
import com.bugworm.coverage.BranchIndex;

public class BranchImpl implements Branch {

	private final Set<Integer> details = new HashSet<Integer>();

	private final Set<BranchIndex> conditions = new HashSet<BranchIndex>();

	private final Set<BranchIndex> executed = new HashSet<BranchIndex>();

	private final int linePosition;

	public BranchImpl(int line){
		linePosition = line;
	}

	public void addConditionPosition(int detailIndex, int pos, int index){
		conditions.add(new BranchIndexImpl(linePosition, detailIndex, pos, index));
		details.add(detailIndex);
	}

	public synchronized void branched(int detailIndex, int pos, int index){
		BranchIndex br = new BranchIndexImpl(linePosition, detailIndex, pos, index);
		if(conditions.contains(br)){
			executed.add(br);
		}else{
			System.out.println("else! ; " + br);
		}
	}

	public int getNumberOfConditions() {
		return conditions.size();
	}

	public int getNumberOfExecutedConditions() {
		return executed.size();
	}

	public int getLineNumberPosition() {
		return linePosition;
	}

	public synchronized Set<BranchIndex> getExecutedConditions() {
		return new HashSet<BranchIndex>(executed);
	}

	public Set<BranchIndex> getConditions(){
		return conditions;
	}

	public boolean existsDetailBranch(int detailIndex){
		return details.contains(detailIndex);
	}

	@Override
	public String toString(){

		StringBuilder builder = new StringBuilder("[ Branch and Condition ]\n");
		builder.append("  Position   : ").append(linePosition).append("\n");
		builder.append("  BranchLines: ").append(conditions).append("\n");
		builder.append("  Executed   : ").append(executed).append("\n");
		return builder.toString();
	}
}
