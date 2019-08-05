import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameMouse extends MouseAdapter{

	private LevelLoader ll;
	private DrawingCanvas dc;
	
	public GameMouse(LevelLoader ll, DrawingCanvas cd) {
		this.ll = ll;
		this.dc = dc;
	}
	
	public void mousePressed(MouseEvent e) {
		//System.out.println("x: "+e.getX() + " y: "+e.getY());	
		if(DrawingCanvas.gameState == GameState.End) {
			if(e.getX() > 510 && e.getX() < 680) {
				if(e.getY() < 790 && e.getY() > 730) {
					DrawingCanvas.gameState = GameState.Levels;
					ll.restart();
				}
			} else if(e.getX() > 940 && e.getX() < 1040) {
				if(e.getY() < 790 && e.getY() > 730) {
					//DrawingCanvas.gameState = GameState.Home;
					System.out.println("mouse pressed");
					ll.goHome();
				} 
			}
		//	System.out.println("x: "+e.getX() + " y: "+e.getY());
		} else if(DrawingCanvas.gameState == GameState.Home) {
			if(e.getX() < 1360 && e.getX() > 1000) {
				if(e.getY() < 320 && e.getY() > 255){
					System.out.println("button pressed");
				//	ll.restart();
					DrawingCanvas.gameState = GameState.Levels;
				//	AudioHandler.getMusic("theme").stop();
					AudioHandler.getSound("here-we-go").play(AudioHandler.pitch,AudioHandler.volume);
				}
			}
		}
	}
}
