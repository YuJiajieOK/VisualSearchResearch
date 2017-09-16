package ObjectComparison;

import java.util.ArrayList;

public class ClassValueTest {
	static int max;
	static int featureVector[][];
	static ArrayList<ArrayList<Integer>> reFeatureVector;
	
	public static int bigloop(int m[][]){
		int row = m.length;
		int col = m[0].length;
		featureVector = new int[row*col][2];
		reFeatureVector = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<Integer> t = new ArrayList<Integer>();
		for(int i = 0; i<256; i++){
			t.add(i);
			reFeatureVector.add(t);
		}
		max = 0;
		int n = -1;
		for (int i = 0; i<row; i++){
			for (int j = 0; j<col; j++){
				n++;
				if(m[i][j]>max){
					max = m[i][j];					
				}
				featureVector[n][0] = n;
				featureVector[n][1] = m[i][j];
				reFeatureVector.get(m[i][j]).add(tools.index2address(m, i, j));
			}
		}

		return max;
		
	}

}
