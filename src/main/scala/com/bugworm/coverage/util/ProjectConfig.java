package com.bugworm.coverage.util;

public class ProjectConfig {

	private String sourceDir;

	private String classDir;

	public void setSourceDir(String sd){
		sourceDir = sd;
	}

	public String getSourceDir(){
		return sourceDir;
	}

	public void setClassDir(String cd){
		classDir = cd;
	}

	public String getClassDir(){
		return classDir;
	}
}
