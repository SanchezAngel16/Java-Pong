package models;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import swing.GamePanel;

public class Ball {
	
	private float x;
	private float y;
	private float initX;
	private float initY;
	private float speedX;
	private float speedY;
	private float size;
	private float dy = 1;
	private float dx = 1;
	
	public Ball(final float x, final float y, final float size, final float speedX, final float speedY) {
		this.speedX = speedX;
		this.speedY = speedY;
		this.initX = x;
		this.initY = y;
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public float getSize() {
		return this.size;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fill(new Rectangle2D.Double(x,y,size,size));
	}
	
	public void flipX() {
		dx = -dx;
		
	}
	
	public void flipY() {
		dy = -dy;
	}
	
	public void resetPos() {
		x = initX;
		y = initY;
	}
	
	private Rectangle2D getBall() {
		return new Rectangle2D.Double(x,y,size,size);
	}
	
	private boolean collision(Rectangle2D p) {
		return getBall().intersects(p);
	}
	
	public void update(final Rectangle2D bounds, final Paddle mPaddle1, final Paddle mPaddle2, final float PADDLE_MARGIN) {
		x += dx * speedX;
		y += dy * speedY;
		
		if(collision(mPaddle1.getPaddle())) {
			flipX();
			x = mPaddle1.getPosX() + mPaddle1.getWidth();
			mPaddle1.decrementHeight(5);
		}
		
		if(collision(mPaddle2.getPaddle())) {
			flipX();
			x = mPaddle2.getPosX() - mPaddle2.getWidth();
			mPaddle2.decrementHeight(5);
		}
		
		if(x < PADDLE_MARGIN - 15) {
			resetPos();
			//mPaddle1.setHeight(10);
			flipX();
			flipY();
			mPaddle2.addScore(1);
			mPaddle2.incrementHeight(10);
			mPaddle1.decrementHeight(10);
			this.speedX += 0.20;
			this.speedY += 0.20;
		}
		if(y < 0) flipY();
		if(x > bounds.getMaxX() - size - PADDLE_MARGIN + 15) {
			resetPos();
			//mPaddle2.setHeight(10);
			flipX();
			mPaddle1.addScore(1);
			mPaddle1.incrementHeight(10);
			mPaddle2.decrementHeight(10);
			this.speedX += 0.10;
			this.speedY += 0.10;
		}
		if(y > bounds.getMaxY() - size) flipY();
	}
}


