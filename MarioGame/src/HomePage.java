import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HomePage{

	private static int startX = 0;
	
	public static void render(Graphics2D g) {
		try {
			
			//draw the home page background
			BufferedImage backgroundImage;
			backgroundImage = ImageIO.read(new File("background.png"));
			backgroundImage = ImageHandler.scaleImage(Window.HEIGHT, Window.WIDTH,backgroundImage);
			g.drawImage(backgroundImage,startX,0,null);
			
			//constant render the background image
			if(startX != 0) 
				g.drawImage(backgroundImage,startX- (Window.WIDTH), 0,null);
			startX++;
			if(startX % Window.WIDTH == 0) 
				startX = 0;
				
			//draw the logo
			BufferedImage logo = ImageIO.read(new File("super-mario-logo.png"));
			logo = ImageHandler.scaleImage(logo,2,2,0);
			g.drawImage(logo,Window.WIDTH / 10,30,null);
			
			//x-coord and y-coord in player 1 image
			int x = 40 + (6* Window.WIDTH / 10);
			int y = Window.HEIGHT / 4 + 30;
			
			//draw the text for player 1
			BufferedImage player1 = ImageIO.read(new File("player1.png"));
			player1 = ImageHandler.scaleImage(player1,3,3,1);
			g.drawImage(player1, x, y,null);
			
			
			
			//draw mushroom selector for the player selection (starts on player 1)
			BufferedImage mushroom = ImageIO.read(new File("mushroomSelector.png"));
			mushroom = ImageHandler.scaleImage(player1.getHeight() * 3 / 4, player1.getHeight() * 3 / 4, mushroom);
			g.drawImage(mushroom, x - mushroom.getWidth() + 5, y+10, null);
			
			
		}
		catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		}
	}
	

}
