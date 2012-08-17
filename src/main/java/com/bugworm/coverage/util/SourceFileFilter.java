package com.bugworm.coverage.util;

import java.io.File;
import java.io.FileFilter;

public class SourceFileFilter implements FileFilter {

	public static final SourceFileFilter INSTANCE = new SourceFileFilter();

	public boolean accept(File pathname) {
		return pathname.getName().endsWith(".java") ||
				pathname.getName().endsWith(".scala");
	}
}
