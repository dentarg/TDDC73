package lab2;

import java.awt.Color;
import java.util.Enumeration;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class Mediator implements DocumentListener, TreeSelectionListener {

    private JTextField 						searchField;
    private MyTree 							tree;
    private DefaultMutableTreeNode 			root;
    private Vector<TreePath>				pathsToBeSelected;

	public Mediator(MyTree tree, JTextField searchField) {
		pathsToBeSelected 	= new Vector<TreePath>();
		this.tree 			= tree;
		this.root 			= (DefaultMutableTreeNode)tree.getModel().getRoot();
		this.searchField 	= searchField;
		
		// Listen for actions
        searchField.getDocument().addDocumentListener(this);
        tree.addTreeSelectionListener(this);
	}

	public void displaySearchError() {
    	searchField.setBackground(Color.red);
    }
    
	public void displaySearchOK() {
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
        	displaySearchError();
            return;
        }
        Pattern pattern = Pattern.compile("/");
        String[] pathComponents = 
            pattern.split(pathString.substring(1));
        search(new TreePath(root), pathComponents, 0);
        tree.addTreeSelectionListener(this);
    }
   
    @SuppressWarnings("unchecked")
	public TreePath search(TreePath parent, String[] searchStrings, int depth) {
    	// Get the node
    	DefaultMutableTreeNode node = 
    		(DefaultMutableTreeNode) parent.getLastPathComponent();
    	String nodeName = node.toString();
    	String searchStr = new String();

    	boolean match;
    	if(depth <= searchStrings.length-1) {
    		searchStr = Pattern.quote(searchStrings[depth]);
    		match = nodeName.matches("^" + searchStr + ".*");
    	}
    	else {
    		match = false;
    	}
  	
    	if(match) {
    		TreePath mpath = new TreePath(parent.getPath());
    		// If equal, go down the branch
    		if (nodeName.equals(searchStrings[depth])) {
    			clearSelection("search string: " + searchStrings[depth]);
    			// If at end, return match
    			if(depth == searchStrings.length-1) {
    				select(parent, "depth == searchStrings.length-1");
    				System.out.println("--return parent: ["+parent+"]");
    				return parent;
    			}

    			// Traverse children
    			if(node.getChildCount() >= 0) {
    				for(Enumeration e = node.children(); e.hasMoreElements();) {
    					DefaultMutableTreeNode n = 
    						(DefaultMutableTreeNode) e.nextElement();
    					TreePath path = parent.pathByAddingChild(n);
    					TreePath result = 
    						search(path, searchStrings, depth+1);
    					// Found a match
    					if(result != null) {    						
    						select(result, "result != null");
    						return result;
    					}
    				}
    			}
    		}
    		else if(match) {
    			select(mpath, "match");
    			return null;
    		}
    		else {
    	    	// No match at this branch
    			displaySearchError();
    	    	return null;
    		}
    	} 
    	else {
    		clearSelection("no match");
    	}
    	return null;
    }    
    
    public void select(TreePath path, String id) {
    	displaySearchOK();
    	pathsToBeSelected.add(path);
    	for(Enumeration<TreePath> e = pathsToBeSelected.elements(); 
    	e.hasMoreElements();) {
    		tree.addSelectionPath(e.nextElement());
    	}
        //System.out.println("select(" + path + ") " + id);
    }

    public void clearSelection(String id) {
    	displaySearchError();
    	if(!pathsToBeSelected.isEmpty())
    		pathsToBeSelected.clear();
    	tree.clearSelection();
    	//System.out.println("clearSelection("+id+")");
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
	
    public DefaultMutableTreeNode TreePathToNode(TreePath path) {
    	return (DefaultMutableTreeNode) path.getLastPathComponent();
    }
    
    public DefaultMutableTreeNode cloneNode(DefaultMutableTreeNode node) {
    	return (DefaultMutableTreeNode) node.clone();
    }
    
    public DefaultMutableTreeNode cloneTreePathToNode(TreePath path) {
    	return cloneNode(TreePathToNode(path));
    }	
}
