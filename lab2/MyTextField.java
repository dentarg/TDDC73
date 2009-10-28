package lab2;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class MyTextField extends JTextField implements MyWidget {
	private MyDirector dir;
	
	public void changed() {
		dir.WidgetChanged(this);
	}

	public MyTextField() {
		// TODO Auto-generated constructor stub
	}

	public MyTextField(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public MyTextField(int columns) {
		super(columns);
		// TODO Auto-generated constructor stub
	}

	public MyTextField(String text, int columns) {
		super(text, columns);
		// TODO Auto-generated constructor stub
	}

	public MyTextField(Document doc, String text, int columns) {
		super(doc, text, columns);
		// TODO Auto-generated constructor stub
	}

}
