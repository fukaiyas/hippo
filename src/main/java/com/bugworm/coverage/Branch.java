package com.bugworm.coverage;

import java.util.Set;

/**
 * 分岐を表すインタフェース。ソースの行単位で生成される。
 */
public interface Branch extends ConditionCoverage{

	/**
	 * この分岐が存在する位置を返す。
	 * 
	 * @return 位置(行番号)
	 */
	int getLineNumberPosition();

	/**
	 * この分岐が持つ条件分岐先を取得する。
	 * 
	 * @return 条件分岐先
	 */
	Set<BranchIndex> getConditions();

	/**
	 * この分岐が持つ、実行済みの条件分岐先を取得する。
	 * 
	 * @return 実行済みの条件分岐先
	 */
	Set<BranchIndex> getExecutedConditions();

	/**
	 * 指定箇所に分岐が存在すればtrue。存在しなければfalseを返す。
	 * 
	 * @param index 実行バイナリのインデックス
	 * @return 分岐が存在すればtrue。存在しなければfalse。
	 */
	boolean existsDetailBranch(int index);

	/**
	 * 分岐した際に実行するメソッド
	 * 
	 * @param detailIndex 分岐元のインデックス
	 * @param pos 分岐先 の行番号
	 * @param index 分岐先のインデックス
	 */
	void branched(int detailIndex, int pos, int index);
}
