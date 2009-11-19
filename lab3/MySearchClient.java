package lab3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import lab3.given.FemaleNamesSearchProvider;
import lab3.given.SearchClient;

@SuppressWarnings("serial")
public class MySearchClient extends JComponent implements SearchClient,
	DocumentListener, MouseListener, MouseMotionListener {
	
	private int maxResults;
	private long searchId;
	private FemaleNamesSearchProvider f;
	private JTextField searchField;
	private List<JLabel> searchResults;
	private JWindow window;
	private JLabel lastSelected;
	
	public MySearchClient(FemaleNamesSearchProvider f, JTextField searchField,
			JWindow window) {
		this.f = f;
		this.searchField = searchField;
		this.searchResults = new ArrayList<JLabel>();
        this.maxResults = 20;
        this.window = window;
        this.lastSelected = null;
        
        addMouseListener(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void addSearchResult(String searchResult) {
		JLabel label = new JLabel(searchResult);
		FontMetrics fm = this.getFontMetrics(searchField.getFont());
		label.setMinimumSize(new Dimension(searchField.getWidth(), fm.getHeight()));
		label.setPreferredSize(new Dimension(searchField.getWidth(), fm.getHeight()));
		label.setOpaque(true);
		// observers
		label.addMouseListener(this);
		label.addMouseMotionListener(this);
		searchResults.add(label);
		add(label);
		revalidate(); // update layout and repaint()
	}
	
	public void clearSearchResults() {
		System.out.println("clear");
		for(JLabel label : searchResults) {
			remove(label);
			System.out.println("removed " + label.getText());
		}
		searchResults.clear();
	}

	// called by newAsyncSearch 
	public void searchResultUpdate(long id, List<String> results) {
		if(results.isEmpty()) {
			System.out.println("Nothing found");
			searchField.setBackground(Color.red);
			window.setVisible(false);
			repaint();
			return;
		}
		
		if(searchId != id) {
			System.out.println("Wrong searchId");
			return;
		}
		
		for(String resStr : results) {
			addSearchResult(resStr);
			System.out.println("Added " + resStr);
		}
		searchField.setBackground(Color.white);
		window.setVisible(true);
    	window.toFront();
    	window.pack();
    	repaint();
	}

	// text field method 
    public void textChanged() {
    	clearSearchResults();
    	String searchStr = searchField.getText();
    	searchId = f.newAsyncSearch(searchStr, getMaxResults());
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

	// MouseListener methods
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();    
		if(obj.getClass() == new JLabel().getClass()) {
			JLabel label = (JLabel) obj;
			searchField.setText(label.getText());
		}
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

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent e) {
		Object obj = e.getSource();
		JLabel label = (JLabel) obj;
		if(obj.getClass() == new JLabel().getClass()) {
			if(lastSelected != null) {
				lastSelected.setBackground(Color.white);
			}
			label.setBackground(Color.lightGray);
			lastSelected = label;
		}
	}
}