package lab3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import lab3.given.FemaleNamesSearchProvider;

@SuppressWarnings("serial")
public class Laboration3 extends JFrame implements AncestorListener {

	private JTextField searchField;
	private MySearchClient sc;
	private FemaleNamesSearchProvider f;
	private JWindow window;
	
	public Laboration3() {
    	initComponents();
    	// observers
        f.addObserver(sc);
        searchField.getDocument().addDocumentListener(sc);
        getRootPane().addAncestorListener(this);
    }
	
	// GUI stuff
    public void initComponents() {
        //Place the window in a nicer position
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int)dim.getWidth()/2;
        int y = (int)dim.getHeight()/2;
    	
        // initialize stuff
        f = FemaleNamesSearchProvider.getInstance();
        searchField = new JTextField();
        window = new JWindow(this);
        sc = new MySearchClient(f, searchField, window);
 
    	// initialize layout
        setLayout(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        GridBagConstraints c2 = new GridBagConstraints();
        
        // Position the search filed at the top
        c1.anchor = GridBagConstraints.FIRST_LINE_START;
        // Make search field stay the same height
        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.gridheight = GridBagConstraints.RELATIVE;
        c1.weightx = 1.0;
        // Do not make search field box bigger when resizing
        // or the button
        c1.weighty = 0.0;
        c1.gridx = 0;
        c1.gridy = 0;
        
        // The tree
        c2.anchor = GridBagConstraints.FIRST_LINE_START;
        c2.fill = GridBagConstraints.BOTH;
        c2.gridheight = GridBagConstraints.REMAINDER;
        c2.weightx = 1.0;
        c2.weighty = 0.5;
        c2.gridx = 0;
        c2.gridy = 3;
        
        // add stuff
        window.add(sc);
        add(searchField, c1);
        add(new JPanel(), c2);
                
        // configure frame
    	setTitle("Laboration 3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(x-2*x/3, y-y/2);
        setPreferredSize(new Dimension(300,searchField.getHeight()+50));
    	pack(); // resize the JFrame to the minimum size necessary
        setVisible(true);
        
        // configure window
        window.setBackground(Color.white);
    }

    public void printList(List<String> v){
    	Iterator<String> i = v.iterator();
    	int counter = 0;
    	while(i.hasNext()) {
    		String item = (String) i.next();
    		System.out.println(counter++ + " ["+item+"]");
    	}
    }
	
	public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new Laboration3();
            }
        });
        System.out.println("Hi!");
	}

	public void placeWindow() {
        int windowX = (int) searchField.getLocationOnScreen().getX();
        int windowY = (int) searchField.getLocationOnScreen().getY();
        int searchFieldHeight = searchField.getHeight();
        window.setLocation(windowX, windowY+searchFieldHeight);
	}
	
	public void ancestorAdded(AncestorEvent event) {
		placeWindow();
	}

	public void ancestorMoved(AncestorEvent event) {
		placeWindow();
	}

	public void ancestorRemoved(AncestorEvent event) {
	}	
}