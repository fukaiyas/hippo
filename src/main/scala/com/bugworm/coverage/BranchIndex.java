package com.bugworm.coverage;

/**
 * BranchIndexは分岐を表すインタフェース。
 */
public interface BranchIndex {

	void setPositionLine(int line);

	int getPositionLine();

	void setPositionIndex(int i);

	int getPositionIndex();

	void setBranchedLine(int line);

	int getBranchedLine();

	void setBranchedIndex(int i);

	int getBranchedIndex();
}
