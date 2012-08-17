package com.bugworm.coverage;

/**
 * ソースファイルを表すインタフェース。
 */
public interface SourceFile {

	/**
	 * ソースの行数(実行対象の行数ではなくテキストファイルとしての行数)を取得する。
	 * 
	 * @return 行数
	 */
	int getAllLineSize();

	/**
	 * 指定した行番号が、実行対象であるかどうかを取得する。
	 * 
	 * @param line 行番号
	 * @return 実行対象ならtrue、そうでなければfalse
	 */
	boolean isAvailable(int line);

	/**
	 * 指定した行番号が、実行済みであるかどうかを取得する。
	 * 
	 * @param line 行番号
	 * @return 実行済みならtrue、そうでなければfalse
	 */
	boolean isExecuted(int line);

	/**
	 * 指定した行番号のソースを取得する。
	 * 
	 * @param line 行番号
	 * @return 1行分のソース
	 */
	String getLine(int line);

	/**
	 * 指定した行番号が分岐であるかどうかを返す。
	 * 
	 * @param line 行番号
	 * @return 分岐ならtrue、そうでなければfalse
	 */
	boolean isBranch(int line);
}
