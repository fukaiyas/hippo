package com.bugworm.coverage.view;

import javax.swing.table.AbstractTableModel;

import com.bugworm.coverage.Branch;
import com.bugworm.coverage.ClassCoverage;

@SuppressWarnings("serial")
public class CoverageTableModel extends AbstractTableModel {

	public static final String[] HEADER = {
		"Line", "C0", "C1", ""
	};

	public static final String C0_NON = "";

	public static final String C0_NOT_EXECUTED = "x";

	public static final String C0_EXECUTED = "o";

	private final ClassCoverage coverage;

	public CoverageTableModel(ClassCoverage cov){
		coverage = cov;
	}

	@Override
	public String getColumnName(int column){

		return HEADER[column];
	}

	public int getColumnCount() {

		return HEADER.length;
	}

	public int getRowCount() {

		return coverage.getAllLineSize();
	}

	public Object getValueAt(int row, int column) {

		//念のため
		if(row < 0 || row >= coverage.getAllLineSize()){
			return "";
		}
		//行番号だから+1
		int line = row + 1;
		if(column == 0){
			return line;

		}else if(column == 1){
			return coverage.isExecuted(line) ? C0_EXECUTED : (coverage.isAvailable(line) ? C0_NOT_EXECUTED : C0_NON);

		}else if(column == 2){
			return getConditionMark(line);

		}else if(column == 3){
			return coverage.getLine(row + 1);
		}
		return "";
	}

	private Object getConditionMark(int line){

		Branch br = coverage.getBranch(line);
		if(br == null){
			return "";
		}
		int num = br.getNumberOfConditions();
		int exe = br.getNumberOfExecutedConditions();
		return new Fraction(exe, num);
	}
}
