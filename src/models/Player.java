package models;

public class Player {
	private String name;
	private int score;
	private boolean winner;
	
	public Player(final String name, final int score) {
		this.name = name;
		this.score = score;
	}
	
	public void setWinner(boolean winner) {
		this.winner = winner;
	}
	
	public boolean getWinner() {
		return this.winner;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public void setScore(final int score) {
		this.score = score;
	}
	
	public void addScore(final int score) {
		this.score += score;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getScore() {
		return this.score;
	}
}
