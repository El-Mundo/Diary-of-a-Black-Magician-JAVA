package base;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class FileIO {
	
	/*读取图片基本方法(路径,分割行数,分割列数)*/
	public static BufferedImage[] getImage(String PATH, int line, int column) {
		BufferedImage[] output = new BufferedImage[line*column];
		BufferedImage source = null;
		try {
			source = ImageIO.read(new File(PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int w = source.getWidth()/column;
		int h = source.getHeight()/line;
		for(int l=0;l<line;l++) {
			int cl = l*column;
			for(int c=0;c<column;c++) {
				output[c+cl] = source.getSubimage(c*w, l*h, w, h);
			}
		}
		return output;
	}
	/*读取文本基本方法*/
	public static String[] readText(String PATH, int length) {
		String[] input = new String[length];
		int lineNumber = 0;
		try {
			Path path = Paths.get(PATH);
			BufferedReader reader = Files.newBufferedReader(path);
			String line;
			while((line = reader.readLine())!=null) {
				input[lineNumber] = line;
				lineNumber++;
			}
		}catch (Exception e) {
			
		}
		return input;
	}
	/*存档格式检测*/
	public static void saveCheck(String[] save) {
		
	}
	public static BufferedImage changeHue(BufferedImage image, float[] factors, float[] offesets) {
		BufferedImage bi = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB_PRE);
		RescaleOp filter = new RescaleOp(factors, offesets, null);
		return filter.filter(bi, null);
	}
	public static BufferedImage changeHue(BufferedImage image, float[] factors) {
		float[] f = {0,0,0};
		return changeHue(image, factors, f);
	}
}
