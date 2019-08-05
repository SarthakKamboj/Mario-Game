import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DrawingCanvas extends Canvas implements Runnable {

	private Thread thread;
	private boolean running;
	public static GameState gameState;
	public LevelLoader levelLoader;
	private CharacterHandler ch;
	private BufferedImage map;
	
	
	public DrawingCanvas() throws IOException {
		gameState = GameState.Home;
		AudioHandler.load();
		AudioHandler.getMusic("theme").loop();
		ch = new CharacterHandler();
		levelLoader = new LevelLoader(ch);
		this.addKeyListener(new KeyBoard(ch));
		this.addMouseListener(new GameMouse(levelLoader,this));
		this.requestFocus();
		new Window(this);
		map = ImageHandler.scaleImage(2*Window.HEIGHT/4, Window.WIDTH, ImageIO.read(new File("Level1Background.png")));
	}
	
	public synchronized void start() {
		thread = new Thread();
		thread.start();
		running = true;
		run();
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

public void run()
    {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running)
        {
                    long now = System.nanoTime();
                    delta += (now - lastTime) / ns;
                    lastTime = now;
                    while(delta >=1)
                    {
                        tick();
                        delta--;
                    }
                    if(running)
                    	render();
                   /* if(running) {
                        render();
                    } */
                    frames++;
                    
                    if(System.currentTimeMillis() - timer > 1000)
                    {
                        timer += 1000;
                  //     System.out.println("FPS: "+ frames);
                        frames = 0;
                    }
        }
        stop();
    }
	

	private void tick() {
		ch.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		//System.out.println(gameState);
		
		if(gameState == GameState.Home) {
		//	System.out.println(gameState);
			HomePage.render(g);
		} else if(gameState == GameState.Levels) {
			
			g.setColor(new Color(94,145,254));
			g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
			g.drawImage(map, 0, 0, this);
			levelLoader.handleLevels(g);
			//running = true;
			ch.render(g);
		} else if(gameState == GameState.End) {
			try {
				g.setColor(Color.black);
				g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);					
				g.drawImage(ImageHandler.scaleImage(8*Window.HEIGHT /9, 3*Window.WIDTH/5, ImageIO.read(new File("gameOverMario.png"))), 100+(2*Window.WIDTH/15), 20, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		g.dispose();
		bs.show();
	}
	
	public CharacterHandler getCH() {
		return ch;
	}
	
	public LevelLoader getLL() {
		return levelLoader;
	}
	
	public static void main(String[] args) throws IOException {
		new DrawingCanvas();
	}
	
	

}
