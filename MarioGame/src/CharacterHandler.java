import java.awt.Graphics2D;
import java.util.ArrayList;

public class CharacterHandler {

	public ArrayList<GameObject> objects;
	
	public CharacterHandler() {
		objects = new ArrayList<GameObject>();
	}
	
	public void render(Graphics2D g) {
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).render(g);
		}
	}
	
	public void tick() {
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).tick();
		}
	}
	
	public void clearObjects() {
		objects.clear();
	}
	
	public void addObject(GameObject o) {
		objects.add(o);
	}
	
	public void removeObject(GameObject o) {
		objects.remove(o);
	}
}
