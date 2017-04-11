


import java.awt.image.*;
import java.io.*;
import javax.imageio.*;



public class Main {

	public static void main(String[] args){
		
		String inputImagePath = args[0];
		String numOfColumensOutput = args[1];
		String numOfRoesOutput = args[2];
		String energyType = args[3];
		String outputImagePath = args[4];
		
		// Load input image:
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(inputImagePath));
		} catch (IOException e) {
			System.out.println("ERROR: can not read the input image.");
			return;
		}
		
		// Find minimum seam
		SeamFinder seamFinder = new SeamFinder();
		int[] minSeam = seamFinder.findMinSeamIndexes(img, true, true);
		
		System.out.println("");
	}
	
	
}
