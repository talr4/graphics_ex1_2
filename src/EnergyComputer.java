

import java.awt.*;
import java.awt.image.*;

public class EnergyComputer
{
	
	public int[][] compute(BufferedImage img)
	{		
		int numOfRows = img.getHeight();
		int numOfColumns =  img.getWidth();
		int[][] energy = new int[numOfRows][numOfColumns];
		
		for(int row = 0; row < numOfRows; row++)
		{
			
			for(int column = 0; column < numOfColumns ; column++)
			{
				Color currentPixel = new Color(img.getRGB(column, row));
				int blue = currentPixel.getBlue();
				int green = currentPixel.getGreen();
				int red = currentPixel.getRed();
				int gradient = 0, numOfNeighbors = 0;
				
				// Upper Neighbors
				if(row != 0)
				{
					// neighbor 1
					if (column != 0)
					{						
						Color neighbor1 = new Color(img.getRGB(column-1, row-1));
						gradient += computeDifferenceFromSpecificPixel(blue, green, red, neighbor1);
						numOfNeighbors++;
					}

					// neighbor 2
					Color neighbor2 = new Color(img.getRGB(column, row-1));
					gradient += computeDifferenceFromSpecificPixel(blue, green, red, neighbor2);
					numOfNeighbors++;
					
					// neighbor 3
					if (column != numOfColumns-1)
					{					
						Color neighbor3 = new Color(img.getRGB(column+1, row-1));
						gradient += computeDifferenceFromSpecificPixel(blue, green, red, neighbor3);
						numOfNeighbors++;
					}
				}
				
				// Lower Neighbors
				if(row != numOfRows-1)
				{
					// neighbor 4
					if (column != 0)
					{						
						Color neighbor4 = new Color(img.getRGB(column-1, row+1));
						gradient += computeDifferenceFromSpecificPixel(blue, green, red, neighbor4);
						numOfNeighbors++;
					}

					// neighbor 5
					Color neighbor5 = new Color(img.getRGB(column, row+1));
					gradient += computeDifferenceFromSpecificPixel(blue, green, red, neighbor5);
					numOfNeighbors++;
					
					// neighbor 6
					if (column != numOfColumns-1)
					{
						
						Color neighbor6 = new Color(img.getRGB(column+1, row+1));
						gradient += computeDifferenceFromSpecificPixel(blue, green, red, neighbor6);
						numOfNeighbors++;
					}
				}
				
				// Middle Neighbors
				
				// neighbor 7
				if(column != 0)
				{
					Color neighbor7 = new Color(img.getRGB(column-1, row));
					gradient += computeDifferenceFromSpecificPixel(blue, green, red, neighbor7);
					numOfNeighbors++;
				}
				
				// neighbor 8
				if (column != numOfColumns-1)
				{
					Color neighbor8 = new Color(img.getRGB(column+1, row));
					gradient += computeDifferenceFromSpecificPixel(blue, green, red, neighbor8);
					numOfNeighbors++;
				}
				
				energy[row][column] = gradient/numOfNeighbors;
				
			}
		}
		
		return energy;
	}
	
	private int computeDifferenceFromSpecificPixel(int currentPixelBlue, int currentPixelGreen, int currentPixelRed, Color neighbor)
	{
		int green, blue, red, difference = 0;
		blue = neighbor.getBlue();
		green = neighbor.getGreen();
		red = neighbor.getRed();
		difference += (Math.abs(currentPixelBlue - blue) + Math.abs(currentPixelGreen - green) + Math.abs(currentPixelRed - red))/3;
		return difference;
	}
	
	public int[][] addEntropy(BufferedImage img, int[][] energy)
	{
		int numOfRows = img.getHeight();
		int numOfColumns =  img.getWidth();
		int[][] entropy = new int[numOfRows][numOfColumns];
		for(int row = 0; row < numOfRows; row++)
		{
			
			for(int column = 0; column < numOfColumns ; column++)
			{			
			
				double p , greyScale,  currentEntropy=0;
				double sumOfGreyScaleWindowPixels = computeSumOfGreyScaleWindowPixels (img, row, column);	
							
				// Current pixel
				Color currentPixel = new Color(img.getRGB(column, row));
				greyScale = (currentPixel.getBlue() + currentPixel.getGreen() + currentPixel.getRed())/3;
				p = greyScale/sumOfGreyScaleWindowPixels;
				currentEntropy += p*(Math.log(p));
				
				// Upper Neighbors
				if(row != 0)
				{
					// neighbor 1
					if (column != 0)
					{						
						Color neighbor1 = new Color(img.getRGB(column-1, row-1));
						greyScale = (currentPixel.getBlue() + currentPixel.getGreen() + currentPixel.getRed())/3;
						p = greyScale/sumOfGreyScaleWindowPixels;
						currentEntropy += p*(Math.log(p));
					}

					// neighbor 2
					Color neighbor2 = new Color(img.getRGB(column, row-1));
					greyScale = (neighbor2.getBlue() + neighbor2.getGreen() + neighbor2.getRed())/3;
					p = greyScale/sumOfGreyScaleWindowPixels;
					currentEntropy += p*(Math.log(p));
					
					// neighbor 3
					if (column != numOfColumns-1)
					{					
						Color neighbor3 = new Color(img.getRGB(column+1, row-1));
						greyScale = (neighbor3.getBlue() + neighbor3.getGreen() + neighbor3.getRed())/3;
						p = greyScale/sumOfGreyScaleWindowPixels;
						currentEntropy += p*(Math.log(p));
					}
				}
				
				// Lower Neighbors
				if(row != numOfRows-1)
				{
					// neighbor 4
					if (column != 0)
					{						
						Color neighbor4 = new Color(img.getRGB(column-1, row+1));
						greyScale = (neighbor4.getBlue() + neighbor4.getGreen() + neighbor4.getRed())/3;
						p = greyScale/sumOfGreyScaleWindowPixels;
						currentEntropy += p*(Math.log(p));
					}

					// neighbor 5
					Color neighbor5 = new Color(img.getRGB(column, row+1));
					greyScale = (neighbor5.getBlue() + neighbor5.getGreen() + neighbor5.getRed())/3;
					p = greyScale/sumOfGreyScaleWindowPixels;
					currentEntropy += p*(Math.log(p));
					
					// neighbor 6
					if (column != numOfColumns-1)
					{
						Color neighbor6 = new Color(img.getRGB(column+1, row+1));
						greyScale = (neighbor6.getBlue() + neighbor6.getGreen() + neighbor6.getRed())/3;
						p = greyScale/sumOfGreyScaleWindowPixels;
						currentEntropy += p*(Math.log(p));
					}
				}
				
				// Middle Neighbors
				
				// neighbor 7
				if(column != 0)
				{
					Color neighbor7 = new Color(img.getRGB(column-1, row));
					greyScale = (neighbor7.getBlue() + neighbor7.getGreen() + neighbor7.getRed())/3;
					p = greyScale/sumOfGreyScaleWindowPixels;
					currentEntropy += p*(Math.log(p));
				}
				
				// neighbor 8
				if (column != numOfColumns-1)
				{
					Color neighbor8 = new Color(img.getRGB(column+1, row));
					greyScale = (neighbor8.getBlue() + neighbor8.getGreen() + neighbor8.getRed())/3;
					p = greyScale/sumOfGreyScaleWindowPixels;
					currentEntropy += p*(Math.log(p));
				}
				
				energy[row][column] += -Math.abs((int)currentEntropy);	
			}
		}
		
		return entropy;
	}
	
	
	private int computeSumOfGreyScaleWindowPixels (BufferedImage img, int row, int column)
	{
			int numOfRows = img.getHeight();
			int numOfColumns =  img.getWidth();
				int sum = 0;
				int greyScale;
				
				// Current pixel
				Color currentPixel = new Color(img.getRGB(column, row));
				greyScale = (currentPixel.getBlue() + currentPixel.getGreen() + currentPixel.getRed())/3;
				sum += greyScale;
				
				// Upper Neighbors
				if(row != 0)
				{
					// neighbor 1
					if (column != 0)
					{						
						Color neighbor1 = new Color(img.getRGB(column-1, row-1));
						greyScale = (neighbor1.getBlue() + neighbor1.getGreen() + neighbor1.getRed())/3;
						sum += greyScale;
					}

					// neighbor 2
					Color neighbor2 = new Color(img.getRGB(column, row-1));
					greyScale = (neighbor2.getBlue() + neighbor2.getGreen() + neighbor2.getRed())/3;
					sum += greyScale;
					
					// neighbor 3
					if (column != numOfColumns-1)
					{					
						Color neighbor3 = new Color(img.getRGB(column+1, row-1));
						greyScale = (neighbor3.getBlue() + neighbor3.getGreen() + neighbor3.getRed())/3;
						sum += greyScale;
					}
				}
				
				// Lower Neighbors
				if(row != numOfRows-1)
				{
					// neighbor 4
					if (column != 0)
					{						
						Color neighbor4 = new Color(img.getRGB(column-1, row+1));
						greyScale = (neighbor4.getBlue() + neighbor4.getGreen() + neighbor4.getRed())/3;
						sum += greyScale;
					}

					// neighbor 5
					Color neighbor5 = new Color(img.getRGB(column, row+1));
					greyScale = (neighbor5.getBlue() + neighbor5.getGreen() + neighbor5.getRed())/3;
					sum += greyScale;
					
					// neighbor 6
					if (column != numOfColumns-1)
					{
						Color neighbor6 = new Color(img.getRGB(column+1, row+1));
						greyScale = (neighbor6.getBlue() + neighbor6.getGreen() + neighbor6.getRed())/3;
						sum += greyScale;
					}
				}
							
				// Middle Neighbors
				
				// neighbor 7
				if(column != 0)
				{
					Color neighbor7 = new Color(img.getRGB(column-1, row));
					greyScale = (neighbor7.getBlue() + neighbor7.getGreen() + neighbor7.getRed())/3;
					sum += greyScale;
				}
				
				// neighbor 8
				if (column != numOfColumns-1)
				{
					Color neighbor8 = new Color(img.getRGB(column+1, row));
					greyScale = (neighbor8.getBlue() + neighbor8.getGreen() + neighbor8.getRed())/3;
					sum += greyScale;;
				}
				
		return sum;
	}
	
}
