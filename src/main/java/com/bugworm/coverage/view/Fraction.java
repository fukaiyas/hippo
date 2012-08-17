package com.bugworm.coverage.view;

/**
 * 分数を表すオブジェクト。
 * 
 * @author bugworm
 */
public class Fraction {

	/** 分子 */
	public final int numerator;

	/** 分母 */
	public final int denominator;

	/** 文字列表現 */
	private final String toStr;

	/**
	 * 分子、分母を指定して、オブジェクトを構築する。
	 * 
	 * @param num 分子
	 * @param den 分母
	 */
	public Fraction(int num, int den){
		numerator = num;
		denominator = den;
		toStr = num + "/" + den;
	}

	/**
	 * 文字列表現を返す。
	 * 
	 * @return このオブジェクトの文字列表現
	 */
	@Override
	public String toString(){
		return toStr;
	}
}
