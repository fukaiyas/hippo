package com.bugworm.coverage;

import java.util.Collection;
import java.util.List;

/**
 * クラスカバレッジを表すインタフェース。
 */
public interface ClassCoverage extends Coverage, SourceFile{

	/**
	 * クラス名を取得する。
	 * 
	 * @return クラス名
	 */
	String getName();

	/**
	 * ソースパスを取得する。
	 * 
	 * @return クラス名
	 */
	String getSrcPath();

	/**
	 * 行が実行されたときに実行するメソッド。
	 * 
	 * @param line 実行された行番号
	 */
	void executed(int line);

	/**
	 * 指定行に分岐が存在すれば取得する。存在しなければnullを返す。
	 * 
	 * @param line 行番号
	 * @return 引数で指定した行番号の分岐。存在しなければnull。
	 */
	Branch getBranch(int line);

	/**
	 * 指定箇所に分岐が存在すればtrue。存在しなければfalseを返す。
	 * 
	 * @param line 行番号
	 * @param index 実行バイナリのインデックス
	 * @return 分岐が存在すればtrue。存在しなければfalse。
	 */
	boolean existsDetailBranch(int line, int index);

	/**
	 * 指定箇所に分岐のfrom、toどちらかがあるかどうか。
	 * 
	 * @param line 行番号
	 * @param index 実行バイナリのインデックス
	 * @return 分岐のfrom、toが存在すればtrue。存在しなければfalse。
	 */
	boolean existsBranchIndex(int line, int index);

	/**
	 * 分岐を通過したときに実行する。
	 * 
	 * @param linefrom 分岐元の行
	 * @param indexfrom 分岐元のインデックス
	 * @param lineto 分岐先の行
	 * @param indexto 分岐先のインデックス
	 */
	void branched(int linefrom, int indexfrom, int lineto, int indexto);

	/**
	 * 分岐を全て取得する。
	 * 
	 * @return 全ての分岐
	 */
	Collection<Branch> getBranches();

	/**
	 * このカバレッジが、全て網羅されたか。
	 * 
	 * @return 網羅されていればtrue、そうでなければfalse
	 */
	boolean isCompleted();

	/**
	 * 有効な実行行を設定する。
	 * 
	 * @param ava 有効な行をあらわすboolean配列
	 */
	void setAvailableLines(boolean[] ava);

	/**
	 * 分岐に関連する箇所を追加する。
	 * 
	 * @param line 行
	 * @param index インデックス
	 */
	void addBranchPosition(int line, int index);

	/**
	 * 分岐のリストを設定する。
	 * 
	 * @param b 分岐のリスト
	 */
	void setBranches(List<Branch> b);
}
