import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HUD extends GameObject{

	public static int coinsHit;
	public static String coinString;
	public static int level;
	public static String levelString;
	public static int levelDisplayBehind = (Window.WIDTH/2)-100;
	
	private BufferedImage coinDisplay;
	private Mario_Text mt;
	
	public HUD(Mario_Text mt) {
		super(Window.WIDTH-400,50, 50, 50, Type.HUD);
		this.mt = mt;
		coinsHit = 0;
		try {
			coinDisplay = ImageHandler.scaleImage(height, width, ImageIO.read(new File("coin-1.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		coinString = "0";
		levelString = "level 1-"+LevelLoader.currentLevel;
		level = 1;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawImage(coinDisplay, x, y, null);
		//g.drawString(""+coinsHit, x, y);
		for(int i = 0; i < coinString.length(); i++) {
			g.drawImage(mt.numbers.get(coinString.substring(i,i+1)), x +((i+1)*60), y-5, null);
		}
		
		for(int i = 0; i < levelString.length(); i++) {
			if(mt.alphabets.get(levelString.substring(i,i+1)) != null)
				g.drawImage(mt.alphabets.get(levelString.substring(i,i+1)), (x- levelDisplayBehind)+((i+1)*40), y-5, null);
			else
				g.drawImage(mt.numbers.get(levelString.substring(i,i+1)), (x- levelDisplayBehind)+((i+1)*40)+4, y-10, null);
		}
	}

	@Override
	public void tick() {
		this.setVelX(0);
		this.setVelY(0);
		coinString = (new Integer(coinsHit)).toString();
	//	levelString = "level 1-"+(new Integer(level)).toString();
		// TODO Auto-generated method stub
		
	}

}
