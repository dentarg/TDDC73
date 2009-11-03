package lab2;

import java.awt.Color;
import java.util.Arrays;
import java.util.NoSuchElementException;
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

	public Mediator(MyTree tree, DefaultMutableTreeNode root, 
			JTextField searchField) {
		this.tree = tree;
		this.root = root;
		this.searchField = searchField;
        searchField.getDocument().addDocumentListener(this);
        tree.addTreeSelectionListener(this);
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
	
	public void search(DefaultMutableTreeNode node, Vector<String> searchVector,
			Vector<DefaultMutableTreeNode> pathVector) {
		
		String searchStr 	= searchVector.firstElement();
		String nodeStr 		= (String) node.getUserObject();
		boolean test 		= nodeStr.startsWith(searchStr);
		test = nodeStr.matches("^" + searchStr + ".*");

		/* TODO
		 * Search all children
		 * Select all matches
		 */
		
		if(test) {			
			System.out.println("["+ searchStr +"]"+" matchar " + nodeStr);
			
			searchField.setBackground(Color.white);
			// select
		    pathVector.add(node);
		    Laboration2.printNodeVector(pathVector);
			tree.setSelectionPath(new TreePath(pathVector.toArray()));
			
			searchVector.remove(searchStr);
			try {
				search((DefaultMutableTreeNode) node.getFirstChild(), 
						searchVector, pathVector);
			} catch(NoSuchElementException e) {
				System.out.println("No children left");
			}
		}
		else {
			searchField.setBackground(Color.red);
		}
	}
    
    public void textChanged() {
    	tree.removeTreeSelectionListener(this);
    	String pathString = searchField.getText();
    	if(!pathString.startsWith("/"))
    		return;
    	
    	Pattern pattern = Pattern.compile("/");
    	String[] pathComponents = 
    		pattern.split(pathString.substring(1));
    	
    	Vector<DefaultMutableTreeNode> pathVector = 
    		new Vector<DefaultMutableTreeNode>();
    	Vector<String> searchVector =
    		new Vector<String>(Arrays.asList(pathComponents));
    	
    	search(root, searchVector, pathVector);
    	System.out.println("");
    	
    	/*for(int i=0; i < pathComponents.length; i++) {
    		System.out.println("pathComponent" + i + ": " + pathComponents[i]);
    	}*/

    	tree.addTreeSelectionListener(this);   	
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
