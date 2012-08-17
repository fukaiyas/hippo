package com.bugworm.coverage.util;

import java.util.HashSet;
import java.util.Set;

import com.bugworm.coverage.BranchIndex;

/**
 * カバレジ結果のセーブ/ロード用のクラス。
 */
public final class CoverageResult {

	/** ソースパス */
	private String srcPath = "";

	/** ラインカバレジ情報 */
	private String lineInfo = "";

	/** 分岐カバレジ情報 */
	private Set<BranchIndex> executedCondition = new HashSet<BranchIndex>();

	/**
	 * XMLエンコーダ/デコーダ用コンストラクタ
	 */
	public CoverageResult(){
	}

	/**
	 * セーブ用にインスタンスを生成する。
	 * 
	 * @param path ソースパス
	 * @param line ラインカバレジ情報
	 * @param cond 分岐カバレジ情報
	 */
	public CoverageResult(String path, boolean[] line, Set<BranchIndex> cond){
		srcPath = path;
		lineInfo = b2s(line);
		executedCondition = cond;
	}

	/**
	 * 
	 * 
	 * @param path
	 */
	public void setSrcPath(String path){
		srcPath = path;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public String getSrcPath(){
		return srcPath;
	}

	/**
	 * 
	 * 
	 * @param line
	 */
	public void setExecutedLine(String line){
		lineInfo = line;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public String getExecutedLine(){
		return lineInfo;
	}

	/**
	 * 
	 * 
	 * @param cond
	 */
	public void setExecutedCondition(Set<BranchIndex> cond){
		executedCondition = cond;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public Set<BranchIndex> getExecutedCondition(){
		return executedCondition;
	}

	/**
	 * ハッシュ値
	 * 
	 * @return ハッシュ値
	 */
	@Override
	public int hashCode(){
		return srcPath.hashCode() ^ lineInfo.hashCode();
	}

	/**
	 * 一致するかどうか。
	 * 
	 * @param 比較対象
	 * @return 一致すればtrue、そうでなければfalse
	 */
	@Override
	public boolean equals(Object obj){
		if(obj == null || !getClass().equals(obj.getClass())){
			return false;
		}
		CoverageResult target = (CoverageResult)obj;
		return srcPath.equals(target.srcPath) &&
				lineInfo.equals(target.lineInfo) &&
				executedCondition.equals(target.executedCondition);
	}

	/**
	 * boolean配列を文字列表現にする。
	 * 
	 * @param ba boolean配列
	 * @return 文字列
	 */
	private static String b2s(boolean[] ba){

		//TODO もうちょっと効率いい形式にしたい
		StringBuilder sb = new StringBuilder();
		for(boolean b : ba){
			sb.append(b ? "t" : "f");
		}
		return sb.toString();
	}
}
