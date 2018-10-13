package models;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Paddle extends Player{
	private float x;
	private float y;
	private float speedY;
	private float WIDTH;
	private float HEIGHT;
	private float MIN_HEIGHT;
	private float MAX_HEIGHT;
	
	private boolean up;
	private boolean down;
	
	public Paddle(final float x, final float y, final float speedY, final float WIDTH, final float HEIGHT, final String nombre, final int score, final float MIN_HEIGHT) {
		super(nombre, score);
		this.x = x;
		this.y = y;
		this.speedY = speedY;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.MAX_HEIGHT = HEIGHT;
		this.MIN_HEIGHT = MIN_HEIGHT;
	}
	
	public float getPosY() {
		return this.y;
	}
	
	public float getPosX() {
		return this.x;
	}
	
	public float getHeight() {
		return this.HEIGHT;
	}
	
	public float getWidth() {
		return this.WIDTH;
	}
	
	public void decrementHeight(float m) {
		this.HEIGHT -= m;
		if(this.HEIGHT <= MIN_HEIGHT) {
			this.HEIGHT = MIN_HEIGHT;
		}
	}
	
	public void incrementHeight(float m) {
		this.HEIGHT += m;
		if(this.HEIGHT >= MAX_HEIGHT) this.HEIGHT = MAX_HEIGHT;
	}
	
	public Rectangle2D.Double getPaddle() {
		return new Rectangle2D.Double(x,y,WIDTH, HEIGHT);
	}
	
	public void setUp(boolean up) {
		this.up = up;
	}
	
	public void setDown(boolean down) {
		this.down = down;
	}
	
	public void update(Rectangle2D bounds) {
		if(up && y > 0) {
			y -= speedY;
		}
		
		if(down && y < bounds.getMaxY() - HEIGHT) {
			y += speedY;
		}
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fill(new Rectangle2D.Double(x, y, WIDTH, HEIGHT));
	}
}
