import java.awt.Rectangle;

public interface Collidable {
	Rectangle getLeftBounds();
	Rectangle getBottomBounds();
	Rectangle getRightBounds();
	Rectangle getTopBounds();
	Rectangle getBounds();
}
