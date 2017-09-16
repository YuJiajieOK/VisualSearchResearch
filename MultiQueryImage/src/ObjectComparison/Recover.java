package ObjectComparison;

import java.util.ArrayList;

public class Recover {
	
	public static int [][]SalienceObject2Image(ArrayList<ArrayList<Integer>> SO, int n, int row, int col){
		int image[][] = new int [row][col];

		for (int i = 0; i<SO.get(n).size(); i++){
			int rowN = tools.address2index(image, SO.get(n).get(i))[0];
			int colN = tools.address2index(image, SO.get(n).get(i))[1];
			image[rowN][colN] = 255;
		}
	return image;
	}
	
	public static int [][]CandObject2Image(ArrayList<Integer> finalPixels, int row, int col){
		int image[][] = new int [row][col];
		for (int i = 0; i<finalPixels.size(); i++){
			int rowN = tools.address2index(image, finalPixels.get(i))[0];
			int colN = tools.address2index(image, finalPixels.get(i))[1];
			image[rowN][colN] = 255;
		}
	return image;
	}
	
}
