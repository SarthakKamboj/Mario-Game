import java.awt.Color;
import java.awt.Graphics2D;

public class Boundary extends GameObject{

	public static boolean onTheEdge;
	
	public Boundary(int x, int y) {
		super(x, y, 20, Window.HEIGHT, Type.Boundary);
		onTheEdge = false;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(Color.blue);
	//	g.draw(this.getBounds());
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		x += KeyBoard.cameraVelX;
		if(x >= 1) {
			onTheEdge = true;
		//	System.out.println("boundaryOnTheEgde: "+onTheEdge);
		} else {
			onTheEdge = false;
		}
	}

}
