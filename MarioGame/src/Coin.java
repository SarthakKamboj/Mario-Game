import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Coin extends GameObject{

	private BufferedImage[] coins;
	private BufferedImage coin;
	private double counter;
	
	public Coin(int x, int y) {
		super(x, y, 50, 50, Type.Ring);
		counter = 0;
		try {
			coin = ImageIO.read(new File("image.gif"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// TODO Auto-generated constructor stub
		coins = new BufferedImage[21];
		try {
			
			for(int xx = 0; xx < 6; xx++) {
				coins[xx] = ImageHandler.scaleImage(50, 50, ImageIO.read(new File("coin-"+(xx+1)+".png")));
			}
			
			/*
			BufferedImage coinSprite = ImageIO.read(new File("coin_sprite.png"));
			for(int xx = 0; xx < 21; xx++) {
				if(xx < 16) {
					coins[xx] = ImageHandler.scaleImage(width, height, coinSprite.getSubimage(xx*128, 0, 128, 128));
				} else {
					coins[xx] = ImageHandler.scaleImage(width, height, coinSprite.getSubimage((xx-16)*128, 128, 128, 128));
				}
			} */
			counter = 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		if(counter >= 6) counter = 0;
		//g.drawImage(coins[(int)counter], x, y, null);
		g.drawImage(coins[(int) counter], x, y, null);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		counter+=0.25;
		x+=KeyBoard.cameraVelX;
		y+=velY;
	}
	
	

}
