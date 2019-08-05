import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyBoard extends KeyAdapter{

	private CharacterHandler ch;
	public static int cameraVelX = 0;
	private GameObject player;
	
	public KeyBoard(CharacterHandler ch) {
		this.ch = ch;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < ch.objects.size();i++) {
			GameObject tempObject = ch.objects.get(i);
			if(tempObject.getType() == Type.Player) {
				player = tempObject;
			}
		}
		
		if(key == KeyEvent.VK_ESCAPE) {
			System.exit(1);
		}
		
		if(!Flag.finished) {
		if(key == KeyEvent.VK_D) {
			for(int i = 0; i < ch.objects.size(); i++) {
				if(!Player.hitRight) {
					if(player.getX() < 2*Window.WIDTH/3) {
						if(ch.objects.get(i).getType() == Type.Player) {
								ch.objects.get(i).setVelX(10);
								return;
						}
					} else {
						cameraVelX = -10;
					}
				}
			}
		}
		
		else if(key == KeyEvent.VK_A) {
			for(int i = 0; i < ch.objects.size(); i++) {
				if(!Player.hitLeft) {
					if(player.getX() > Window.WIDTH/3  || Boundary.onTheEdge) {
						if(ch.objects.get(i).getType() == Type.Player) {
								ch.objects.get(i).setVelX(-10);
						}
					} else {
						cameraVelX = 10;
					}
				}
			}
		}
		
		else if(key == KeyEvent.VK_SPACE) {
			for(int i = 0; i < ch.objects.size(); i++) {
				if(ch.objects.get(i).getType() == Type.Player) {
				/*	if(ch.objects.get(i).getVelY() >= 0) { */
					if(!Player.jumping) {
						ch.objects.get(i).setVelY(-60);
						Player player = (Player) ch.objects.get(i);
					}
				}
			}
		}
		}
		
	}
	
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_D) {
			for(int i = 0; i < ch.objects.size(); i++) {
				if(ch.objects.get(i).getType() == Type.Player) {
					ch.objects.get(i).setVelX(0);
					cameraVelX = 0;
				}
			}
		}
		
		else if(key == KeyEvent.VK_A) {
			for(int i = 0; i < ch.objects.size(); i++) {
				if(ch.objects.get(i).getType() == Type.Player) {
					ch.objects.get(i).setVelX(0);
					cameraVelX = 0;
				}
			}
		}
	}
}
