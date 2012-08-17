package com.bugworm.coverage.view;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;

import com.bugworm.coverage.ClassCoverage;
import com.bugworm.coverage.ProjectCoverage;
import com.bugworm.coverage.impl.ConditionInfo;
import com.bugworm.coverage.impl.EmptyCoverage;
import com.bugworm.coverage.view.listener.TreeMouseAdapter;

@SuppressWarnings("serial")
public class ProjectCoverageView extends JPanel{

	public final JTabbedPane classTab = new JTabbedPane();

	private final JPanel treePanel = new JPanel(new BorderLayout(), true);

	private final LineInfo lineInfo = new LineInfo(EmptyCoverage.EMPTY_COVERAGE);

	private final ConditionInfo conditionInfo = new ConditionInfo(EmptyCoverage.EMPTY_COVERAGE);

	private ProjectCoverage projectCoverage;

	public JTree classTree;

	public ProjectCoverageView(){

		super(new BorderLayout(), true);
		setBorder(BorderFactory.createLoweredBevelBorder());

		classTab.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEtchedBorder(),
				BorderFactory.createEmptyBorder(4, 4, 4, 4)));

		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, classTab);
		sp.setDividerSize(4);
		sp.setResizeWeight(0.2);
		add(sp, BorderLayout.CENTER);

		Box northPanel = new Box(BoxLayout.Y_AXIS);
		northPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEtchedBorder(),
				BorderFactory.createEmptyBorder(4, 4, 4, 4)));
		northPanel.add(ViewUtil.createPanel("C0 :", lineInfo));
		northPanel.add(ViewUtil.createPanel("C1 :", conditionInfo));
		add(northPanel, BorderLayout.NORTH);
	}

	public void setProject(ProjectCoverage cov){

		projectCoverage = cov;
		lineInfo.setTarget(cov);
		conditionInfo.setTarget(cov);

		treePanel.removeAll();
		classTree = new JTree(createTreeNodes(cov));
		classTree.addMouseListener(new TreeMouseAdapter(this));
		treePanel.add(new JScrollPane(classTree), BorderLayout.CENTER);
	}

	public ProjectCoverage getProject(){
		return projectCoverage;
	}

	private static TreeNode createTreeNodes(ProjectCoverage cov){

		PathTreeNode root = new PathTreeNode(null, "root");
		Map<String, PathTreeNode> packages = new HashMap<String, PathTreeNode>();

		ClassCoverage[] cc = cov.getClasses();
		Arrays.sort(cc, new Comparator<ClassCoverage>(){
			public int compare(ClassCoverage o1, ClassCoverage o2) {
				return o1.getSrcPath().compareTo(o2.getSrcPath());
			}
		});
		for(ClassCoverage clazz : cc){
			String name = clazz.getSrcPath();
			int index = name.lastIndexOf('/');
			if(index == -1){
				root.addChild(new ClassTreeNode(root, clazz));
			}else{
				String pac = name.substring(0, index);
				PathTreeNode path = packages.get(pac);
				if(path == null){
					path = new PathTreeNode(root, pac);
					packages.put(pac, path);
					root.addChild(path);
				}
				path.addChild(new ClassTreeNode(path, clazz));
			}
		}
		return root;
	}
}
