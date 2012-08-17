package com.bugworm.coverage.impl;

import java.util.HashMap;
import java.util.Map;

import com.bugworm.coverage.ClassCoverage;
import com.bugworm.coverage.ProjectCoverage;

public class ProjectCoverageImpl implements ProjectCoverage {

	private final Map<String, ClassCoverage> classCoverages =
		new HashMap<String, ClassCoverage>();

	public void putClassCoverage(ClassCoverage classCov){

		classCoverages.put(classCov.getSrcPath(), classCov);
	}

	public ClassCoverage getClassCoverageBySrcPath(String srcPath) {

		return classCoverages.get(srcPath);
	}

	public ClassCoverage[] getClasses() {
		return classCoverages.values().toArray(new ClassCoverage[classCoverages.size()]);
	}

	public int getNumberOfAvailableLines() {

		int num = 0;
		for(ClassCoverage ccov : classCoverages.values()){
			num += ccov.getNumberOfAvailableLines();
		}
		return num;
	}

	public int getNumberOfExecutedLines() {

		int num = 0;
		for(ClassCoverage ccov : classCoverages.values()){
			num += ccov.getNumberOfExecutedLines();
		}
		return num;
	}

	public int getNumberOfConditions() {

		int num = 0;
		for(ClassCoverage ccov : classCoverages.values()){
			num += ccov.getNumberOfConditions();
		}
		return num;
	}

	public int getNumberOfExecutedConditions() {

		int num = 0;
		for(ClassCoverage ccov : classCoverages.values()){
			num += ccov.getNumberOfExecutedConditions();
		}
		return num;
	}

	//TODO toString
}
