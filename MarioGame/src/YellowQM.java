import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class YellowQM extends GameObject{

	BufferedImage questionBox;
	
	public YellowQM(int x, int y) {
		super(x, y, 80, 80, Type.Ground);
		try {
			questionBox = ImageHandler.scaleImage(height,width,ImageIO.read(new File("marioQuestionMark.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawImage(questionBox, x, y, null);
		g.draw(this.getBottomBounds());
		g.draw(this.getRightBounds());
		g.draw(this.getLeftBounds());
	}
	
	public Rectangle getBottomBounds() {
		return new Rectangle(x-2,y+height-2,width+4,2);
	}
	
	public Rectangle getLeftBounds() {
		return new Rectangle(x,y+3,2,height-6);
	}
	
	public Rectangle getRightBounds() {
		return new Rectangle(x+width-2,y+3,2,height-6);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		x += KeyBoard.cameraVelX;
		
	}

}
