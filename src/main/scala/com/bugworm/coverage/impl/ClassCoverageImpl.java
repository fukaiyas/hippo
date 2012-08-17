package com.bugworm.coverage.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bugworm.coverage.Branch;
import com.bugworm.coverage.ClassCoverage;

public class ClassCoverageImpl implements ClassCoverage {

	private final String className;

	private final String srcPath;

	private final String[] allLines;

	private final boolean[] available;

	private int numberOfAvailableLines;

	private final boolean[] executed;

	private int numberOfExecutedLines = 0;

	private final Map<Integer, Branch> branches = new HashMap<Integer, Branch>();

	private int numberOfConditions = 0;

	public final Set<Position> branchPositions = new HashSet<Position>();

	public ClassCoverageImpl(String name, String src, List<String> lines){

		className = name;
		srcPath = src;
		allLines = lines.toArray(new String[lines.size()]);
		available = new boolean[lines.size()];
		executed = new boolean[lines.size()];
	}

	public void setAvailableLines(boolean[] ava){

		if(available.length != ava.length){
			throw new RuntimeException("available length check");
		}
		numberOfAvailableLines = 0;
		for(int i = 0; i < available.length; i++){
			available[i] |= ava[i];
			if(available[i]){
				numberOfAvailableLines++;
			}
		}
	}

	public String getName() {
		return className;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public synchronized void executed(int line) {
		if(!executed[line - 1]){
			numberOfExecutedLines++;
			executed[line - 1] = true;
		}
		if(!available[line - 1]){
			System.out.println("bad line.");
		}
	}

	public boolean isExecuted(int line) {
		return executed[line - 1];
	}

	public boolean isAvailable(int line) {
		return available[line - 1];
	}

	public void setBranches(List<Branch> b){

		for(Branch br : b){
			if(branches.containsKey(br.getLineNumberPosition())){
				throw new RuntimeException("Duplicate line number of branch : " + br.getLineNumberPosition());
			}
			branches.put(br.getLineNumberPosition(), br);
			numberOfConditions += br.getNumberOfConditions();
		}
	}

	public boolean isBranch(int line) {
		return branches.containsKey(line);
	}

	public Branch getBranch(int line) {
		return branches.get(line);
	}

	/**
	 * 指定箇所に分岐が存在すればtrue。存在しなければfalseを返す。
	 * 
	 * @param line 行番号
	 * @param index 実行バイナリのインデックス
	 * @return 分岐が存在すればtrue。存在しなければfalse。
	 */
	public boolean existsDetailBranch(int line, int index){
		Branch br = branches.get(line);
		return br == null ? false : br.existsDetailBranch(index);
	}

	/**
	 * 指定箇所に分岐のfrom、toどちらかがあるかどうか。
	 * 
	 * @param line 行番号
	 * @param index 実行バイナリのインデックス
	 * @return 分岐のfrom、toが存在すればtrue。存在しなければfalse。
	 */
	public boolean existsBranchIndex(int line, int index) {
		return branchPositions.contains(Position.c(line, index));
	}

	public void branched(int linefrom, int indexfrom, int lineto, int indexto) {
		Branch br = getBranch(linefrom);
		if(br == null){
			System.out.println("Branch is null !!!");
			return;
		}
		br.branched(indexfrom, lineto, indexto);
	}

	public Collection<Branch> getBranches() {
		return branches.values();
	}

	public int getNumberOfAvailableLines() {
		return numberOfAvailableLines;
	}

	public int getNumberOfExecutedLines() {
		return numberOfExecutedLines;
	}

	public int getAllLineSize() {
		return allLines.length;
	}

	public String getLine(int line) {
		return allLines[line - 1];
	}

	public int getNumberOfConditions() {
		return numberOfConditions;
	}

	public int getNumberOfExecutedConditions() {
		int count = 0;
		for(Branch b : branches.values()){
			count += b.getNumberOfExecutedConditions();
		}
		return count;
	}

	public boolean isCompleted(){
		if(numberOfAvailableLines != numberOfExecutedLines){
			return false;
		}
		for(Branch b : branches.values()){
			if(b.getNumberOfConditions() != b.getNumberOfExecutedConditions()){
				return false;
			}
		}
		return true;
	}

	public void addBranchPosition(int line, int index) {
		//TODO Branchが自分自身のインデックスも持っていれば、setBranchで全部出来そうな気がする
		branchPositions.add(Position.c(line, index));
	}

	@Override
	public String toString(){
		//TODO Branchもつける
		StringBuilder builder = new StringBuilder("[ Class Coverage ]\n");
		builder.append("  Name : ").append(className).append("\n");
		builder.append("  Lines: ").append(getNumberOfExecutedLines()).append(" / ");
		builder.append(getNumberOfAvailableLines()).append("\n");
		return builder.toString();
	}
}
