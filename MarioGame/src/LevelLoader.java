import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class LevelLoader {

	private String[] levelNames;
	public static boolean loaded, finishedAllLevels;
	public static int currentLevel, radius;
	private static double animateVelY, animateAccX, animateVelX,animateAccY,animateX,animateY, originalAnimateVelY;
	private static boolean animating;
	private CharacterHandler ch;
	private Mario_Text mt;
	private Timer timer;
	private Color transparent = new Color(0f,0f,0f,0f);
	private static int xEnd = Window.WIDTH/2, yEnd = Window.HEIGHT/2;
	private BufferedImage finished;
	private boolean stop;
	public LevelLoader(CharacterHandler ch) {
		levelNames = new String[3];
		animateVelY = 10;
		animating = true;
		originalAnimateVelY = animateVelY;
		animateAccY = 0.5;
		stop = false;
		animateVelX = 2;
		animateX = 0;
		animateAccX = -0.2;
		radius = 0; 
		//radius = Window.WIDTH/2;
		for(int i = 0; i < levelNames.length; i++) {
			levelNames[i] = "level"+(i+1)+".png";
		}
		loaded = false;
		currentLevel = 1;
		this.ch = ch;
		mt = new Mario_Text();
		finishedAllLevels = false;
		try {
			finished = ImageHandler.scaleImage(ImageIO.read(new File("transparent-finished.png")), 2, 2, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//animateY = (Window.HEIGHT/2)-(finished.getHeight()/2);
		animateY = 0;
		
	}
	
	public void handleLevels(Graphics2D g) {
		/*if(currentLevel == levelNames.length + 1) {
			DrawingCanvas.gameState = GameState.End;
		} */
		if(finishedAllLevels) {
			finishedAnimation();
			g.setColor(Color.black);
			//g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
			//g.setColor(transparent);
			g.fill(new Ellipse2D.Double(xEnd,yEnd,2*radius,2*radius));
			g.drawImage(finished,(int)animateX/* (Window.WIDTH/2)-(finished.getWidth()/2)*/, (int)animateY/*(Window.HEIGHT/2)-(finished.getHeight()/2)*/, null);
			animateX += animateVelX;
			animateY += animateVelY;
			animateVelY += animateAccY;
		//	animateVelX += animateAccX;
			if(animateY > (Window.HEIGHT/2)-(finished.getHeight()/2) && animating) {
				System.out.println("animateVelY has bounced");
				//animateVelY = originalAnimateVelY + 2;
				originalAnimateVelY -= 2;
				animateVelY  = originalAnimateVelY * -1;
				//originalAnimateVelY = animateVelY;
				if(animateVelY >= 0) {
					System.out.println("animation velocities/accelerations are 0");
					animateVelY = 0;
					animateVelX = 0;
					animateAccY = 0;
					//DrawingCanvas.gameState = GameState.End;
					animating = false;
					return;
				//	DrawingCanvas.gameState = GameState.End;
				}
			}

			long now = System.currentTimeMillis();
			if(2*radius >= (int)4*Window.WIDTH) {
		/*		while(System.currentTimeMillis() - now < 3000) {
					//continue;
					int x;
				} */
				DrawingCanvas.gameState = GameState.Home;
				return;
			} 
		//	DrawingCanvas.gameState = GameState.End;
			return;
		}
		if(!loaded) {
			loadLevel(levelNames[currentLevel-1]);
		}
	}
		
	
	public void loadLevel(String level) {
		/* Here are the color codes for the objects in the game 
		 	Color (rgb)   Object
		 	black			ground
		 	blue			player
		 	(255,0,255)		grass
		 	yellow			coin
		 	(0,255,255)		pipe
		 	(222,184,135)	goomba
		 	(100,10,10) 	koopa troopa
		 	(100,100,100) 	flag and the hud
		 	(240,0,240)  	boundary
		 	(200,200,200)	castle
		 	(255,255,128)	yellow question mark
		 */
		
		
		try {
			BufferedImage map = ImageIO.read(new File(level));
			for(int x = 0; x < map.getWidth(); x++) {
				for(int y = 0; y < map.getHeight(); y++) {
					int rgb = map.getRGB(x, y);
					if(rgb == Color.black.getRGB()) {
						ch.addObject(new Ground(x*100,y*100));
					} else if(rgb == Color.blue.getRGB()) {
						ch.addObject(new Player(x*100, y*100,ch));
					} else if(rgb == (new Color(255,0,255).getRGB())) {
						ch.addObject(new Grass(x*100,y*100));
					}else if(rgb == Color.yellow.getRGB()) {
						ch.addObject(new Coin(x*100+25,y*100+25));
					} else if(rgb == (new Color(0,255,255).getRGB())) {
						ch.addObject(new GreenPipeSmall(x*100,y*100));
					} else if(rgb == (new Color(222,184,135).getRGB())) {
						ch.addObject(new Goomba(x*100,y*100,ch));
					} else if(rgb == (new Color(100,10,10).getRGB())) {
						ch.addObject(new Koopa_Troopa(x*100,y*100,ch));
					} else if(rgb == (new Color(100,100,100).getRGB())) {
						ch.addObject(new Flag(x*100,y*100,ch,this));
						ch.addObject(new HUD(mt));
					} else if(rgb == new Color(240,0,240).getRGB()) {
						ch.addObject(new Boundary(x*100,y*100));
					} else if(rgb == (new Color(200,200,200)).getRGB()) {
						ch.addObject(new Castle(x*100,y*100));
					} else if(rgb == (new Color(255,255,128)).getRGB()) {
					//	ch.addObject(new YellowQM(x*100,y*100));
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loaded = true;
	}
	
	
	public void nextLevel() {
		currentLevel++;
		if(currentLevel == levelNames.length + 1) {
			finishedAllLevels = true;
		//	DrawingCanvas.gameState = GameState.End;
		} 
		loaded = false;
		ch.clearObjects();
		Flag.finished = false;
		Boundary.onTheEdge = false;
	//	System.out.println("all objects cleared");
	}
	
	public Dimension finishedAnimation() {
		int delay = 180;
		ActionListener taskPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(radius > Window.WIDTH) {
					timer.stop();
				} 
				/*if(radius == 0) {
					timer.stop();
				}*/
				
				radius++;
				//radius--;
				xEnd = (Window.WIDTH/2) - radius;
				yEnd =  (Window.HEIGHT/2) - radius;
				
				return;
			}
		};
		
		timer = new Timer(delay,taskPerformer);
		timer.start();
		return new Dimension(xEnd,yEnd);
	}
	
	public void restart() {
		AudioHandler.getMusic("theme").loop();
		currentLevel = 1;
		loaded = false;
		ch.clearObjects();
		Flag.finished = false;
		DrawingCanvas.gameState = GameState.Levels;
	}
	
	public void goHome() {
		AudioHandler.getMusic("theme").loop();
		ch.clearObjects();
		finishedAllLevels=false;
		loaded = false;
		Flag.finished = false;
		DrawingCanvas.gameState = GameState.Home;
	}
}
