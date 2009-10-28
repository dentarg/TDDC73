package lab2;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

@SuppressWarnings("serial")
public class Laboration2 extends JFrame implements DocumentListener, TreeSelectionListener {
	
    private MyTextField searchField;
    private DefaultMutableTreeNode top;
    private MyTree tree;
    private JScrollPane treeView;
    private Vector<String> nodes;

    public Laboration2() {
    	initComponents();
        
        // Field stuff
        searchField.setText("/");
        searchField.getDocument().addDocumentListener(this);

        // Tree stuff
        tree.getSelectionModel().setSelectionMode
        	(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setToggleClickCount(1);
        tree.setExpandsSelectedPaths(true);

        // Listen for when the selection changes
        tree.addTreeSelectionListener(this);
        
        // test
        //tree.setSelectionPath(new TreePath(top));
    }
    
    // search our tree
    public void search() {
		String searchStr = searchField.getText();
		Iterator<String> i = nodes.iterator();
		while(i.hasNext()){
			String nodeName = i.next();
			System.out.println("nodeName: " + nodeName);
			if(searchStr == nodeName)
				tree.setSelectionPath(new TreePath(nodeName));
		}
    }
    
    // DocumentListener methods
	public void changedUpdate(DocumentEvent e) {
	}
	public void insertUpdate(DocumentEvent e) {
		search();
	}
	public void removeUpdate(DocumentEvent e) {
		search();
	}
	
	// TreeSelectionListener method
	public void valueChanged(TreeSelectionEvent e) {
		//Returns the last path element of the selection.
		//This method is useful only when the selection model allows a single selection.
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
			tree.getLastSelectedPathComponent();

		TreeNode[] nodes = node.getPath();
		StringBuilder path;
		
		//Nothing is selected.
		if (node == null) {
			path = new StringBuilder("/");
			return;
		}
		path = new StringBuilder();
		for(TreeNode item : nodes) {
			path.append("/"+item);
		}
		System.out.println("path: " + path);
		searchField.setText(path.toString());
	}
	
	// GUI stuff
    public void initComponents() {
    	nodes		= new Vector<String>();
        searchField = new MyTextField();
        top 		= new DefaultMutableTreeNode("top");
        tree 		= new MyTree(nodes, top);
        treeView 	= new JScrollPane(tree);
        
        setTitle("Laboration 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        //Place the window in a nicer position
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int)dim.getWidth()/2;
        int y = (int)dim.getHeight()/2;
        setLocation(x-x/2, y-y/2);
        setPreferredSize(new Dimension(400,600));        
        
        GridBagConstraints c1 = new GridBagConstraints();
        GridBagConstraints c2 = new GridBagConstraints();
        
        // Position the search filed at the top
        c1.anchor = GridBagConstraints.FIRST_LINE_START;
        // Make search field stay the same height
        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.gridheight = GridBagConstraints.RELATIVE;
        c1.weightx = 1.0;
        // Do not make search field box bigger when resizing
        c1.weighty = 0.0;
        c1.gridx = 0;
        c1.gridy = 0;
        
        c2.anchor = GridBagConstraints.FIRST_LINE_START;
        c2.fill = GridBagConstraints.BOTH;
        c2.gridheight = GridBagConstraints.REMAINDER;
        c2.weightx = 1.0;
        c2.weighty = 0.5;
        c2.gridx = 0;
        c2.gridy = 1;

        add(searchField, c1);
        add(treeView, c2);
        pack(); // resize the JFrame to the minimum size necessary
        setVisible(true);
    }
	
	public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new Laboration2();
            }
        });
		System.out.println("Hello World!");
	}
}
