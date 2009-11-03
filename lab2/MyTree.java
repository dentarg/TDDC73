package lab2;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

@SuppressWarnings("serial")
public class MyTree extends JTree {
	
	private DefaultMutableTreeNode root;
	
	public MyTree(DefaultMutableTreeNode root) {
        super(root);
        this.root = root;
        createTopNode("abc");
        createTopNode("xyz");
        createTopNode("jkl");
        createTopNode("abc");
	}
		
	public void createTopNode(String name) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
		root.add(node);
		generateNodes(node,2,2);
	}
	
	public void generateNodes(DefaultMutableTreeNode node, int childrens, 
			int generations) {
        for(int i = 0; i < childrens; i++) {
        	String str = new String(node.toString() + i);
        	DefaultMutableTreeNode child = new DefaultMutableTreeNode(str);
        	node.add(child);
        	if(generations > 0) {
        		generateNodes(child,childrens,--generations);
        	}
        }
	}
}