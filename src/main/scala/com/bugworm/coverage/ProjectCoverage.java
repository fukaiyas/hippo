package com.bugworm.coverage;

/**
 * プロジェクトのカバレジ情報を保持するためのインタフェース。
 */
public interface ProjectCoverage extends Coverage {

	/**
	 * ClassCoverageを追加する
	 * 
	 * @param classCov ClassCoverage
	 */
	void putClassCoverage(ClassCoverage classCov);

	/**
	 * 全クラスのClassCoverageを取得する。
	 * 
	 * @return ClassCoverageの配列
	 */
	ClassCoverage[] getClasses();

	/**
	 * ソースパスでClassCoverageを取得する。
	 * 
	 * @param srcPath ソースパス(ex:"hoge/foo/Bar.java")
	 * @return ClassCoverage
	 */
	ClassCoverage getClassCoverageBySrcPath(String srcPath);
}
