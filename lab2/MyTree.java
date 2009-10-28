package lab2;

import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

@SuppressWarnings("serial")
public class MyTree extends JTree {
	
	private DefaultMutableTreeNode root;
	private Vector<DefaultMutableTreeNode> nodes;
	
	public MyTree(Vector<DefaultMutableTreeNode> nodes, DefaultMutableTreeNode root) {
        super(root);
        this.root = root;
        this.nodes = nodes;
        createTopNode("abc");
        createTopNode("xyz");
        createTopNode("jkl");
        //createTopNode("abc");
	}
		
	public void createTopNode(String name) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
		root.add(node);
		nodes.add(node);
		generateNodes(node,2,2);
	}
	
	public void generateNodes(DefaultMutableTreeNode node, int childrens, 
			int generations) {
        for(int i = 0; i < childrens; i++) {
        	String str = new String(node.toString() + i);
        	DefaultMutableTreeNode child = new DefaultMutableTreeNode(str);
        	node.add(child);
        	nodes.add(child);
        	if(generations > 0) {
        		generateNodes(child,childrens,--generations);
        	}
        }
	}
}