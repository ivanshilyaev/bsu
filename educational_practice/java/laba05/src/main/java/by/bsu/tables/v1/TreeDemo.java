package by.bsu.tables.v1;

import by.bsu.tables.v2.Presenter;
import by.bsu.tables.v2.TableDemo;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TreeDemo extends JPanel implements TreeSelectionListener {
    private JSplitPane splitPane;
    private JTree tree; // upper component of splitPane
    private JPanel presenterPanel; // lower component of splitPane

    public TreeDemo() {
        super(new GridLayout(1, 0));

        //Create the nodes.
        DefaultMutableTreeNode root =
                new DefaultMutableTreeNode("Научная конференция");
        createNodes(root);

        //Create a tree that allows one selection at a time.
        tree = new JTree(root);
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);

        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);

        //Create the scroll pane and add the tree to it.
        JScrollPane treeView = new JScrollPane(tree);

        //Create the HTML viewing pane.
        presenterPanel = new TableDemo(new ArrayList<>());

        //Add the scroll panes to a split pane.
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(presenterPanel);

        Dimension minimumSize = new Dimension(100, 50);
        presenterPanel.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(300);
        splitPane.setPreferredSize(new Dimension(800, 500));

        //Add the split pane to this panel.
        add(splitPane);
    }

    /**
     * Required by TreeSelectionListener interface.
     */
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                tree.getLastSelectedPathComponent();

        if (node == null) return;

        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            try {
                Presenter presenter = (Presenter) nodeInfo;
                List<Presenter> list = new ArrayList<>();
                list.add(presenter);
                presenterPanel = new TableDemo(list);
                Dimension minimumSize = new Dimension(100, 50);
                presenterPanel.setMinimumSize(minimumSize);
                splitPane.setBottomComponent(presenterPanel);
                splitPane.setDividerLocation(300);
                splitPane.setPreferredSize(new Dimension(800, 500));
            } catch (ClassCastException exc) {

            }
        }
    }

    private void createNodes(DefaultMutableTreeNode root) {
        DefaultMutableTreeNode section;
        DefaultMutableTreeNode report;
        DefaultMutableTreeNode presenter;

        section = new DefaultMutableTreeNode("Математика");
        root.add(section);
        report = new DefaultMutableTreeNode("Доклад 1");
        section.add(report);
        presenter = new DefaultMutableTreeNode(new Presenter("Заломов", "Данил",
                "Павлович", "БГУ"));
        report.add(presenter);
        report = new DefaultMutableTreeNode("Доклад 2");
        section.add(report);
        presenter = new DefaultMutableTreeNode(new Presenter("Шиляев", "Иван",
                "Владимирович", "БГУ"));
        report.add(presenter);
        presenter = new DefaultMutableTreeNode(new Presenter("Беркович", "Павел",
                "Александрович", "БГУ"));
        report.add(presenter);

        section = new DefaultMutableTreeNode("Информатика");
        root.add(section);
        report = new DefaultMutableTreeNode("Доклад 3");
        section.add(report);
        presenter = new DefaultMutableTreeNode(new Presenter("Настаченко", "Артемий",
                "Юрьевич", "БГУ"));
        report.add(presenter);

        section = new DefaultMutableTreeNode("Физика");
        root.add(section);
        report = new DefaultMutableTreeNode("Доклад 4");
        section.add(report);
        presenter = new DefaultMutableTreeNode(new Presenter("Бочков", "Илья",
                "Витальевич", "БГУ"));
        report.add(presenter);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TreeDemo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new TreeDemo());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(TreeDemo::createAndShowGUI);
    }
}