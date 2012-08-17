package com.bugworm.coverage.impl;

import com.bugworm.coverage.BranchIndex;

public class BranchIndexImpl implements BranchIndex{
//TODO 実行用と保存用で共用しているので、分けたほうがよさそう。(実装を切り替えたいときに不便な気がする)

	/** 分岐元の行番号 */
	private int positionLine;

	/** 分岐元の実行バイナリインデックス */
	private int positionIndex;

	/** 分岐先の行番号 */
	private int branchedLine;

	/** 分岐先の実行バイナリインデックス */
	private int branchedIndex;

	public BranchIndexImpl(){
	}

	public BranchIndexImpl(int posL, int posI, int braL, int braI){
		positionLine = posL;
		positionIndex = posI;
		branchedLine = braL;
		branchedIndex = braI;
	}

	public void setPositionLine(int line){
		positionLine = line;
	}

	public int getPositionLine(){
		return positionLine;
	}

	public void setPositionIndex(int i){
		positionIndex = i;
	}

	public int getPositionIndex(){
		return positionIndex;
	}

	public void setBranchedLine(int line){
		branchedLine = line;
	}

	public int getBranchedLine(){
		return branchedLine;
	}

	public void setBranchedIndex(int i){
		branchedIndex = i;
	}

	public int getBranchedIndex(){
		return branchedIndex;
	}

	@Override
	public int hashCode(){
		return positionLine ^ positionIndex ^ branchedLine ^ branchedIndex;
	}

	@Override
	public boolean equals(Object obj){
		if(obj == null || !getClass().equals(obj.getClass())){
			return false;
		}
		BranchIndexImpl target = (BranchIndexImpl)obj;
		return positionLine == target.positionLine &&
				positionIndex == target.positionIndex &&
				branchedLine == target.branchedLine &&
				branchedIndex == target.branchedIndex;
	}

	@Override
	public String toString(){
		return String.format("%3d %3d %3d %3d",
				positionLine, positionIndex, branchedLine, branchedIndex);
	}

}
