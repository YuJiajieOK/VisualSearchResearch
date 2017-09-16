package ObjectComparison;

import java.util.ArrayList;

public class Neighbour {
	
	static ArrayList<ArrayList<Integer>> bins4r = null;
	static ArrayList<ArrayList<Integer>> bins4g = null;
	static ArrayList<ArrayList<Integer>> bins4b = null;
	
	
	public static ArrayList<Integer> spacialNb(int m[][],ArrayList<Integer>adhPoints,int ad, ArrayList<ArrayList<Integer>> saliencyObjects,int r) {
		//m is the image matrix
		//adhPoints are the arraylist with all the adherent points
		//ad is the index in adhPoints
		//SaliencyObjects is the saliency objects been detected
		//r is the radius of the neighbor circle
		int col = m[0].length;
		int D = 0;
		ArrayList<Integer> nbs = new ArrayList<Integer>();
		nbs.add(adhPoints.get(ad));
		int index[] = new int[2];
	    index = tools.address2index(m, adhPoints.get(ad));
	    int rr = (int) Math.pow(r, 2);
	    // Add neighbors on the cross
	    for (int i = 1; i<=r;i++){
	    	if (saliencyObjects.get(ad).contains(adhPoints.get(ad)-i)==true) {
	    		nbs.add(adhPoints.get(ad)-i);
			}
	    	if (saliencyObjects.get(ad).contains(adhPoints.get(ad)+i)==true) {
	    		nbs.add(adhPoints.get(ad)+i);
			}
	    	if (saliencyObjects.get(ad).contains(adhPoints.get(ad)-i*col)==true) {
	    		nbs.add(adhPoints.get(ad)-i*col);
			}
	    	if (saliencyObjects.get(ad).contains(adhPoints.get(ad)+i*col)==true) {
		    	nbs.add(adhPoints.get(ad)+i*col);
			}
	    }	     
	    for (int i = 1; i<=r; i++){
	    	for (int j = 1;j<=r; j++){
	    		D = Math.abs((int) Math.pow(i, 2)-(int) Math.pow(j, 2));
	    		if (D<=rr&&saliencyObjects.get(ad).contains(tools.index2address(m, index[0]-i, index[1]-j))==true) {
		    		// Go up-left
					nbs.add(tools.index2address(m, index[0]-i, index[1]-j));
				}
	    		else if (D<=rr&&saliencyObjects.get(ad).contains(tools.index2address(m, index[0]-i, index[1]+j))==true) {
		    	    // Go up-right
					nbs.add(tools.index2address(m, index[0]-i, index[1]+j));
				}
	    		else if (D<=rr&&saliencyObjects.get(ad).contains(tools.index2address(m, index[0]+i, index[1]-j))==true) {
	    			// Go down-left
					nbs.add(tools.index2address(m, index[0]+i, index[1]-j));
				}
	    		else if (D<=rr&&saliencyObjects.get(ad).contains(tools.index2address(m, index[0]+i, index[1]+j))==true) {
	    			// Go down-right
					nbs.add(tools.index2address(m, index[0]+i, index[1]+j));
				}	    		
	    		else {
					break;
				}
	    	}
	    }
		return nbs;
	}
	
//	public static ArrayList<Integer> RankedSM(int m[][], ArrayList<Integer> adhPoints, int adN, ArrayList<ArrayList<Integer>> saliencyObjects, int rankThreshold) {
//		ArrayList<Integer> RSM = new ArrayList<Integer>();
//		
//		
//		
//		return RSM;		
//	}
	

}
