import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class GameObject implements Collidable{

	protected int x, y, velX, velY;
	protected Type type;
	protected int width, height;
	
	public GameObject(int x, int y, int width, int height, Type type) {
		this.x = x;
		this.type = type;
		this.y = y;
		this.width = width;
		this.height = height;
		this.velX = 0;
		this.velY = 0;
	}
	
	public abstract void render(Graphics2D g);
	public abstract void tick();
	
	public Rectangle getBounds() {
		return new Rectangle(x,y,width,height);
	}
	
	public Rectangle getLeftBounds() {
		return new Rectangle(x-2,y,2,height);
	}
	
	public Rectangle getRightBounds() {
		return new Rectangle(x+width,y,2,height);
	}
	
	public Rectangle getTopBounds() {
		return new Rectangle(x,y-2,width,2);
	}
	
	public Rectangle getBottomBounds() {
		return new Rectangle(x,y,width,2);
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setVelX(int velX) {
		this.velX = velX;
	}
	
	public int getVelX() {
		return velX;
	}
	
	public void setVelY(int velY) {
		this.velY = velY;
	}
	
	public int getVelY() {
		return velY;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return y;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
}
