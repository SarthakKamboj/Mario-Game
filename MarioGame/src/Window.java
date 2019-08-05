import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

public class Window{
	
	public static final int HEIGHT = 900,  WIDTH = HEIGHT * 16 / 9;
	private JFrame frame;
	
	public Window(DrawingCanvas game) throws IOException {
		frame = new JFrame("Mario Game");
		frame.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		frame.setMaximumSize(new Dimension(WIDTH,HEIGHT));
		frame.setMinimumSize(new Dimension(WIDTH,HEIGHT));
		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	frame.addKeyListener(new KeyBoard(game.getCH()));

		frame.requestFocus();
		
		//add key listener
	//	frame.addKeyListener(new GameKeyboardAdapter());
		frame.requestFocus();
		
		
		frame.add(game);
		frame.pack();
		frame.setVisible(true);
		
		game.start();
		
	}
	
	public static int clamp(int max, int min, int num) {
		if(num > max) return max;
		if(num < min) return min;
		return num;
	}
	
	public JFrame getJFrame() {
		return frame;
	}
}

