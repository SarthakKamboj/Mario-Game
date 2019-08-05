import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Mario_Text {

	public HashMap<String,BufferedImage> alphabets = new HashMap<String,BufferedImage>();
	public HashMap<String,BufferedImage> numbers = new HashMap<String,BufferedImage>();
	private String[] realAlph = new String[] {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"," ","-"};
	private String[] realNum = new String[] {"0","1","2","3","4","5","6","7","8","9"};
	private BufferedImage alphabetsSprite, textSprite;
	
	public Mario_Text() {
		try {
			alphabetsSprite = ImageIO.read(new File("alphabets.png"));
			textSprite = ImageIO.read(new File("numbers.png"));
			int numberOfAlphabets = 27;
			int boundaryRemover = 20;
			for(int x = 0; x < numberOfAlphabets; x++) {
				if(x < 9) {
					//alphabetsSprite.getSubimage((x*110)+5,0,105,120) ==> +5 is to remove any annoying boundaries and 110 -> 105 is to account for the width change					
					alphabets.put(realAlph[x],ImageHandler.scaleImage(50, 50, alphabetsSprite.getSubimage((x*110)+boundaryRemover,0,110-boundaryRemover,120)));
				} else if(x < 18) {
					alphabets.put(realAlph[x],ImageHandler.scaleImage(50, 50, alphabetsSprite.getSubimage(((x-9)*110)+boundaryRemover,120,110-boundaryRemover,120)));
				} else if(x < 27) {
					alphabets.put(realAlph[x],ImageHandler.scaleImage(50, 50, alphabetsSprite.getSubimage(((x-18)*110)+boundaryRemover,240,110-boundaryRemover,120)));
				}
			}
			
			alphabets.put(realAlph[27],ImageHandler.scaleImage(50, 50, ImageIO.read(new File("dash.png"))));
			
			int numberOfNumbers = 10;
			for(int x = 0; x < numberOfNumbers; x++) {
				if(x < 9) {
					numbers.put(realNum[x],ImageHandler.scaleImage(60, 60, textSprite.getSubimage((x*110)+boundaryRemover,0,110-boundaryRemover,120)));
				} else if(x == 9) {
					numbers.put(realNum[x], ImageHandler.scaleImage(60, 60, textSprite.getSubimage(((x-9)*110)+boundaryRemover,120,110-boundaryRemover,120)));
				}
			}
			
			this.alphabets.get("f");
			System.out.println("f gotten");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
}
