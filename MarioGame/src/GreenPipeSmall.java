import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GreenPipeSmall extends GameObject{

	private BufferedImage pipe;
	
	public GreenPipeSmall(int x, int y) {
		super(x, y, 100, 140, Type.PipeObject);
		// TODO Auto-generated constructor stub
		try {
			pipe = ImageHandler.scaleImage(100, 140, ImageIO.read(new File("small-pipe.png")));
	//		pipe = ImageHandler.scaleImage(pipe.getHeight()*2, pipe.getHeight(), pipe);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawImage(pipe, x, y, null);
	//	g.draw(this.getLeftBounds());
	/*	g.setColor(Color.black);
	//	g.draw(this.getBounds());
		g.setColor(Color.blue);
		g.draw(this.getLeftBounds());
		g.setColor(Color.green);
		g.draw(this.getRightBounds());
		g.setColor(Color.red);
		g.draw(this.getBottomBounds());
		g.setColor(Color.cyan);
		g.draw(this.getTopBounds()); */
	}

	public Rectangle getRightBounds() {
		return new Rectangle(x+width,y,2,height);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x-15,y,width+30,height);
	}
	
	public Rectangle getLeftBounds() {
		return new Rectangle(x-3,y,3,height);
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		x += KeyBoard.cameraVelX;
	}

}
