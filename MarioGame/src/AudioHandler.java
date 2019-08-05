import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AudioHandler {

	private static Map<String,Sound> soundMap = new HashMap<String,Sound>();
	private static Map<String,Music> musicMap = new HashMap<String,Music>();
	public static float pitch = 1, volume = 0.2f;
	
	public static void load() {
		try {
			soundMap.put("coin", new Sound("res/coin.ogg"));
			soundMap.put("died", new Sound("res/died.ogg"));
			soundMap.put("game-complete", new Sound("res/game-complete.ogg"));
			soundMap.put("level-clear", new Sound("res/level-clear.ogg"));
			soundMap.put("here-we-go", (new Sound("res/here-we-go.ogg")));
			soundMap.put("hit-shell", new Sound("res/hit-shell.ogg"));
			
			musicMap.put("theme", new Music("res/theme-song.ogg"));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Music getMusic(String key) {
		return musicMap.get(key);
	}
	
	public static Sound getSound(String key) {
		return soundMap.get(key);
	}
}
