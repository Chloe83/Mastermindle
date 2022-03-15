package mastermindle;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Mastermindle {
	
	private MastermindleFrame frame;
	
	private ArrayList<String> answers = new ArrayList<String>();
	private ArrayList<String> guesses = new ArrayList<String>();
	private String answer;
	
	public Mastermindle() {
		//read in the possible answers in answers.txt
	    try {
	    	Scanner input = new Scanner(new File("answers.txt"));
	    	
	    	while(input.hasNextLine()) {
	    		String temp = input.nextLine().trim();
	    		//System.out.println(temp);
	    		this.answers.add(temp);
	    	}
	    	
	    	input.close();
	    } catch(Exception e) {
	    	System.out.println("Error reading or parsing answers.txt\n" + e);
	    }   
	    
	    //read in the possible guesses in guesses.txt
	    try {
	    	Scanner input = new Scanner(new File("guesses.txt"));
	    	
	    	while(input.hasNextLine()) {
	    		this.guesses.add(input.nextLine().trim());
	    	}
	    	
	    	input.close();
	    } catch(Exception e) {
	    	System.out.println("Error reading or parsing guesses.txt");
	    }
	    
	    this.answer = this.selectAnswer();
	    this.frame = new MastermindleFrame(this);
	}
	
	public Mastermindle(String word) {
		this();
		
		this.answer = word;
	}
	
	public String getAnswer() {
		return this.answer;
	}
	
	private String selectAnswer() {
		int index = (int) (Math.random() * this.answers.size());
		
		return this.answers.get(index);
	}
	
	private boolean isValidGuess(String guess) {
		return this.guesses.contains(guess);
	}
	
	public String guess(String guess) {
		if (!this.isValidGuess(guess)) {
			return "invalid";
		}
		
		return compare(guess);
	}
	
	private String compare(String guess) {
		char[] guessChars = guess.toCharArray();
		char[] answerChars = this.answer.toCharArray();
		String output = "";
		
		for (int i = 0; i < guessChars.length; i++) {
			if (guessChars[i] == answerChars[i]) {
				output += "=";
				answerChars[i] = ' ';
				guessChars[i] = ' ';
			}
		}
		
		for (int i = 0; i < guessChars.length; i++) {
			for (int j = 0; j < answerChars.length; j++) {
				if (guessChars[i] == answerChars[j] && !(guessChars[i] == ' ' || answerChars[j] == ' ')) {
					output += "~";
					answerChars[j] = ' ';
					guessChars[i] = ' ';
				}
			}
		}
		
		for (int i = output.length(); i < guessChars.length; i++) {
				output += "x";
		}
		
		return output;
	}
	
	public void play() {
		while(!(this.frame.getPanel().getWon() || this.frame.getPanel().getLost())) {
			try {
				TimeUnit.MILLISECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (this.frame.getPanel().getButtonPressed()) {
				this.frame.getPanel().guess();
			}
		}
		
		this.frame.getPanel().setTitle();
		this.frame.pack();
		
		while(this.frame.getPanel().getWon() || this.frame.getPanel().getLost()) {
			try {
				TimeUnit.MILLISECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (this.frame.getPanel().getButtonPressed()) {
				this.frame.resetScreen();
				this.selectAnswer();
				this.play();
			}
		}
	}
}