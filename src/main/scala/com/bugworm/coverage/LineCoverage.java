package com.bugworm.coverage;

/**
 * ラインカバレッジを表すインタフェース
 */
public interface LineCoverage {

	/**
	 * 実行可能な行数を取得する。
	 * 
	 * @return 実行可能な行数
	 */
	int getNumberOfAvailableLines();

	/**
	 * 実行済みの行数を取得する。
	 * 
	 * @return 実行済み行数
	 */
	int getNumberOfExecutedLines();
}
