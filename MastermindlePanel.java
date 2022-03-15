package mastermindle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class MastermindlePanel extends JPanel implements ActionListener {
	
	private JLabel title;
	private JButton enter;
	private GuessPanel[] guesses;
	private Mastermindle m;
	private int currentGuess = 0;
	private boolean won;
	private boolean lost;
	private boolean buttonPressed;
	
	public MastermindlePanel(Mastermindle m) {
		super();
		
		this.declareComponents();
		
		this.addComponents();
		
		this.m = m;
		
		this.guesses[0].getGuess().setEditable(true);
	}
	
	public boolean getWon() {
		return this.won;
	}
	
	public boolean getLost() {
		return this.lost;
	}
	
	public void declareComponents() {
		this.title = new JLabel("<html> <center>Type a guess into the textbox. Green means one letter is in the word in that spot. Yellow is same but wrong spot.</center> </html>", SwingConstants.CENTER);
		this.title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
		this.title.setPreferredSize(new Dimension(500, 100));
		
		this.enter = new JButton("Enter");
		
		this.guesses = new GuessPanel[9];
		
		for (int i = 0; i < 9; i++) {
			this.guesses[i] = new GuessPanel();
		}
	}
	
	public void resetGUI() {
		this.declareComponents();
		this.addComponents();
	}
	
	public void addComponents() {
		GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        this.setLayout(gridbag);
        
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.insets = new Insets(5, 5, 10, 5);
		
		gridbag.setConstraints(this.title, c);
		this.add(this.title);

		c.insets = new Insets(0, 0, 15, 0);
		
		for (int i = 0; i <= 8; i++) {
			gridbag.setConstraints(this.guesses[i], c);
			this.add(this.guesses[i]);
		}
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 5, 5, 5);
		
		gridbag.setConstraints(this.enter, c);
		this.enter.addActionListener(this);
		
		this.add(this.enter);
	}
	
	public void guess() {
		JTextField thisGuess = this.guesses[currentGuess].getGuess();
		
		char[] guessOutput = m.guess(thisGuess.getText()).toCharArray();
		
		if (!(new String(guessOutput).equals("invalid"))) {
			thisGuess.setEditable(false);
		
			this.guesses[currentGuess].setSquares(guessOutput);
			
			this.currentGuess++;
		} else {
			thisGuess.setText("");
		}
		
		if (new String(guessOutput).equals("=====")) {
			for (int i = 0; i < 9; i++) {
				this.guesses[i].getGuess().setEditable(false);
				this.won = true;
			}
		} else if (this.currentGuess > 8) {
			for (int i = 0; i < 9; i++) {
				this.guesses[i].getGuess().setEditable(false);
				this.lost = true;
			}
		} else {
			this.guesses[this.currentGuess].getGuess().setEditable(true);
		}
		
		this.buttonPressed = false;
	}
	
	public void setTitle() {
		if (this.won) {
			this.title.setText("Congrats! The word was " + m.getAnswer() + " and you got it in " + (this.currentGuess) + " guesses.");
		} else if (this.lost) {
			this.title.setText("Nice try. The word was " + m.getAnswer() + ".");
		}
		
		this.title.setPreferredSize(new Dimension(500, 50));
	}
	
	public void setButtonPressed(boolean buttonPressed) {
		this.buttonPressed = buttonPressed;
	}
	
	public boolean getButtonPressed() {
		return this.buttonPressed;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.buttonPressed = true;
	}
}
