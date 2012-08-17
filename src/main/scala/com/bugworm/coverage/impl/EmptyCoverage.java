package com.bugworm.coverage.impl;

import com.bugworm.coverage.Coverage;

public class EmptyCoverage implements Coverage {

	public static final Coverage EMPTY_COVERAGE = new EmptyCoverage();

	public int getNumberOfAvailableLines() {
		return 0;
	}

	public int getNumberOfExecutedLines() {
		return 0;
	}

	public int getNumberOfConditions() {
		return 0;
	}

	public int getNumberOfExecutedConditions() {
		return 0;
	}
}
