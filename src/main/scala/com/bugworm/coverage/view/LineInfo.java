package com.bugworm.coverage.view;

import com.bugworm.coverage.Coverage;
import com.bugworm.coverage.CoverageInfo;

/**
 * ラインカバレッジの情報
 */
public class LineInfo implements CoverageInfo{
//TODO viewでしかつかわないな
	/** 対象とするカバレッジ */
	private Coverage coverage;

	/**
	 * 対象を受け取るコンストラクタ。
	 * 
	 * @param cov 対象とするカバレッジ
	 */
	public LineInfo(Coverage cov){
		coverage = cov;
	}

	/**
	 * 分子を取得する。このクラスでは、実行済みの行数。
	 * 
	 * @return 分子
	 */
	public int getNumerator() {
		return coverage.getNumberOfExecutedLines();
	}

	/**
	 * 分母を取得する。このクラスでは、実行可能な行数。
	 * 
	 * @return 分母
	 */
	public int getDenominator() {
		return coverage.getNumberOfAvailableLines();
	}

	/**
	 * 対象を設定する。
	 * 
	 * @param cov 対象とするカバレッジ
	 */
	public void setTarget(Coverage cov) {
		coverage = cov;
	}
}
