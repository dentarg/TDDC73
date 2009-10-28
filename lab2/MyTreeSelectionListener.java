package lab2;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class MyTreeSelectionListener implements TreeSelectionListener, DocumentListener {
	
	private MyTree tree;
	private MyTextField searchField;
	
	public MyTreeSelectionListener(MyTree tree, MyTextField searchField) {
		this.tree = tree;
		this.searchField = searchField;
	}

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
		System.out.println(path);
		searchField.setText(path.toString());
	}

	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

}
