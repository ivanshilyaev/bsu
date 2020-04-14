package by.bsu.tables.v2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

public class DynamicTreeDemo extends JPanel
        implements ActionListener {
    private int newNodeSuffix = 1;
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String CLEAR_COMMAND = "clear";

    private DynamicTree treePanel;

    public DynamicTreeDemo() {
        super(new BorderLayout());

        //Create the components.
        treePanel = new DynamicTree();
        populateTree(treePanel);

        JButton addButton = new JButton("Add");
        addButton.setActionCommand(ADD_COMMAND);
        addButton.addActionListener(this);

        JButton removeButton = new JButton("Remove");
        removeButton.setActionCommand(REMOVE_COMMAND);
        removeButton.addActionListener(this);

        JButton clearButton = new JButton("Clear");
        clearButton.setActionCommand(CLEAR_COMMAND);
        clearButton.addActionListener(this);

        //Lay everything out.
        treePanel.setPreferredSize(new Dimension(800, 500));
        add(treePanel, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(0, 3));
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(clearButton);
        add(panel, BorderLayout.SOUTH);
    }

    public void populateTree(DynamicTree treePanel) {
        DefaultMutableTreeNode section;
        DefaultMutableTreeNode report;
        DefaultMutableTreeNode presenter;

        section = new DefaultMutableTreeNode("Математика");
        treePanel.addObject(treePanel.getRoot(), section);
        report = new DefaultMutableTreeNode("Доклад 1");
        treePanel.addObject(section, report);
        presenter = new DefaultMutableTreeNode(new Presenter("Заломов", "Данил",
                "Павлович", "БГУ"));
        treePanel.addObject(report, presenter);
        report = new DefaultMutableTreeNode("Доклад 2");
        treePanel.addObject(section, report);
        presenter = new DefaultMutableTreeNode(new Presenter("Шиляев", "Иван",
                "Владимирович", "БГУ"));
        treePanel.addObject(report, presenter);
        presenter = new DefaultMutableTreeNode(new Presenter("Беркович", "Павел",
                "Александрович", "БГУ"));
        treePanel.addObject(report, presenter);

        section = new DefaultMutableTreeNode("Информатика");
        treePanel.addObject(treePanel.getRoot(), section);
        report = new DefaultMutableTreeNode("Доклад 3");
        treePanel.addObject(section, report);
        presenter = new DefaultMutableTreeNode(new Presenter("Настаченко", "Артемий",
                "Юрьевич", "БГУ"));
        treePanel.addObject(report, presenter);

        section = new DefaultMutableTreeNode("Физика");
        treePanel.addObject(treePanel.getRoot(), section);
        report = new DefaultMutableTreeNode("Доклад 4");
        treePanel.addObject(section, report);
        presenter = new DefaultMutableTreeNode(new Presenter("Бочков", "Илья",
                "Витальевич", "БГУ"));
        treePanel.addObject(report, presenter);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (ADD_COMMAND.equals(command)) {
            //Add button clicked
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    treePanel.getTree().getLastSelectedPathComponent();
            if (node == null) return;
            //Object nodeInfo = node.getUserObject();
            if (!node.isLeaf()) return;
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
            treePanel.addObject(parent, new Presenter("New presenter " + newNodeSuffix++));
        } else if (REMOVE_COMMAND.equals(command)) {
            //Remove button clicked
            treePanel.removeCurrentNode();
        } else if (CLEAR_COMMAND.equals(command)) {
            //Clear button clicked.
            treePanel.clear();
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("DynamicTreeDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        DynamicTreeDemo newContentPane = new DynamicTreeDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(DynamicTreeDemo::createAndShowGUI);
    }
}
