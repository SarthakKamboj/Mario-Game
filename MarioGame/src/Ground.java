import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ground extends GameObject {

	private BufferedImage ground;
	
	public Ground(int x, int y) {
		super(x,y,100,100,Type.Ground);
		try {
			ground = ImageHandler.scaleImage(height, width, ImageIO.read(new File("ground.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
	/*	g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		g.setColor(Color.cyan);
		g.draw(this.getBottomBounds());
		g.setColor(Color.green);
		g.draw(this.getLeftBounds());
		g.setColor(Color.blue);
		g.draw(this.getRightBounds());
		g.setColor(Color.red);
		g.draw(this.getTopBounds());*/
		
		g.drawImage(ground, x, y, null);
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		x += KeyBoard.cameraVelX;
		y += velY;
	}

	@Override
	public Rectangle getLeftBounds() {
		// TODO Auto-generated method stub
		return new Rectangle(x,y+3,2,height-6);
	}

	@Override
	public Rectangle getBottomBounds() {
		// TODO Auto-generated method stub
		return new Rectangle(x-2,y+height-2,width+4,2);
	}

	@Override
	public Rectangle getRightBounds() {
		// TODO Auto-generated method stub
		return new Rectangle(x+width,y+3,2,height-6);
	}

	@Override
	public Rectangle getTopBounds() {
		// TODO Auto-generated method stub
		return new Rectangle(x-2,y-2,width+4,2);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x,y,width,height);
	}
}
