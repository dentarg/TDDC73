package lab2;

import java.awt.Color;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class Mediator implements DocumentListener, TreeSelectionListener {

    private JTextField 						searchField;
    private MyTree 							tree;
    private DefaultMutableTreeNode 			root;

	public Mediator(MyTree tree, 
			//DefaultMutableTreeNode root, 
			JTextField searchField) {
		this.tree = tree;
		this.root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		this.searchField = searchField;
        searchField.getDocument().addDocumentListener(this);
        tree.addTreeSelectionListener(this);
	}
    
	public void searchError() {
    	searchField.setBackground(Color.red);
    }
    
    public void searchOK() {
    	searchField.setBackground(Color.white);
    }	
    
    // DocumentListener methods
	public void changedUpdate(DocumentEvent e) {
	}
	public void insertUpdate(DocumentEvent e) {
		textChanged();
	}
	public void removeUpdate(DocumentEvent e) {
		textChanged();
	}
	
    public void textChanged() {
        tree.removeTreeSelectionListener(this);
        String pathString = searchField.getText();
        if(!pathString.startsWith("/")) {
        	searchError();
            return;
        }
        
        Pattern pattern = Pattern.compile("/");
        String[] pathComponents = 
            pattern.split(pathString.substring(1));
        
        tree.setSelectionPath(
        		search(new TreePath(root), pathComponents, 0)
        );

        tree.addTreeSelectionListener(this);    
    }
    
    public TreePath search(TreePath parent, String[] searchStr, int depth) {
    	// Get the node
    	TreeNode node = (TreeNode) parent.getLastPathComponent();
    	String nodeName = node.toString();
		
    	// WIP: If match, select
    	if (nodeName.matches("^" + searchStr[depth] + ".*")) {
    		tree.addSelectionPath(
    				parent
    		);
    	}
    	
    	// If equal, go down the branch
    	if (nodeName.equals(searchStr[depth])) {
    		searchOK();
    		// If at end, return match
    		if(depth == searchStr.length-1)
    			return parent;
    		
    		// Traverse children
    		if(node.getChildCount() >= 0) {
    			for(Enumeration e = node.children(); e.hasMoreElements();) {
    				TreeNode n = (TreeNode) e.nextElement();
    				TreePath path = parent.pathByAddingChild(n);
    				TreePath result = search(path, searchStr, depth+1);
    				// Found a match
    				if(result != null)
    					return result;
    			}
    		}
    	}
    	// No match at this branch
    	searchError();
    	return null;
    }
    	
	// TreeSelectionListener method
	public void valueChanged(TreeSelectionEvent e) {
		searchField.getDocument().removeDocumentListener(this);
		// Build path string
		StringBuilder sb = new StringBuilder();
		for(Object o : e.getPath().getPath()) {
			sb.append("/").append(o.toString());
		}
		// Set the text
		searchField.setText(sb.toString());
		searchField.getDocument().addDocumentListener(this);
	}
}
