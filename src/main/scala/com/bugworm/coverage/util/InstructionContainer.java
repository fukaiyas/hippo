package com.bugworm.coverage.util;

import java.util.Arrays;

public class InstructionContainer {

	public final int lineNumber;

	public final int operationCode;

	public final int[] branchOffsets;

	public InstructionContainer(int ln, int op, int... off){
		lineNumber = ln;
		operationCode = op;
		branchOffsets = off.clone();
	}

	@Override
	public String toString(){
		return String.format("%3d %2x %s", lineNumber, operationCode, Arrays.toString(branchOffsets));
	}
}
