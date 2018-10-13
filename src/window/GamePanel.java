package window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JOptionPane;

import models.Ball;
import models.Paddle;

public class GamePanel extends Canvas implements KeyListener, Runnable{
	
	private Frame mFrame;
	private Ball mBall;
	private Paddle mPaddle1;
	private Paddle mPaddle2;
	private static Boolean isRunning;
	//private Thread mThread;
	
	private final float PADDLE_WIDTH = 20;
	private final float PADDLE_HEIGHT = 150;
	private final float PADDLE_MARGIN = 20;
	private final float PADDLE_SPEED = 8f;
	private final float BALL_SPEED_X = 4f;
	private final float BALL_SPEED_Y = 4f;
	private final int SCORE_1_CORDX;
	private final int SCORE_2_CORDX;
	private final int SCORE_CORDY = 35;
	
	
	public GamePanel(int WIDTH, int HEIGHT, Frame mFrame) {
		this.SCORE_1_CORDX = (int) WIDTH / 3;
		this.SCORE_2_CORDX = (int) (WIDTH/3) * 2;
		this.mFrame = mFrame;
		isRunning = false;
		addKeyListener(this);
		setFocusable(true);
		mBall = new Ball(WIDTH / 2, HEIGHT / 2, 20f, BALL_SPEED_X, BALL_SPEED_Y);
		mPaddle1 = new Paddle(PADDLE_MARGIN, HEIGHT / 2 - PADDLE_HEIGHT, PADDLE_SPEED, PADDLE_WIDTH, PADDLE_HEIGHT, "", 0, mBall.getSize());
		mPaddle2 = new Paddle(WIDTH - PADDLE_WIDTH - 15 - PADDLE_MARGIN, HEIGHT / 2 - 100, PADDLE_SPEED, PADDLE_WIDTH, PADDLE_HEIGHT, "", 0, mBall.getSize());
		
	}
	
	 public void start() {
		 if (isRunning) return;
		 isRunning = true;
		 new Thread(this, "Pong-Thread").start();
	 }

	 public void stop() {
		 if (!isRunning) return;
		 isRunning = false;
	 }
	 
	private void paintLine(Graphics g) {
		for(int i = 0; i <= this.getBounds().getHeight(); i++) {
			g.drawLine((int)this.getBounds().getWidth()/2, i * 30,(int) this.getBounds().getWidth()/2, i*30+10);
		}
	}
	
	public void render() {
		BufferStrategy mBufferStrategy = this.getBufferStrategy();
		if(mBufferStrategy == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		do {
			do {
				Graphics g = mBufferStrategy.getDrawGraphics();
		
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				
				mBall.paint(g);
				mPaddle1.paint(g);
				mPaddle2.paint(g);
				
				g.setFont(new Font("Monospaced", Font.PLAIN, 40));
				g.drawString(String.valueOf(mPaddle1.getScore()), SCORE_1_CORDX, SCORE_CORDY);
				g.drawString(String.valueOf(mPaddle2.getScore()), SCORE_2_CORDX, SCORE_CORDY);
				
				paintLine(g);
				
				g.dispose();
			}while(mBufferStrategy.contentsRestored());
			mBufferStrategy.show();
		}while(mBufferStrategy.contentsLost());
	}
	
	public void update() {
		mBall.update(this.getBounds(), mPaddle1, mPaddle2, PADDLE_MARGIN);
		mPaddle1.update(this.getBounds());
		mPaddle2.update(this.getBounds());
		if(mPaddle1.getScore() == 10) {
			mPaddle1.setWinner(true);
			render();
			isRunning = false;
		}
		if(mPaddle2.getScore() == 10) {
			mPaddle2.setWinner(true);
			render();
			isRunning = false;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_W) mPaddle1.setUp(true);
		if(keyCode == KeyEvent.VK_S) mPaddle1.setDown(true);
		
		if(keyCode == KeyEvent.VK_UP) mPaddle2.setUp(true);
		if(keyCode == KeyEvent.VK_DOWN) mPaddle2.setDown(true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_W) mPaddle1.setUp(false);
		if(keyCode == KeyEvent.VK_S) mPaddle1.setDown(false);
		
		if(keyCode == KeyEvent.VK_UP) mPaddle2.setUp(false);
		if(keyCode == KeyEvent.VK_DOWN) mPaddle2.setDown(false);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void run() {
		System.out.println("The thread is already start");
		setNames();
		double target = 120.0;
		double nsPerTick = 1000000000.0 / target;
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double unprocessed = 0.0;
		int fps = 0;
		int tps = 0;
		boolean canRender = false;
		while(isRunning) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			
			if(unprocessed >= 1.0) {
				update();
				unprocessed--;
				tps++;
				canRender = true;
			}else {
				canRender = false;
			}
			
			try {
				Thread.sleep(1);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			if(canRender) {
				render();
				fps++;
			}
			
			if(System.currentTimeMillis() - 1000 > timer) {
				timer += 1000;
				String fixedTitle = "Pong	   FPS: " + fps + " | TPS: " + tps;
				mFrame.setTitle(fixedTitle);
				fps = 0;
				tps = 0;
			}
		}
		if(mPaddle1.getWinner()) JOptionPane.showMessageDialog(null, "The winner is: " + mPaddle1.getName());
		if(mPaddle2.getWinner()) JOptionPane.showMessageDialog(null, "The winner is: " + mPaddle2.getName());
		System.exit(0);
	}
	
	public void setNames() {
		String player1 = JOptionPane.showInputDialog(null, "Player 1");
		if(player1 == null) player1 = "Player 1";
		String player2 = JOptionPane.showInputDialog(null, "Player 2");
		if(player2 == null) player2 = "Player 2";
		mPaddle1.setName(player1);
		mPaddle2.setName(player2);
	}
}
