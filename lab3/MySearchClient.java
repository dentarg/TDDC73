package lab3;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import lab3.given.FemaleNamesSearchProvider;
import lab3.given.SearchClient;

@SuppressWarnings("serial")
public class MySearchClient extends JComponent implements SearchClient,
	DocumentListener, MouseListener {
	
	private int maxResults;
	private long searchId;
	private FemaleNamesSearchProvider f;
	private JTextField searchField;
	private List<JLabel> searchResults;
	
	public MySearchClient(FemaleNamesSearchProvider f, JTextField searchField) {
		this.f = f;
		this.searchField = searchField;
		this.searchResults = new ArrayList<JLabel>();
        this.maxResults = 5;
        
        addMouseListener(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

/*
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(JLabel label : searchResults) {
			System.out.println("draw: " + label.getText());
		}
	}
*/	

	public void addSearchResult(String searchResult) {
		JLabel label = new JLabel(searchResult);
		searchResults.add(label);
		add(label);
		label.addMouseListener(this);
		revalidate();
	}
	public void clearSearchResults() {
		System.out.println("clear");
		for(JLabel label : searchResults) {
			remove(label);
			System.out.println("removed" + label.getText());
		}
		searchResults.clear();
	}

	// called by newAsyncSearch 
	public void searchResultUpdate(long id, List<String> results) {
		if(results.isEmpty()) {
			System.out.println("Nothing found");
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
}
