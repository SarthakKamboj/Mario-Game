import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Goomba extends GameObject{

	private CharacterHandler ch;
	private BufferedImage goombaDie, standStill;
	private BufferedImage[] goombaWalkRight, goombaWalkLeft;
	private double countRight, countLeft;
	private boolean died, diedAnimationStarted;
	
	public Goomba(int x, int y, CharacterHandler ch) throws IOException {
		super(x, y+25, 75,75, Type.Enemy);
		// TODO Auto-generated constructor stub
		velX = 2;
		velY = 0;
		countRight = 0;
		countLeft = 0;
		died = false;
		diedAnimationStarted = false;
		this.ch = ch;
		goombaWalkRight = new BufferedImage[11];
		goombaWalkLeft = new BufferedImage[11];
		for(int xx =1; xx <= 11; xx++) {
			goombaWalkRight[xx-1] = ImageHandler.scaleImage(75, 75, ImageIO.read(new File("goomba-right-"+xx+".png")));
		}
		
		int xx = 0;
		for(BufferedImage image : goombaWalkRight) {
			goombaWalkLeft[xx] = ImageHandler.reverseImage(image);
			xx++;
		} 
			
		goombaDie = ImageHandler.scaleImage(75, 75, ImageIO.read(new File("goomba-die.png")));
		standStill = ImageHandler.scaleImage(75, 75, ImageIO.read(new File("goomba-still.png")));
		
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
	//	g.fill(this.getBounds());
		if(!died) {
			if(velX > 0) {
				g.drawImage(goombaWalkRight[(int)countRight],x,y,null);
				g.setColor(Color.blue);
			//	g.draw(this.getTopBounds());
			//	g.draw(this.getLeftBounds());
				//g.draw(this.getBounds());
				countRight+=0.5;
				if(countRight >= goombaWalkRight.length) {
					countRight = 0;
				}
			} else if(velX < 0) {
				g.drawImage(goombaWalkLeft[(int)countLeft],x,y,null);
				g.setColor(Color.blue);
			//	g.draw(this.getTopBounds());
			//	g.draw(this.getLeftBounds());
				countLeft+=0.5;
				if(countLeft >= goombaWalkRight.length) {
					countLeft = 0;
				}
			} else if(velX == 0) {
				g.drawImage(standStill, x, y, null);
			}
		} else if(died) {
			diedAnimation(g);
		//	diedAnimationStarted = true;
		}
	}

	public void diedAnimation(Graphics2D g) {
		g.drawImage(goombaDie, x, y, null);
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if(!died) {
			collideCheck();
			x += velX;
			x += KeyBoard.cameraVelX;
			y+=velY;
		} else if(died) {
			velY += 1;
			if(velY == 0) {
				try {
					goombaDie = ImageHandler.scaleImage(height, width,ImageIO.read(new File("goomba-die-upside-down.png")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			y += velY;
			if(y > Window.HEIGHT + 100) {
				ch.removeObject(this);
			}
		}
		
	}
	
	public Rectangle getTopBounds() {
		return new Rectangle(x+4, y, width - 8, 4);
	}
	
	public void collideCheck() {
		if(!died) {
			for(int i = 0; i < ch.objects.size(); i++) {
				GameObject tempObject = ch.objects.get(i);
				if(tempObject.getType() == Type.PipeObject) {
					if(this.getBounds().intersects(tempObject.getLeftBounds())) {
						velX *= -1;
					} else if(this.getBounds().intersects(tempObject.getRightBounds())) {
						this.setX(tempObject.getX()+tempObject.getWidth());
						velX*=-1;
					}
				} else if(tempObject.getType() == Type.Player) {
					if(this.getBounds().intersects(tempObject.getBottomBounds())) {
						//System.out.println("the goomba hit the bottom of the player");
						AudioHandler.getSound("hit-shell").play();
						died = true;
						this.setVelY(-20);
						this.setVelX(0);
						tempObject.setVelY(-25);
					}
				} else if(tempObject.getType() == Type.Shell) {
					if(this.getBounds().intersects(tempObject.getBounds())) {
						//System.out.println("the goomba hit the bottom of the player");
						died = true;
						this.setVelY(-20);
						this.setVelX(0);
						Player.enemiesHit.add(this);
						ch.removeObject(tempObject);
					}
				}
			}
		} else if(died) {
			return;
		}
	}
	
	

}
