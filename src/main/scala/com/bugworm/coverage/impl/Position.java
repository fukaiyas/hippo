package com.bugworm.coverage.impl;

public class Position {

	public final int line;
	public final int index;
	private final int hash;
	public Position(int ln, int idx){
		line = ln;
		index = idx;
		hash = line ^ index;
	}
	public static Position c(int line, int index){
		return new Position(line, index);
	}
	@Override
	public int hashCode(){
		return hash;
	}
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Position)){
			return false;
		}
		Position target = (Position)o;
		return line == target.line && index == target.index;
	}
	@Override
	public String toString(){
		return "(" + line + ":" + index + ")";
	}
}
