import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Castle extends GameObject{

	BufferedImage castle;
	
	public Castle(int x, int y) {
		super(x, y-250, 300, 350, Type.Castle);
		try {
			castle = ImageHandler.scaleImage(height, width, ImageIO.read(new File("marioCastle.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(Color.orange);
		g.drawImage(castle, x, y, null);
		//g.drawRect(x, y, width, height);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		x += KeyBoard.cameraVelX;
	}

	
}
