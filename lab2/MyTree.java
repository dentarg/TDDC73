package lab2;

import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public class MyTree extends JTree implements MyWidget {
	private MyDirector dir;
	
	public void changed() {
		dir.WidgetChanged(this);
	}
	
	public MyTree(DefaultMutableTreeNode top) {
        super(top);
		DefaultMutableTreeNode node =
            new DefaultMutableTreeNode("node");
        top.add(node);
	}
}
