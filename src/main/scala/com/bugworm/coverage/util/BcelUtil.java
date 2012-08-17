package com.bugworm.coverage.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LineNumber;
import org.apache.bcel.classfile.LineNumberTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;
import org.apache.bcel.util.ByteSequence;

import com.bugworm.coverage.Branch;
import com.bugworm.coverage.ClassCoverage;
import com.bugworm.coverage.ProjectCoverage;
import com.bugworm.coverage.impl.BranchImpl;

public class BcelUtil {

	public static void parseClass(ProjectCoverage pcov, File classFile)throws IOException{

		BufferedInputStream clazzin = new BufferedInputStream(new FileInputStream(classFile));
		try{
			JavaClass javaClass = new ClassParser(clazzin, classFile.getCanonicalPath()).parse();
			createClassCoverage(pcov, javaClass);
		}finally{
			Util.closeCloseable(clazzin);
		}
	}

	public static void createClassCoverage(ProjectCoverage pcov,
			JavaClass javaClass)throws IOException{

		String srcname = javaClass.getSourceFileName();
		String pname = javaClass.getPackageName();
		pname = pname.replace('.', '/');
		srcname = pname + "/" + srcname;
		ClassCoverage cov = pcov.getClassCoverageBySrcPath(srcname);
		if(cov == null){
			System.out.println("No ClassCoverage : " + srcname);
			return;
		}

		List<Branch> branches = new ArrayList<Branch>();
		boolean[] ava = new boolean[cov.getAllLineSize()];

		for(Method meth : javaClass.getMethods()){
			Type[] types = meth.getArgumentTypes();
			List<String> args = new ArrayList<String>();
			for(Type ty : types){
				args.add(ty.toString());
			}
			LineNumberTable tab = meth.getLineNumberTable();
			if(tab != null){
				LineNumber[] linenums = tab.getLineNumberTable();
				for(LineNumber num : linenums){
					ava[num.getLineNumber() - 1] = true;
				}

				Map<Integer, BranchImpl> lineBranch = new HashMap<Integer, BranchImpl>();
				int lineIndex = 0;
				int lineNumber = linenums[0].getLineNumber();
				Map<Integer, InstructionContainer> pcMap = new HashMap<Integer, InstructionContainer>();
				Code code = meth.getCode();
				ByteSequence seq = new ByteSequence(code.getCode());
				while(seq.available() > 0){
					int pc = seq.getIndex();
					int ope = seq.readUnsignedByte();
					if((ope >= 153 && ope <= 166) || ope == 198 || ope == 199){
						int offset = (int)seq.readShort();
						int next = seq.getIndex();
						pcMap.put(pc, new InstructionContainer(lineNumber, ope, next - pc, offset));

					}else if(ope == Constants.LOOKUPSWITCH){
						int padding = (pc + 1) % 4;
						padding = padding == 0 ? 0 : 4 - padding;
						if(padding != 0){
							seq.skip(padding);
						}
						int defaultOffset = seq.readInt();
						int[] offsets = new int[seq.readInt() + 1];
						offsets[0] = defaultOffset;
						for(int i = 1; i < offsets.length; i++){
							seq.skip(4);
							offsets[i] = seq.readInt();
						}
						pcMap.put(pc, new InstructionContainer(lineNumber, ope, offsets));

					}else if(ope == Constants.TABLESWITCH){
						int padding = (pc + 1) % 4;
						padding = padding == 0 ? 0 : 4 - padding;
						if(padding != 0){
							seq.skip(padding);
						}
						int defaultOffset = seq.readInt();
						int[] offsets = new int[2 - seq.readInt() + seq.readInt()];
						offsets[0] = defaultOffset;
						for(int i = 1; i < offsets.length; i++){
							offsets[i] = seq.readInt();
						}
						pcMap.put(pc, new InstructionContainer(lineNumber, ope, offsets));

					}else{
						seq.skip(Constants.NO_OF_OPERANDS[ope]);
						pcMap.put(pc, new InstructionContainer(lineNumber, ope));
					}
					if(lineIndex < linenums.length - 1){
						if(linenums[lineIndex + 1].getStartPC() <= seq.getIndex()){
							lineIndex++;
							lineNumber = linenums[lineIndex].getLineNumber();
						}
					}
				}
				for(int pc : pcMap.keySet()){
					InstructionContainer con = pcMap.get(pc);
					int ope = con.operationCode;
					int line = con.lineNumber;
					if((ope >= 153 && ope <= 166) || ope == 198 || ope == 199 ||
							ope == Constants.LOOKUPSWITCH || ope == Constants.TABLESWITCH){
						if(!lineBranch.containsKey(line)){
							BranchImpl bm = new BranchImpl(line);
							lineBranch.put(line, bm);
							branches.add(bm);
						}
						BranchImpl bimp = lineBranch.get(line);
						cov.addBranchPosition(line, pc);
						for(int boff : con.branchOffsets){
							int off = pc + boff;
							InstructionContainer tcon = pcMap.get(off);
							bimp.addConditionPosition(pc, tcon.lineNumber, off);
							cov.addBranchPosition(tcon.lineNumber, off);
						}
					}
				}
			}
		}
		cov.setBranches(branches);
		cov.setAvailableLines(ava);
	}
}
