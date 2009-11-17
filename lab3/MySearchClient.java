package lab3;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import lab3.given.FemaleNamesSearchProvider;
import lab3.given.SearchClient;

@SuppressWarnings("serial")
public class MySearchClient extends JComponent implements SearchClient,
	DocumentListener, MouseListener {
	
	private int maxResults;
	private FemaleNamesSearchProvider f;
	private JTextField searchField;
	private List<String> searchResults;
	
	public MySearchClient(FemaleNamesSearchProvider f, JTextField searchField) {
		this.f = f;
		this.searchField = searchField;
		this.searchResults = new ArrayList<String>();
        this.maxResults = 5;
        
        addMouseListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int x = 0;
		int y = 10;
		for(String resStr : searchResults) {
			System.out.println("draw: " + resStr);
			g.drawString(resStr, x, y);
			y+=10;
			System.out.println(g.getFontMetrics().getStringBounds(resStr, g));
		}
	}

	public void searchResultUpdate(long arg0, List<String> arg1) {
		if(arg1.isEmpty())
			System.out.println("Nothing found");
		for(String resStr : arg1) {
			searchResults.add(resStr);
			System.out.println("Added " + resStr);
		}
		System.out.println(searchResults);
		repaint();
	}

    public void textChanged() {
    	searchResults.clear();
    	String searchStr = searchField.getText();
    	f.newAsyncSearch(searchStr, getMaxResults());
    	repaint();
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
	
	public void setMaxResults(int n) {
		maxResults = n;
	}
	public int getMaxResults() {
		return maxResults;
	}

	public void mouseClicked(MouseEvent e) {
		int X=e.getX();
        int Y=e.getY();
        System.out.println("The (X,Y) coordinate of window is ("+X+","+Y+")");
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
