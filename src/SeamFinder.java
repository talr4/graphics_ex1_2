import java.awt.image.BufferedImage;

public class SeamFinder
{
	
	private int[][] computeDynamicProgrammingMap(int[][] energy, int numOfRows, int numOfColumns, boolean allowDiagonalSeam)
	{
		int[][] dynamicProgrammingMap = new int[numOfRows][numOfColumns];
		
		// First row: the same values as the energy map:
		for(int column = 0; column < numOfColumns; column ++)
		{
			dynamicProgrammingMap[0][column] = energy[0][column];
		}
		
		// Compute Dynamic Programming map from second row:
		for(int row = 1; row < numOfRows; row++)
		{
			
			for(int column = 0; column < numOfColumns ; column++)
			{		
				
				
				int min = dynamicProgrammingMap[row-1][column];
				
				if(allowDiagonalSeam)
				{
					if(column != 0)
					{
						min = Math.min(min, dynamicProgrammingMap[row-1][column-1]);
					}
					if(column != numOfColumns-1)
					{
						min = Math.min(min, dynamicProgrammingMap[row-1][column+1]);
					}
				}

				
				dynamicProgrammingMap[row][column] += energy[row][column];
				dynamicProgrammingMap[row][column] += min;
				
				
			}
		}
		
		return dynamicProgrammingMap;
	}
	
	public int[] findMinSeamIndexes(BufferedImage img, boolean allowDiagonalSeam, boolean addEntropyToEnergy)
	{
				int numOfRows = img.getHeight();
				int numOfColumns =  img.getWidth();
				int[] seam = new int[img.getHeight()];
				
				// Compute energy map
				EnergyComputer energyComputer = new EnergyComputer();
				int[][] energy = energyComputer.compute(img);
				
				if(addEntropyToEnergy)
				{
					energyComputer.addEntropy(img, energy);
				}
				
				// Compute dynamic programming map
				int[][] dynamicProgrammingMap = computeDynamicProgrammingMap(energy, img.getHeight(), img.getWidth(), allowDiagonalSeam);
				
				// Compute min seam column index
				int minSeamValue = dynamicProgrammingMap[numOfRows-1][0];
				int meanSeamEndIndex = 0;
				for(int column =1; column < numOfColumns; column++)
				{
					if(dynamicProgrammingMap[numOfRows-1][column] < minSeamValue)
					{
						minSeamValue = dynamicProgrammingMap[numOfRows-1][column];
						meanSeamEndIndex = column;
					}
				}
				
				// Compute min seam
					for(int row = numOfRows-1; row >= 0 ; row--)
					{
						seam[row] = meanSeamEndIndex;
						
						// If diagonal seams are allowed, choose the column index for next row.
						if(allowDiagonalSeam)
						{
							if(row > 0)
							{
								int minUpperNeighborValue = dynamicProgrammingMap[row-1][meanSeamEndIndex];
								int minUpperNeighborIndex = meanSeamEndIndex;
								if(meanSeamEndIndex != 0)
								{
									if(dynamicProgrammingMap[row-1][meanSeamEndIndex-1] < minUpperNeighborValue)
									{
										minUpperNeighborValue = dynamicProgrammingMap[row-1][meanSeamEndIndex-1];
										minUpperNeighborIndex = meanSeamEndIndex-1;
									}
									
									if(dynamicProgrammingMap[row-1][meanSeamEndIndex+1] < minUpperNeighborValue)
									{
										minUpperNeighborValue = dynamicProgrammingMap[row-1][meanSeamEndIndex+1];
										minUpperNeighborIndex = meanSeamEndIndex+1;
									}
								}
								
								meanSeamEndIndex = minUpperNeighborIndex;		
							}
						}
				}
				
				return seam;
	}

}
