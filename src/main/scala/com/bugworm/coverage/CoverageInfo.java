package com.bugworm.coverage;

/**
 * カバレッジ率を表すインタフェース。
 */
public interface CoverageInfo{

	/**
	 * 対象となるカバレッジを設定する。
	 * 
	 * @param cov 対象となるカバレッジ
	 */
	void setTarget(Coverage cov);

	/**
	 * 分子に相当する値を取得する。
	 * 
	 * @return 分子
	 */
	int getNumerator();

	/**
	 * 分母に相当する値を取得する。
	 * 
	 * @return 分母
	 */
	int getDenominator();
}
