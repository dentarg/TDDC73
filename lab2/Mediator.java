package lab2;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class Mediator implements DocumentListener, TreeSelectionListener, 
ActionListener {

    private JTextField 						searchField;
    private MyTree 							tree;
    private DefaultMutableTreeNode 			root;    
    private Vector<TreePath>				pathsToBeSelected;
    private JButton							fltrBttn;

	public Mediator(MyTree tree, JTextField searchField, JButton fltrBttn) {
		pathsToBeSelected 	= new Vector<TreePath>();
		this.tree 			= tree;
		this.root 			= (DefaultMutableTreeNode)tree.getModel().getRoot();
		this.searchField 	= searchField;
		this.fltrBttn 		= fltrBttn;
		
		fltrBttn.setActionCommand("filter");
		
		// Listen for actions
        searchField.getDocument().addDocumentListener(this);
        tree.addTreeSelectionListener(this);
        fltrBttn.addActionListener(this);
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
    
    public void select(TreePath path) {
    	displaySearchOK();
    	pathsToBeSelected.add(path);
    	for(Enumeration<TreePath> e = pathsToBeSelected.elements(); 
    	e.hasMoreElements();) {
    		tree.addSelectionPath(e.nextElement());
    	}
        System.out.println("select(" + path + ")");
    }

    public void clearSelection() {
    	displaySearchError();
    	if(!pathsToBeSelected.isEmpty())
    		pathsToBeSelected.clear();
    	tree.clearSelection();
    	System.out.println("clearSelection()");
    }    
    
    @SuppressWarnings("unchecked")
	public TreePath search(TreePath parent, String[] searchStrings, int depth) {
    	System.out.println("--search--");
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
    	}
    	
    	if(match) {
    		TreePath mpath = new TreePath(parent.getPath());
    		// If equal, go down the branch
    		if (nodeName.equals(searchStrings[depth])) {
    			clearSelection();
    			// If at end, return match
    			if(depth == searchStrings.length-1) {
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
    						return result;
    					}

    				}
    			}
    		}
    		else if(match) {
    			select(mpath);
    			return null;
    		}
    		else {
    	    	// No match at this branch
    			displaySearchError();
    	    	return null;
    		}
    	}
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

	public void actionPerformed(ActionEvent e) {
		if("filter".equals(e.getActionCommand())) {
			fltrBttn.setActionCommand("mark");
			fltrBttn.setText("Växla till markeringsläge");
		}
		else {
			fltrBttn.setActionCommand("filter");
			fltrBttn.setText("Växla till filtreringsläge");			
		}
	}
}
