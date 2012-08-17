package com.bugworm.coverage.view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.bugworm.coverage.ClassCoverage;
import com.bugworm.coverage.impl.ConditionInfo;

@SuppressWarnings("serial")
public class ClassCoverageView extends JPanel {

	public final ClassCoverage classCoverage;

	private final LineInfo lineInfo;

	private final ConditionInfo conditionInfo;

	public ClassCoverageView(ClassCoverage cov){

		super(new BorderLayout(), true);
		classCoverage = cov;
		lineInfo = new LineInfo(classCoverage);
		conditionInfo = new ConditionInfo(classCoverage);

		CoverageTable table = new CoverageTable(new CoverageTableModel(classCoverage));
		JScrollPane scroll = new JScrollPane(table);
		add(scroll, BorderLayout.CENTER);

		Box graphPanel = new Box(BoxLayout.Y_AXIS);
		graphPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEtchedBorder(), BorderFactory.createEmptyBorder(1, 0, 1, 2)));
		graphPanel.add(ViewUtil.createPanel("C0 :", lineInfo));
		graphPanel.add(ViewUtil.createPanel("C1 :", conditionInfo));
		add(graphPanel, BorderLayout.SOUTH);
	}
}
