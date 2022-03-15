package mastermindle;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("serial")
public class GuessPanel extends JPanel {

	private JTextField guess;
	private JLabel[] squares;
	
	public GuessPanel() {
		super();
		
		this.declareComponents();
		
		this.addComponents();
	}

	public void declareComponents() {
		this.guess = new JTextField();
		this.guess.setEditable(false);
		this.guess.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		this.guess.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.squares = new JLabel[5];
				
		for (int i = 0; i < 5; i++) {
			this.squares[i] = new JLabel(new ImageIcon("blank_square.png"));
		}
	}
	
	public void addComponents() {
		GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        this.setLayout(gridbag);
        
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weightx = 1.0;
		c.gridwidth = 5;
		c.insets = new Insets(0, 10, 0, 10);
			
		gridbag.setConstraints(this.guess, c);
		this.add(this.guess);
			
		c.weightx = 0.0;
		c.gridwidth = 1;
			
		for (int i = 0; i < 5; i++) {
			this.addSquare(i, gridbag, c);
		}
	}
	
	public void addSquare(int i, GridBagLayout gridbag, GridBagConstraints c) {
		if (i == 4) {
			c.gridwidth = GridBagConstraints.REMAINDER;
		}
		
		gridbag.setConstraints(this.squares[i], c);
		this.add(this.squares[i]);
	}
	
	public JTextField getGuess() {
		return this.guess;
	}
	
	public JLabel getSquare(int i) {
		return this.squares[i];
	}
	
	public void setSquares(char[] output) {
		int currChar = 0;
		
		for (char c : output) {
			switch (c) {
				case '=':
					this.squares[currChar].setIcon(new ImageIcon("green_square.png"));
					currChar++;
					break;
				case '~':
					this.squares[currChar].setIcon(new ImageIcon("yellow_square.png"));
					currChar++;
					break;
				default:
					this.squares[currChar].setIcon(new ImageIcon("gray_square.png"));
					currChar++;
					break;
			}
			
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 50);
	}
}
