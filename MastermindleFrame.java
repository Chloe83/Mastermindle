package mastermindle;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MastermindleFrame extends JFrame {
	private MastermindlePanel panel;
	private Mastermindle m;
	
	public MastermindleFrame(Mastermindle m) {
		super();
		
		this.panel = new MastermindlePanel(m);
		this.m = m;
		
		this.setup();
	}
	
	private void setup() {
		this.add("Center", panel);
		this.setResizable(false);
        this.pack();
        this.setTitle("Mastermindle");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public MastermindlePanel getPanel() {
		return this.panel;
	}
	
	public void resetScreen() {
		this.panel.setVisible(false);
		this.panel = new MastermindlePanel(this.m);
		this.add("Center", panel);
		this.pack();
		this.setVisible(true);
	}
}
