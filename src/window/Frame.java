package window;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Frame extends JFrame{
	
	private final int WIDTH;
	private final int HEIGHT;
	private final String title;
	private final GamePanel mPanel;
	
	public Frame(int WIDTH, int HEIGHT, String title) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.title = title;
		mPanel = new GamePanel(WIDTH, HEIGHT, this);
		add(mPanel);
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		mPanel.start();
	}

}
