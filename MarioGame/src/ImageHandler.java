import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageHandler {
	
	private static Mario_Text mt = new Mario_Text();
	
	public ImageHandler() {
	}
	
	//scale up/down image by relative factor
	public static BufferedImage scaleImage(BufferedImage image, int scaleHeight, int scaleWidth, int scaleUpOrDown) {
		BufferedImage outImage;
		//scale up if scaleUpOrDown = 0
		if(scaleUpOrDown == 0)
			outImage = new BufferedImage(image.getWidth()*scaleWidth,image.getHeight()*scaleHeight, image.getType());
		else
			outImage = new BufferedImage(image.getWidth() / scaleWidth, image.getHeight() / scaleHeight, image.getType());
		Graphics2D g2d = outImage.createGraphics();
		if(scaleUpOrDown == 0)
			g2d.drawImage(image,0,0,image.getWidth()*scaleWidth,image.getHeight()*scaleHeight,null);
		else
			g2d.drawImage(image,0,0,image.getWidth() / scaleHeight, image.getHeight() / scaleHeight, null);
		g2d.dispose();
		
		return outImage;
	}
	
	//scale image by absolute number
	public static BufferedImage scaleImage(int height, int width, BufferedImage image) {
		BufferedImage outImage = new BufferedImage(width, height, image.getType());
		Graphics2D g2d = outImage.createGraphics();
		g2d.drawImage(image,0,0,width,height,null);
		g2d.dispose();
		
		return outImage;
	}
	
	public static BufferedImage makeTransparent(Color colorToChange, BufferedImage image) {
		BufferedImage outImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		Graphics2D g = outImage.createGraphics();
		Color transparent = new Color((float)0.0,(float)0.0,(float)0.0,(float)0.0);
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				BufferedImage subImage = image.getSubimage(x, y, 1, 1);
				
				if(subImage.getRGB(0,0) == colorToChange.getRGB()) {
					g.setColor(transparent);
					g.drawRect(x,y,1,1);
				}
				else {
					g.drawImage(subImage,x,y,1, 1, null);
				}
			}
		}
		return outImage;
	}
	
	public static BufferedImage makeImageTranslucent(BufferedImage source,
		      double alpha) throws IOException {
		    BufferedImage target = new BufferedImage(source.getWidth(),
		        source.getHeight(), java.awt.Transparency.TRANSLUCENT);
		    // Get the images graphics
		    Graphics2D g = target.createGraphics();
		    // Set the Graphics composite to Alpha
		    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
		        (float) alpha));
		  //  g.drawImage(source, null, 0, 0);
		    BufferedImage transparentBlock = ImageHandler.scaleImage(1, 1, ImageIO.read(new File("transparentBlock.png")));
		    // Draw the image into the prepared reciver image
		    for(int x = 0; x < source.getWidth(); x++) {
		    	for(int y = 0; y < source.getHeight(); y++) {
		    		if(source.getRGB(x, y) == Color.white.getRGB()) {
		    			g.drawImage(transparentBlock, null, x, y);
		    		} else {
		    			g.drawImage(source.getSubimage(x, y, 1, 1), x, y, null);
		    		}
		    	}
		    }
		    // let go of all system resources in this Graphics
		    g.dispose();
		    // Return the image
		    return target;
		  }
	
	public static BufferedImage getBackground(String filePath) throws IOException {
		return ImageHandler.scaleImage(Window.HEIGHT, Window.WIDTH, ImageIO.read(new File(filePath)));
	}
	
	public static BufferedImage reverseImage(BufferedImage image) throws IOException {
		BufferedImage outImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		Graphics2D g = outImage.createGraphics();
		
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				g.drawImage(image.getSubimage(x, y, 1, 1), image.getWidth() - x, y, null);
			}
		}
		
		g.dispose();
		return outImage;
	}
	
	public static String marioTextToImage(String word) {
		word = word.toLowerCase();
		String file = "C:\\Mario Final\\Text\\finished.png";
		BufferedImage outImage = new BufferedImage(word.length()*75,75,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = outImage.createGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, outImage.getWidth(), outImage.getHeight());
		for(int i = 0; i < word.length(); i++) {
		//	System.out.println(word.substring(i,i+1));
			g.drawImage(ImageHandler.scaleImage(outImage.getHeight(), outImage.getHeight(), mt.alphabets.get(word.substring(i,i+1))), i*outImage.getHeight(), 0, null);
		//	g.drawImage(ImageHandler.scaleImage(75, 75, mt.alphabets.get(word.substring(i,i+1))), i*75, 0, null);
		//	g.drawImage(mt.alphabets.get(word.substring(i,i+1)), i*75, 0, null);
		}
		g.dispose();
		try {
			ImageIO.write(outImage, "jpg", new File(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
	
}