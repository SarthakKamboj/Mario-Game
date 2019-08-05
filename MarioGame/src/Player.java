import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player extends GameObject{

	private CharacterHandler ch;
	private int accX, accY;
	public static boolean jumping, hitRight, hitLeft;
	private boolean died, facingRight, facingLeft,played;
	public static ArrayList<GameObject> enemiesHit;
	public static boolean levelOver;
	private Flag flag;
	private BufferedImage standingRight,standingLeft, marioJumpRight, marioJumpLeft,marioFlag, marioDead;
	private BufferedImage[] movingRight, movingLeft;
	private double countRight, countLeft;
	
	public Player(int x, int y, CharacterHandler ch) {
		super(x,y,80,120,Type.Player);
		this.ch = ch;
		velY = 10;
		accX = -1;
		played = false;
		accY = 2;
		jumping = false;
		facingRight = true;
		facingLeft = false;
		levelOver = false;
		hitRight = true;
		hitLeft = false;
		died = false;
		countRight = 0;
		countLeft = 0;
		movingRight = new BufferedImage[6];
		movingLeft = new BufferedImage[6];
		enemiesHit = new ArrayList<GameObject>();
		try {
			standingRight = ImageHandler.scaleImage(height, width, ImageIO.read(new File("mario-stand-still.png")));
			standingLeft = ImageHandler.reverseImage(standingRight);
			for(int i = 0; i < movingRight.length; i++) {
				movingRight[i] = ImageHandler.scaleImage(height, width,ImageIO.read(new File("mario-run-right-"+(i+1)+".png")));
			}
			int xx = 0;
			for(BufferedImage image : movingRight) {
				movingLeft[xx] = ImageHandler.reverseImage(image);
				xx++;
			}
			marioJumpRight = ImageHandler.scaleImage(height, width+20, ImageIO.read(new File("mario-jump.png")));
			marioJumpLeft = ImageHandler.reverseImage(marioJumpRight);
			marioFlag = ImageHandler.scaleImage(height, width, ImageIO.read(new File("mario-flag.png")));
			marioDead = ImageHandler.scaleImage(height, (int)(width*1.5), ImageIO.read(new File("mario-dead.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void render(Graphics2D g) {
		System.out.println("Player Vel Y: "+this.getVelY());
		// TODO Auto-generated method stub
		if(levelOver) {
			g.drawImage(marioFlag, x+20, y, null);
			return;
		}
		else if(!died) {
			if(jumping) {
				if(facingRight) {
					g.drawImage(marioJumpRight, x, y, null);
					return;
				}
				else if(facingLeft) {
					g.drawImage(marioJumpLeft, x, y, null);
					return;
				}
			}
	//		System.out.println("rendering mario and velX: "+velX);
			g.setColor(Color.blue);
		//	g.drawRect(x, y, width, height);
		//	g.setColor(Color.green);
		//	g.draw(this.getBounds());
		//	g.draw(this.getBounds());
			if(velX > 0 || KeyBoard.cameraVelX < 0){
				g.drawImage(movingRight[(int)countRight],x,y,null);
			} else if(velX < 0 || KeyBoard.cameraVelX > 0){
				g.drawImage(movingLeft[(int)countLeft],x,y,null);
			}else if(velX == 0) {
				if(facingRight) {
					g.drawImage(standingRight, x, y, null);
			//		g.draw(this.getBounds());
				}
				else if(facingLeft) {
					g.drawImage(standingLeft, x, y, null);
				}
			} 
			/*
			else if(velX>0){
				g.drawImage(movingRight[(int)countRight],x,y,null);
			} else if(velX<0){
				g.drawImage(movingLeft[(int)countLeft],x,y,null);
			} */
		//	g.draw(this.getRightBounds());
		//	g.draw(this.getBottomBounds());
		} else if(died) {
			g.setColor(Color.blue);
			//g.drawRect(x, y, width, height);
		//	g.drawImage(marioFlag, x, y, null);
			g.drawImage(marioDead, x, y, null);
		}
		//g.draw(this.getBottomBounds());
	}
	
	public Rectangle getRightBounds() {
		return new Rectangle(x+width+2,y+2,2,height-4);
	}

	public Rectangle getBottomBounds() {
		return new Rectangle(x+2,y+height-2,width-4,2);
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if(levelOver) {
			if(this.getY()+this.getHeight() > flag.getY() + flag.getBounds().getHeight() - 60) {
				this.setVelY(0);
				return;
			}
			y += velY;
			return;
		}
		
		if(y > Window.HEIGHT + 2000) {
			if(!played) {
				AudioHandler.getMusic("theme").pause();
				AudioHandler.getSound("died").play();
				played = true;
			}
			DrawingCanvas.gameState = GameState.End;
			return;
		}

		if(!died) {
			
			if(velX > 0 || KeyBoard.cameraVelX < 0) {
				countRight += 0.5;
				if(countRight >= 6) countRight = 0;
				facingRight = true;
				facingLeft = false;
			} else if(velX < 0 || KeyBoard.cameraVelX > 0) {
				countLeft += 0.5;
				if(countLeft >= 6) countLeft = 0;
				facingRight = false;
				facingLeft = true;
			} 
			
		//	System.out.println("dPressed: "+KeyBoard.dPressed+" aPressed: "+KeyBoard.aPressed);
			
			
			x += velX;
			y += velY;
			
			/*if(!collideCheck()) {
				velY -= accY;
				
			}*/
			
			velY+=accY;
			collideCheck();
			
			//if(jumping) velY += accY;
	//		if(velX > 0) velX += accX;
	//		else if(velX < 0) velX -= accX;
			
			velY = Window.clamp(80, -30, velY);
		} else if(died) {
			y += velY;
			velY += accY;
		}
		
	}
	
	public void levelOver() {
		
	}
	
	public boolean collideCheck() {
		for(int i = 0; i < ch.objects.size(); i++) {
			
			if(!died) {
				GameObject tempObject = ch.objects.get(i);
				
				if(tempObject.getType() == Type.Ground || tempObject.getType() == Type.PipeObject) {
					
					if(this.getBounds().intersects(tempObject.getTopBounds())) {
						this.setVelY(0);
						this.setY(tempObject.getY() - this.getHeight());
					} 
					
					if(this.getBounds().intersects(tempObject.getLeftBounds())) {
						this.setVelX(0);
						hitRight = true;
						this.setX(tempObject.getX() - this.getWidth() - 10);
						return true;
					} else {
						hitRight = false;
					}
					
					if(this.getBounds().intersects(tempObject.getRightBounds())){
						this.setVelX(0);
						hitLeft = true;
						this.setX(tempObject.getX() + this.getWidth() + 30);
						return true;
					} else {
						hitLeft = false;
					}
					
					if(this.getBounds().intersects(tempObject.getBottomBounds())) {
					//	System.out.println("hit bottom");
						this.setVelY(0);
						this.setY(tempObject.getY() + tempObject.getHeight());
						this.setVelX(0);
						return true;
					}
				} else if (tempObject.getType() == Type.Ring) {
					if(this.getBounds().intersects(tempObject.getBounds())) {
						AudioHandler.getSound("coin").play(AudioHandler.pitch,AudioHandler.volume);
						ch.removeObject(tempObject);
						HUD.coinsHit++;
					}
				} else if(tempObject.getType() == Type.Enemy) {
					// TODO : add animation when mario hits the side of the enemy
					
					if(this.getBounds().intersects(tempObject.getTopBounds())) {
						enemiesHit.add(tempObject);
						return true;
					} else if((this.getBounds().intersects(tempObject.getLeftBounds())) && !(enemiesHit.contains(tempObject))) {
						if(!played) {
							AudioHandler.getSound("died").play();
							AudioHandler.getMusic("theme").pause();
							played = true;
						}
						tempObject.setX(this.getX() + this.getWidth() + 3);
						died = true;
						this.setVelY(-20);
						this.setVelX(0);
						for(int o = 0; o < ch.objects.size(); o++) {
							if(ch.objects.get(o).getType() == Type.Enemy)
								ch.objects.get(o).setVelX(0);
						}
					} else if(this.getBounds().intersects(tempObject.getRightBounds()) && !(enemiesHit.contains(tempObject))){
						if(!played) {
							AudioHandler.getSound("died").play();
							AudioHandler.getMusic("theme").pause();
							played = true;
						}
						tempObject.setX(this.getX()-tempObject.getWidth()-3);
						died = true;
						this.setVelY(-20);
						this.setVelX(0);
						tempObject.setVelX(0);
						for(int o = 0; o < ch.objects.size(); o++) {
							if(ch.objects.get(o).getType() == Type.Enemy)
								ch.objects.get(o).setVelX(0);
					}
				} /*else if(tempObject.getType() == Type.Shell) {
					if(this.getLeftBounds().intersects(tempObject.getBounds())) {
						this.setVelX(-10);
						Koopa_Troopa kt = (Koopa_Troopa) tempObject;
						kt.allDone();
					} else if(this.getRightBounds().intersects(tempObject.getBounds())) {
						this.setVelX(10);
						Koopa_Troopa kt = (Koopa_Troopa) tempObject;
						kt.allDone();
					}
				} */
				} else if(tempObject.getType() == Type.Flag) {
					Flag flag = (Flag) tempObject;
					//if(this.getBounds().intersects(flag.getLeftBounds())) {
				//	if(this.getBounds().intersects(new Rectangle(tempObject.getX()+25,y,3,(int)(height*1.5)+250))) {
					if(this.getBounds().intersects(flag.getLeftBounds())) {
					//	this.setX(tempObject.getX() - this.getWidth()+25);
					//	this.setX(this.getX() + 25);
					//	this.setX((int)tempObject.getLeftBounds().getX() - this.getWidth());
						AudioHandler.getMusic("theme").pause();
						AudioHandler.getSound("level-clear").play();
						this.setX((int)flag.getLeftBounds().getX()-this.getWidth());
						flag.finished();
						levelOver = true;
						this.flag = flag;
						long now = System.currentTimeMillis();
						while(System.currentTimeMillis() - now < 800) {
							continue;
						}
						this.setVelY(1);
					}
					//x: 1420 y: 150
				} else if(tempObject.getType() == Type.Boundary) {
					if(this.getBounds().intersects(tempObject.getBounds())) {
						this.setVelX(0);
						this.setX(tempObject.getX()+tempObject.getWidth());
					}
				}
			}/*else {
				return;
			} */
		//	return false;
		}
		return false;
	}
	
	public void setJump(boolean jump) {
		this.jumping = jump;
	}
	
	public boolean getJump() {
		return jumping;
	}

}
