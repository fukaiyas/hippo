package com.bugworm.coverage.impl;

import com.bugworm.coverage.Coverage;
import com.bugworm.coverage.CoverageInfo;

/**
 * 分岐カバレッジの情報
 */
public class ConditionInfo implements CoverageInfo{
//TODO viewでしかつかわないな
	/** 対象とするカバレッジ */
	private Coverage coverage;

	/**
	 * 対象を受け取るコンストラクタ。
	 * 
	 * @param cov 対象とするカバレッジ
	 */
	public ConditionInfo(Coverage cov){
		coverage = cov;
	}

	/**
	 * 分子を取得する。このクラスでは、実行済みの分岐数。
	 * 
	 * @return 分子
	 */
	public int getNumerator() {
		return coverage.getNumberOfExecutedConditions();
	}

	/**
	 * 分母を取得する。このクラスでは、実行可能な分岐数。
	 * 
	 * @return 分母
	 */
	public int getDenominator() {
		return coverage.getNumberOfConditions();
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
