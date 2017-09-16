package ObjectComparison;

import java.util.ArrayList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class MatrixReshape {
	public static int []Two2One(int [][]M){
		//convert 2D array into 1D array
		int row = M.length;
		int col = M[0].length;
		int[] OneDArray = new int[row*col];
		int address = -1;
		for (int i = 0; i<row; i++){
			for (int j = 0; j < col; j++){
				address ++;
				OneDArray [address] = M[i][j];
			}
		}		
		return OneDArray;		
	}
	
//	public static int [][] reScale(double [][]M, int newScale ) {
//		int row = M.length;
//		int col = M[0].length;
//		double [][] t = new double [row][col];
//		int [][] result = new int [row][col];
//		double max = 0;
//		max = tools.maxValue(M);
//		ArrayList<ArrayList<Integer>> listed  = new ArrayList<ArrayList<Integer>>();
//		for (int i = 0; i<row; i++){
//			for (int j = 0; j<col; j++){
//				t[i][j] = M[i][j]/(double)(max);
//				M[i][j] = (int) (t[i][j]*newScale);
//				listed.get(M[i][j]).add((Integer)(i*row+col));
//				//
//			}
//		}
//		result = M;
//		return result;	
//	}
	
	
	public static int mapping (double value, double max, int newScale){
		int mapped = 0;
		double t = 0;
		t = value/max;
		mapped= (int)(t*newScale);
		return mapped;	
	}
}
