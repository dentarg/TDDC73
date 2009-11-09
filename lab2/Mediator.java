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
    private boolean							searchOK;
    
    private Vector<TreePath>				pathsToBeSelected;

	public Mediator(MyTree tree, JTextField searchField) {
		this.tree = tree;
		this.root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		this.searchField = searchField;
        searchField.getDocument().addDocumentListener(this);
        tree.addTreeSelectionListener(this);
        pathsToBeSelected = new Vector<TreePath>();
	}

	public void displaySearchError() {
    	searchField.setBackground(Color.red);
    	System.err.println("NU BLEV DET R…TT");
    }
    
	public void displaySearchOK() {
    	searchField.setBackground(Color.white);
    	System.out.println("NU BLEV DET VITT");
    }		
	
	public void searchOK() {
		if(searchOK) {
			displaySearchOK();
		}
		else {
			displaySearchError();	
		}
	}
	
	public void searchOK(boolean searchOK) {
		this.searchOK = searchOK;
		searchOK();
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
        	searchOK();
            return;
        }
        
        Pattern pattern = Pattern.compile("/");
        String[] pathComponents = 
            pattern.split(pathString.substring(1));
        
        search(new TreePath(root), pathComponents, 0);

        tree.addTreeSelectionListener(this);    
    }
    
    public void select(TreePath path) {

    	pathsToBeSelected.add(path);
    	
    	for(Enumeration e = pathsToBeSelected.elements(); e.hasMoreElements();) {
    		tree.addSelectionPath((TreePath) e.nextElement());
    	}

        System.out.println("select(" + path + ")");
    }

    public void clearSelection() {
    	if(!pathsToBeSelected.isEmpty())
    		pathsToBeSelected.clear();
    	tree.clearSelection();
    	System.out.println("clearSelection()");
    }    
    
    public TreePath search(TreePath parent, String[] searchStrings, int depth) {
    	System.out.println("--search--");
    	
    	//clearSelection();
    	
    	// Get the node
    	TreeNode node = (TreeNode) parent.getLastPathComponent();
    	String nodeName = node.toString();
    	String searchStr = new String();

    	boolean match;
    	if(depth <= searchStrings.length-1) {
    		searchStr = Pattern.quote(searchStrings[depth]);
    		match = nodeName.matches("^" + searchStr + ".*");
    	}
    	else {
    		match = false;
    		System.out.println("match false");
    	}
    	
    	if(match) {
    		TreePath mpath = new TreePath(parent.getPath());
    		System.out.println("nodeName: "+ nodeName + " parent path: " + mpath);

    		// If equal, go down the branch
    		if (nodeName.equals(searchStrings[depth])) {
    			// If at end, return match
    			clearSelection();
    			if(depth == searchStrings.length-1) {
    				System.out.println("depth == searchStr.length-1: " + parent);
    				select(parent);
    				return parent;
    			}

    			// Traverse children
    			if(node.getChildCount() >= 0) {
    				for(Enumeration e = node.children(); e.hasMoreElements();) {
    					TreeNode n = (TreeNode) e.nextElement();
    					TreePath path = parent.pathByAddingChild(n);
    					TreePath result = search(path, searchStrings, depth+1);
    					// Found a match
    					if(result != null) {
    						select(result);
    						System.out.println("result != null: " + path);
    						return result;
    					}

    				}
    			}
    		}
    		else if(match) {
    			System.out.println("addSelectionPath: " + mpath + "(not equal, but match)");
    			select(mpath);
    			return null;
    		}
    		else {
    	    	// No match at this branch
    			//clearSelection();
    			System.out.println("clearSelection (not equal, no match)");
    	    	return null;
    		}
    	}
//    	searchOK();
//    	System.out.println("SLUTET match: " + match + " searchError: " + searchOK);
    	
    	//clearSelection();
    	//System.out.println("clearSelection never a match");
    	System.out.println("--end--");
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
