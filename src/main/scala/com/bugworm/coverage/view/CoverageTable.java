package com.bugworm.coverage.view;

import static com.bugworm.coverage.view.CoverageTableModel.HEADER;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.bugworm.coverage.view.renderer.ConditionCellRenderer;
import com.bugworm.coverage.view.renderer.CoverageRenderer;
import com.bugworm.coverage.view.renderer.TextCellRenderer;

@SuppressWarnings("serial")
public class CoverageTable extends JTable {

	private static final int[] PREFERRED_WIDTH = {
		48, 32, 48, 512
	};

	private static final int[] MAX_WIDTH = {
		48, 32, 48, Integer.MAX_VALUE
	};

	private static final int[] MIN_WIDTH = {
		48, 32, 48, 128
	};

	private static final TableCellRenderer[] RENDERER = {

		new TextCellRenderer(JLabel.RIGHT),
		new CoverageRenderer(),
		new ConditionCellRenderer(),
		new TextCellRenderer(JLabel.LEFT),
	};

	public CoverageTable(TableModel model){
		super(model);
		setRowHeight(16);
		for(int i = 0; i < PREFERRED_WIDTH.length; i++){
			TableColumn col = getColumn(HEADER[i]);
			col.setPreferredWidth(PREFERRED_WIDTH[i]);
			col.setMaxWidth(MAX_WIDTH[i]);
			col.setMinWidth(MIN_WIDTH[i]);
		}
	}

	@Override
	public TableCellRenderer getCellRenderer(int row, int column){

		return RENDERER[column];
	}
}
