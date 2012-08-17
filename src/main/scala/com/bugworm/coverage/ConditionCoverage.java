package com.bugworm.coverage;

/**
 * 条件カバレッジを表すインタフェース。
 */
public interface ConditionCoverage {

	/**
	 * 条件による分岐数を返す。
	 * 
	 * @return 分岐数
	 */
	int getNumberOfConditions();

	/**
	 * 実行済みの分岐数を返す。
	 * 
	 * @return 実行済みの分岐数
	 */
	int getNumberOfExecutedConditions();
}
