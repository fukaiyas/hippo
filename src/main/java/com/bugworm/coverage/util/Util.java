package com.bugworm.coverage.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bugworm.coverage.Branch;
import com.bugworm.coverage.BranchIndex;
import com.bugworm.coverage.ClassCoverage;
import com.bugworm.coverage.ProjectCoverage;
import com.bugworm.coverage.impl.ClassCoverageImpl;
import com.bugworm.coverage.impl.ProjectCoverageImpl;

public class Util {

	public static ProjectCoverage getProjectCoverage(
			File classDir, File srcDir, FileFilter filter)throws IOException{

		ProjectCoverage project = new ProjectCoverageImpl();
		readSrc(project, srcDir, srcDir.getCanonicalPath(), filter);
		readClass(project, classDir);
		return project;
	}

	public static void readSrc(ProjectCoverage project, File currentDir,
			String srcPath, FileFilter filter)throws IOException{

		for(File child : currentDir.listFiles()){
			if(child.isDirectory()){
				readSrc(project, child, srcPath, filter);

			}else if(filter.accept(child)){
				List<String> lineArray = new ArrayList<String>();
				//ソース読み込み TODO エンコーディングを指定したい
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(child)));
				try{
					for(String line = reader.readLine(); line != null; line = reader.readLine()){
						//TODO TABの置き換えは設定したい
						lineArray.add(line.replaceAll("\t", "    "));
					}
				}finally{
					Util.closeCloseable(reader);
				}
				String path = child.getCanonicalPath().substring(srcPath.length() + 1).replace('\\', '/');
				System.out.println(path);
				ClassCoverage cov = new ClassCoverageImpl("", path, lineArray);
				project.putClassCoverage(cov);
			}
		}
	}

	public static void readClass(ProjectCoverage project, File currentDir)throws IOException{

		for(File child : currentDir.listFiles()){
			if(child.isDirectory()){
				readClass(project, child);

			}else{
				BcelUtil.parseClass(project, child);
			}
		}
	}

	public static void closeCloseable(Closeable closeable){

		if(closeable != null){
			try{
				closeable.close();
			}catch(Exception e){
			}
		}
	}

	public static void loadClassesResult(File project, ProjectCoverage pcov,
			Map<String, CoverageResult> results)throws IOException{

		File loaddir = new File(project, "current");
		for(File pac : loaddir.listFiles()){
			for(File xml : pac.listFiles()){
				CoverageResult res = load(xml);
				String name = res.getSrcPath();
				if(results.containsKey(name)){
					throw new IOException("duplicate class : " + name);
				}
				results.put(name, res);
				ClassCoverage cov = pcov.getClassCoverageBySrcPath(name);
				String exline = res.getExecutedLine();
				for(int i = 0; i < exline.length(); i++){
					if(exline.charAt(i) == 't'){
						cov.executed(i + 1);
					}
				}
				for(BranchIndex br : res.getExecutedCondition()){
					boolean db = cov.existsDetailBranch(br.getPositionLine(), br.getPositionIndex());
					if(db){
						cov.getBranch(br.getPositionLine()).branched(
								br.getPositionIndex(), br.getBranchedLine(), br.getBranchedIndex());
					}else{
						//TODO おそらくソースが変更になったと思われる。
					}
				}
			}
		}
	}

	public static void saveClassesResult(File project, ProjectCoverage pcov,
			Map<String, CoverageResult> results)throws IOException{

		for(ClassCoverage cov : pcov.getClasses()){
			boolean[] exec = new boolean[cov.getAllLineSize()];
			for(int i = 0; i < exec.length; i++){
				exec[i] = cov.isExecuted(i + 1);
			}
			Set<BranchIndex> conds = new HashSet<BranchIndex>();
			for(Branch b : cov.getBranches()){
				conds.addAll(b.getExecutedConditions());
			}
			String name = cov.getSrcPath();
			CoverageResult res = new CoverageResult(name, exec, conds);
			CoverageResult old = results.get(name);
			if(!res.equals(old)){
				int index = name.lastIndexOf('/');
				String pacname = index < 0 ? "default_package" : name.substring(0, index).replace('/', '.');
				String filename = index < 0 ? name : name.substring(index + 1);
				File pac = new File(project, "current/" + pacname);
				if(!pac.exists()){
					pac.mkdirs();
				}
				File out = new File(pac, filename + ".xml");
				save(res, out);
				results.put(name, res);
				System.out.println("save : " + name);
			}else{
				System.out.println("not save : " + name);
			}
		}
	}

	public static CoverageResult load(File in)throws IOException{

		XMLDecoder de = new XMLDecoder(new BufferedInputStream(new FileInputStream(in)));
		try{
			return (CoverageResult)de.readObject();
		}finally{
			de.close();
		}
	}

	public static void save(CoverageResult res, File out)throws IOException{

		XMLEncoder en = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(out)));
		try{
			en.writeObject(res);
		}finally{
			en.close();
		}
	}
}
