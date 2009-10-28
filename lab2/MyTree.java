package lab2;

import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public class MyTree extends JTree {

    private class BookInfo {
        public String nodeName;

        public BookInfo(String name) {
            nodeName = name;
        }

        public String toString() {
            return nodeName;
        }
    }
	
	
	public MyTree(DefaultMutableTreeNode top) {
        super(top);
		DefaultMutableTreeNode a = new DefaultMutableTreeNode("aaa");
		DefaultMutableTreeNode b = new DefaultMutableTreeNode("bbb");
		DefaultMutableTreeNode c = new DefaultMutableTreeNode("ccc");
		top.add(a);
		top.add(b);
		top.add(c);
		generateNodes(a,3,1);
		generateNodes(b,4,2);
		generateNodes(c,2,3);
	}
	public void generateNodes(DefaultMutableTreeNode node, int childrens, 
			int generations) {
        for(int i = 0; i < childrens; i++) {
        	DefaultMutableTreeNode child = new DefaultMutableTreeNode(node.toString() + i);
        	node.add(child);
        	if(generations > 0) {
        		generateNodes(child,childrens,--generations);
        	}
        }
	}
}


