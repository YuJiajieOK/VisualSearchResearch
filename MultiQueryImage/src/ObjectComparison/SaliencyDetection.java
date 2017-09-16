package ObjectComparison;

import java.io.InputStream;
import java.util.ArrayList;

public class SaliencyDetection {
	
	static int [][]ImageMatrix = null;
	
	static ArrayList<Integer> adhPoint = null;
	
	public static ArrayList<ArrayList<Integer>> newSalienceDetection(int sm[][],int thrshd,float fv[][],int numOfBins) {
		ArrayList<ArrayList<Integer >> saliencyObject = new ArrayList<ArrayList<Integer>>();
		
		adhPoint = new ArrayList<Integer>();
		int max[] = new int[4];
		int n = -1;
		max = tools.Maximum1(sm,fv,numOfBins);
		while(tools.Maximum(sm)[0]>thrshd){//Each of the loop will produce a salience object
			ArrayList<ArrayList<Integer>> T = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> oneComponent = new ArrayList<Integer>();
			ArrayList<Integer> forT  = null;
			ArrayList<Integer> peak = new ArrayList<Integer>();
			max = tools.Maximum(sm);			
			oneComponent.add(max[1]);
//			Adding the peak point(adherent point)into bins        	
			peak.add(max[1]);
			adhPoint.add(max[1]);
			T.add(peak);
			sm[tools.address2index(sm, peak.get(0))[0]][tools.address2index(sm, peak.get(0))[1]] = 0;
			n++;
			int RowOfT = 1;
			while (T.size()!=0) {// Each of the loop is representing each iteration on searching the salience neighbor
				ArrayList<Integer> componentForT = new ArrayList<Integer>();	
				for (int j = 0; j<T.get(RowOfT-1).size(); j++){
						int [][]neighbourhood = null;
						neighbourhood = tools.nbs(sm,tools.address2index(sm,T.get(RowOfT-1).get(j))[0], tools.address2index(sm,T.get(RowOfT-1).get(j))[1]);
						
						for (int m = 0; m<neighbourhood.length;m++){
							if (sm[neighbourhood[m][0]][neighbourhood[m][1]]>=thrshd) {
								oneComponent.add(tools.index2address(sm, neighbourhood[m][0], neighbourhood[m][1]));
//								Adding the neighbors of the adherent point into the Feature Vector and the bins
								componentForT.add(tools.index2address(sm, neighbourhood[m][0], neighbourhood[m][1]));
								sm[neighbourhood[m][0]][neighbourhood[m][1]] = 0;	
								forT = componentForT;						
							}
						}
					}
					T.add(forT);
					forT = new ArrayList<Integer>();
					if (T.get(RowOfT)==null||T.get(RowOfT).isEmpty()==true) {
						T.clear();
					}
					else {
						RowOfT++;
					}		
			}
			saliencyObject.add(oneComponent);
		}
	
		return saliencyObject;	
	}
	
//	public static int adPointSelection(ArrayList<ArrayList<Integer>> SalienceObjects,int[][]sm, int[][] featureVector,int featureNumber, int object, int NumBins) {
	public static int adPointSelection(ArrayList<ArrayList<Integer>> SalienceObjects,int[][]sm,  int object) {	
	    // This function returns the address of the adherent point
		// SalienceObjects is the ArrayList with all the salience objects
		// sm is the saliecny map
		//object is the row number of the ArrayList. It is also representing a saliency object
		int adpAddress = 0;
		int row = 0;
		int col = 0;
		int smcol = sm[0].length;
		int salienceT = SalienceObjects.get(object).get(0);
		ArrayList<Integer> binNum = new ArrayList<Integer>();
//		for (int i = 0; i<NumBins; i++){
//			binNum.add(i);
//			bin.add(binNum);
//		}		
		for (int i = 0; i<SalienceObjects.get(object).size();i++){
//			filled up the bin
//			bin.get(featureVector[SalienceObjects.get(object).get(i)][featureNumber]).add(SalienceObjects.get(object).get(i));			
			row = SalienceObjects.get(object).get(i)/smcol;
			col = SalienceObjects.get(object).get(i)%smcol;
			if (salienceT<sm[row][col]) {
				salienceT = sm[row][col];
				adpAddress = SalienceObjects.get(object).get(i);
			}
		}
		return adpAddress;		
	}

}
