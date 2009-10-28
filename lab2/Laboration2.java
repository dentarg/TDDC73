package lab2;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;

public class Laboration2 {

    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        GridBagConstraints c2 = new GridBagConstraints();
        
        MyTextField searchField = new MyTextField();
        searchField.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchField.setPreferredSize(new Dimension(250, 25));
        // Position the search filed at the top
        c1.anchor = GridBagConstraints.FIRST_LINE_START;
        // Make search field stay the same height
        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.gridheight = GridBagConstraints.RELATIVE;
        c1.weightx = 1.0;
        // Do not make search field box bigger when resizing
        c1.weighty = 0.0;
        c1.gridx = 0;
        c1.gridy = 0;
        pane.add(searchField, c1);
        
        MyTree tree = new MyTree(new DefaultMutableTreeNode("root"));
        c2.anchor = GridBagConstraints.FIRST_LINE_START;
        c2.fill = GridBagConstraints.BOTH;
        c2.gridheight = GridBagConstraints.REMAINDER;
        c2.weightx = 1.0;
        c2.weighty = 0.5;
        c2.gridx = 0;
        c2.gridy = 1;
        pane.add(tree, c2);
    }
    
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Laboration 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
        //Place the window in a nicer position
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int)dim.getWidth()/2;
        int y = (int)dim.getHeight()/2;
        frame.setLocation(x-x/2, y-y/2);
        
        //frame.setSize(x, y);
        frame.setMinimumSize(new 	Dimension(400,600));
        frame.setPreferredSize(new 	Dimension(400,600));
        
        //Set up the content pane.
        Container pane = frame.getContentPane();
        addComponentsToPane(pane);

        //resize the JFrame to the minimum size necessary
        frame.pack();
        //Display the window.
        frame.setVisible(true);
    }
	
	public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
		System.out.println("Hello World!");
	}

}
