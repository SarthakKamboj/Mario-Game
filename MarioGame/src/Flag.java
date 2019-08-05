import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Flag extends GameObject{

	private BufferedImage[] flag;
	private BufferedImage flagPole, solelyFlag;
	private double count;
	public static boolean finished;
	private int flagX, flagY;
	private CharacterHandler ch;
	private LevelLoader ll;
	
	public Flag(int x, int y, CharacterHandler ch, LevelLoader ll) throws IOException {
		super(x,y-200-250,80,200, Type.Flag);
		flag = new BufferedImage[3];
		//flagX = 0;
		//flagY = 0;
		this.ch = ch;
		this.ll = ll;
		System.out.println("flag x: "+x+" flag y: "+y);
		
		for(int i = 0; i < 3; i++) {
	//		System.out.println("i: "+i+" f-"+(i+1)+".png");
			try {
			flag[i] = ImageHandler.scaleImage((int)(height*1.5)+250,(int)(width*1.2),ImageIO.read(new File("f-"+(i+1)+".png")));
			} catch(Exception e) {
				System.out.println(e.toString());
			}
		}
		count = 0;
		flagPole = ImageHandler.scaleImage((int)(height*1.5)+250,(int)(width*1.2),ImageIO.read(new File("transparent-flag-pole.png")));
		solelyFlag = ImageHandler.scaleImage(60,70,ImageIO.read(new File("flag-transparent.png")));
		/*x: 1429 y: 174
		x: 1497 y: 167
		x: 1428 y: 232
		x: 1497 y: 229 */
	}

	public Rectangle getBounds() {
		return new Rectangle(x+25,y,(int)(width*1.2),(int)(height*1.5)+250);
		
	}
	
	public Rectangle getLeftBounds() {
		return new Rectangle(x+29,y+20,3,(int)(height*1.5)+250);
	}
	
	@Override
	public void render(Graphics2D g) {
		if(!finished) {
			g.drawImage(flag[(int)count], x, y, null);
			g.setColor(Color.white);
			g.draw(this.getLeftBounds());
			
		} else if(finished) {
			g.drawImage(flagPole, x, y, null);
			g.drawImage(solelyFlag,flagX, flagY, null);
			//x: 1429 y: 173
		}
	}

	@Override
	public void tick() {
	//	System.out.println("flag is still here");
		// TODO Auto-generated method stub

		
		if(!finished) {
			count += 0.2;
			if(count >= 3) count = 0;
			x +=KeyBoard.cameraVelX;
			y+=velY;
		} else if(finished) {
			if(flagY > this.getY() + this.getBounds().getHeight() - 140) {
				this.setVelY(0);
				long now = System.currentTimeMillis();
				while(System.currentTimeMillis() - now < (3500)) {
					/*try {
					System.out.println("waiting");
					continue;
					} catch(Exception e) {
						System.out.println(e);
						System.exit(1);
					} */
				}
			/*	ch.removeObject(this);
				System.out.println("flag removed"); */
				ll.nextLevel();
				AudioHandler.getMusic("theme").play();
				return;
			}
			flagY += velY;
		}
	}
	
	public void finished() {
		for(int i = 0; i < ch.objects.size();i++) {
			if(ch.objects.get(i).getType() == Type.Enemy) {
				ch.objects.get(i).setVelX(0);
			}
		}
		finished = true;
		flagX = x+27;
		//flagY = 173;
		flagY = (int) (this.getLeftBounds().getY()+5);
		this.setVelY(2);
	}
	
}
