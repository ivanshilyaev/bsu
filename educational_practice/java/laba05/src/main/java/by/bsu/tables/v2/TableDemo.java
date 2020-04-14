package by.bsu.tables.v2;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

/**
 * TableDemo is just like SimpleTableDemo, except that it
 * uses a custom TableModel.
 */
public class TableDemo extends JPanel {
    private boolean DEBUG = false;

    public TableDemo(List<Presenter> list) {
        super(new GridLayout(1, 0));
        JTable table = new JTable(new MyTableModel(list));
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {
                "Surname",
                "Name",
                "Patronymic",
                "University"
        };
        private Object[][] data;

        public MyTableModel(List<Presenter> list) {
            if (!list.isEmpty()) {
                data = new Object[list.size()][];
                int i = 0;
                for (Presenter presenter : list) {
                    data[i] = new Object[]{presenter.getSurname(), presenter.getName(),
                            presenter.getPatronymic(), presenter.getUniversity()};
                    ++i;
                }
            } else {
                data = new Object[0][0];
            }
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell. If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            try {
                return getValueAt(0, c).getClass();
            } catch (NullPointerException e) {
                return Object.class;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                        + " to " + value
                        + " (an instance of "
                        + value.getClass() + ")");
            }
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
    }
}
