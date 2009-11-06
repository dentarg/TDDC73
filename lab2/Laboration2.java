package lab2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

@SuppressWarnings("serial")
public class Laboration2 extends JFrame {
	
	private MyTree tree;
	private JTextField searchField;
	private DefaultMutableTreeNode root;
    private JScrollPane treeView;

    public Laboration2() {
    	initComponents();
    	//new Mediator(tree, root, searchField);
    	new Mediator(tree, searchField);

    	DefaultMutableTreeNode a = new DefaultMutableTreeNode("a");
    	DefaultMutableTreeNode b = new DefaultMutableTreeNode("b");
    	DefaultMutableTreeNode c = new DefaultMutableTreeNode("c");
    	root.add(a);
    	a.add(b);
    	b.add(c);
    	Object[] o = new Object[] {root, a, b, c};
    	TreePath p = new TreePath(o);
    	//tree.setSelectionPath(p);
    }
        
	// GUI stuff
    public void initComponents() {
        searchField = new JTextField();
        root 		= new DefaultMutableTreeNode("top");
        tree 		= new MyTree(root);
        treeView 	= new JScrollPane(tree);
        
        setTitle("Laboration 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        
        searchField.setText("/");
        tree.setToggleClickCount(1);
    	/* SINGLE_TREE_SELECTION, 
    	 * CONTIGUOUS_TREE_SELECTION or 
    	 * DISCONTIGUOUS_TREE_SELECTION
    	 */
    	tree.getSelectionModel().setSelectionMode
    		(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);        

        //Place the window in a nicer position
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int)dim.getWidth()/2;
        int y = (int)dim.getHeight()/2;
        setLocation(x-x/12, y-y/2);
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
       
    public static void printVector(Vector<String> v){
    	Iterator<String> i = v.iterator();
    	int counter = 0;
    	while(i.hasNext()) {
    		String item = (String) i.next();
    		System.out.println(counter++ + " ["+item+"]");
    	}
    }
    
    public static void printNodeVector(String name, Vector<DefaultMutableTreeNode> v){
    	System.out.println(name);
    	Iterator<DefaultMutableTreeNode> i = v.iterator();
    	int counter = 0;
    	while(i.hasNext()) {
    		String item = (String) i.next().getUserObject();
    		System.out.println(counter++ + " ["+item+"]");
    	}
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
/*        
        String s = new String("/apa/bajs/kiss/");
        Vector<String> v = 
        	new Vector<String>(Arrays.asList(s.split("/")));
        printVector(v);
        
    	Pattern pattern = Pattern.compile("/");
    	String[] pathComponents = 
    		pattern.split(s.substring(1));
    	for(int i=0; i < pathComponents.length; i++) {
    		System.out.println("pathComponent" + i + ": " + pathComponents[i]);
    	}        
        
		
		String nodeStr = new String("a");
		String searchStr = new String("a");
		boolean m = nodeStr.matches("^" + searchStr + ".*");
		System.out.println("testing: " + m);
*/		
	}
}
