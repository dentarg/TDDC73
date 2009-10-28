package lab2;

import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

@SuppressWarnings("serial")
public class MyTree extends JTree {
	
	private DefaultMutableTreeNode root;
	private Vector<String> nodes;
	
	public MyTree(Vector<String> nodes, DefaultMutableTreeNode root) {
        super(root);
        this.root = root;
        this.nodes = nodes;
        createTopNode("abc");
        createTopNode("xyz");
        createTopNode("jkl");
	}
	
	public void createTopNode(String name) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
		root.add(node);
		nodes.add(name);
		generateNodes(node,2,2);
	}
	
	public void generateNodes(DefaultMutableTreeNode node, int childrens, 
			int generations) {
        for(int i = 0; i < childrens; i++) {
        	String str = new String(node.toString() + i);
        	DefaultMutableTreeNode child = new DefaultMutableTreeNode(str);
        	node.add(child);
        	nodes.add(str);
        	if(generations > 0) {
        		generateNodes(child,childrens,--generations);
        	}
        }
	}
}