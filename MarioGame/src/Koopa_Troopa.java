import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Koopa_Troopa extends GameObject {
	
	private CharacterHandler ch;
	private boolean died, goingLeft, goingRight, allDone;
	private BufferedImage[] koopaShellRight, koopaShellLeft, koopaImagesRight,koopaImagesLeft;
	double countRight, countLeft, countRightShell, countLeftShell;
	
	
	public Koopa_Troopa(int x, int y, CharacterHandler ch) {
		super(x,y-30,100,130,Type.Enemy);
		countRight = 0;
		countLeft = 0;
		countRightShell = 0;
		countLeftShell = 0;
		this.ch = ch;
		velX = -2;
		died = false;
		goingLeft = false;
		goingRight = false;
		allDone = false;
		koopaImagesRight = new BufferedImage[7];
		koopaImagesLeft = new BufferedImage[7];
		koopaShellRight = new BufferedImage[4];
		koopaShellLeft = new BufferedImage[4];
		for(int i = 0; i < 7; i++) {
			try {
				koopaImagesLeft[i] = ImageHandler.scaleImage(height, width, ImageIO.read(new File("koopa-troopa-"+(i+1)+".png")));
				if(i < 4) {
				//	System.out.println("shell-"+(i+1)+".png");
					koopaShellRight[i] = ImageHandler.scaleImage(50, 65, ImageIO.read(new File("shell-"+(i+1)+".png")));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int xx = 0;
		for(BufferedImage image : koopaImagesLeft) {
			try {
				koopaImagesRight[xx] = ImageHandler.reverseImage(image);
				xx++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		xx = 0;
		for(BufferedImage image : koopaShellRight) {
			try {
				koopaShellLeft[xx] = ImageHandler.reverseImage(image);
				xx++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void allDone() {
		allDone = true;
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		if(!died) {
			if(velX < 0) {
				g.drawImage(koopaImagesLeft[(int)countLeft], x, y, null);
		//		g.draw(this.getLeftBounds());
				g.setColor(Color.red);
		//		g.draw(this.getTopBounds());
			}else if(velX > 0) {
				g.drawImage(koopaImagesRight[(int)countRight], x, y, null);	
		//		g.draw(this.getLeftBounds());
				g.setColor(Color.red);
		//		g.draw(this.getTopBounds());
			}else if(velX == 0) {
				if(goingRight)
					g.drawImage(koopaImagesRight[0], x, y, null);
				else if(goingLeft)
					g.drawImage(koopaImagesLeft[0], x, y, null);
			}
		//	g.draw(this.getLeftBounds());
		} else if(died) {
			System.out.println("rendering");
			if(velX == 0) {
				g.drawImage(koopaShellRight[0], x, y, null);
			} else if(velX < 0) {
				g.drawImage(koopaShellLeft[(int) countLeftShell], x, y, null);
			} else if(velX > 0) {
				g.drawImage(koopaShellRight[(int) countRightShell], x, y, null);
			}
		}
	}
	
	
	public Rectangle getTopBounds() {
		return new Rectangle(x+8, y, width - 16, 4);
	}
	

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if(!died) {
			if(velX < 0) {
				goingLeft = true;
				goingRight = false;
				countLeft += 0.2;
				if(countLeft >= 7) countLeft = 0;
			} else if(velX > 0) {
				goingLeft = false;
				goingRight = true;
				countRight += 0.2;
				if(countRight >= 7) countRight = 0;
			} 
			x+=velX;
			x += KeyBoard.cameraVelX;
			y+=velY;
			
			collideCheck();
		} else if(died) {
			x += KeyBoard.cameraVelX;
			System.out.println("ticking");
			x+=velX;
			if(velX < 0) {
				countLeftShell += 0.5;
				if(countLeftShell >= 4) countLeftShell = 0;
			} else if(velX > 0) {
				countRightShell += 0.2;
				if(countRightShell >= 4) countRightShell = 0;
			}
			
			collideCheck();
			
		}
	}
	
	public void collideCheck() {
		if(!died) {
			for(int i = 0; i < ch.objects.size(); i++) {
				GameObject tempObject = ch.objects.get(i);
				if(tempObject.getType() == Type.PipeObject) {
					if(this.getBounds().intersects(tempObject.getLeftBounds())) {
						this.setX(tempObject.getX()-this.getWidth() - 2);
						velX *= -1;
					} else if(this.getBounds().intersects(tempObject.getRightBounds())) {
						this.setX(tempObject.getX()+tempObject.getWidth() + 2);
						velX*=-1;
					}
				} else if(tempObject.getType() == Type.Player) {
					if(this.getBounds().intersects(tempObject.getBottomBounds())) {
						AudioHandler.getSound("hit-shell").play();
						died = true;
						this.setVelX(0);
						this.setVelY(0);
						this.width = 65;
						this.height = 50;
						this.y += 80;
						this.setType(Type.Shell);
						tempObject.setVelY(-25);
					}
				}
			}
		} else if(died) {
			// code for the shell
			for(int i = 0; i < ch.objects.size(); i++) {
				GameObject tempObject = ch.objects.get(i);
				if(tempObject.getType() == Type.Player) {
					System.out.println("allDone: "+ allDone);
					if(this.getBounds().intersects(tempObject.getLeftBounds())) {
						if(!allDone) {
							System.out.println("hit left");
							this.setVelX(-15);
							allDone = true;
							this.setX(this.getX()-5);
						} 
					}else if(this.getBounds().intersects(tempObject.getRightBounds())) {
						if(!allDone) {
							System.out.println("hit right");
							this.setVelX(15);
							allDone = true;
						}
					} else if(this.getBounds().intersects(tempObject.getBottomBounds())) {
						if(!allDone) {
							if(this.getX() >= (tempObject.getX() + (tempObject.getWidth()/2))) {
								this.setVelX(15);
							} else {
								this.setVelX(-15);
							}
							allDone = true;
						}
					} 
				} else if(tempObject.getType() == Type.PipeObject) {
					if(this.getBounds().intersects(tempObject.getBounds())) {
						System.out.println("hits pipe");
						velX *= -1;
						//ch.removeObject(this);
					}
				}
			}
		}
	}
}
