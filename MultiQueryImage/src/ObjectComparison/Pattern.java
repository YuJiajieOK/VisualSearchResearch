package ObjectComparison;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Pattern {
	
	
	public static ArrayList<ArrayList<Integer>> PatternGenerators(int sm[][],ArrayList<ArrayList<Integer>> saliencyBlocks, int r) {
		
		ArrayList<ArrayList<Integer>> PGS = new ArrayList<ArrayList<Integer>>();
//		ArrayList<ArrayList<Integer>> saliencyBlocks = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> adhPoints = new ArrayList<Integer>();
//		saliencyBlocks = SaliencyDetection.newSalienceDetection(sm, thrshd, fr,fg,fb,numOfbins);
		adhPoints = SaliencyDetection.adhPoint;
		int numbOfPatterns = adhPoints.size();
	    for (int i = 0; i<numbOfPatterns; i++){
	    	ArrayList<Integer> onePattern = new ArrayList<Integer>();
	    	onePattern = Neighbour.spacialNb(sm, adhPoints, i, saliencyBlocks, r);
	    	PGS.add(onePattern);
	    }
		return PGS;		
	}
	
	public static ArrayList<ArrayList<Integer>> NewPatternGenerators(ArrayList<ArrayList<Integer>> saliencyBlocks, int numbers) {
		ArrayList<ArrayList<Integer>> PGS = new ArrayList<ArrayList<Integer>>();
		int numOfPatterns = saliencyBlocks.size();
		int steplength= 0;
		for (int i = 0; i<numOfPatterns; i++){
			ArrayList<Integer> onePattern = new ArrayList<Integer>();
			steplength = saliencyBlocks.get(i).size()/numbers;
			if (steplength<1) {
				onePattern.addAll(saliencyBlocks.get(i));
			}
			else {
				for(int j = 0; j<numbers;j++){
					onePattern.add(saliencyBlocks.get(i).get(j*steplength));
				}
			}
			PGS.add(onePattern);
		}
		
		return PGS;		
	}

	public static ArrayList<ArrayList<Integer>> SegPatternGenerators(int sm[][],ArrayList<ArrayList<Integer>> saliencyBlocks, int r) {
		ArrayList<ArrayList<Integer>> PGS = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<Integer> oneObject = new ArrayList<Integer>();
		
		for (int i = 0; i<saliencyBlocks.size(); i++){
			if (saliencyBlocks.get(i).size()>=Math.pow(r,2)) {
				ArrayList<Integer>onePattern = new ArrayList<Integer>();
				ArrayList<Integer> adhPoint = new ArrayList<Integer>();
				oneObject.addAll(saliencyBlocks.get(i));
				Collections.sort(oneObject);
				adhPoint.add(oneObject.get(oneObject.size()/2));
				onePattern = Neighbour.spacialNb(sm, adhPoint, 0, saliencyBlocks, r);
				PGS.add(onePattern);
			}
		}
		return PGS;		
	}

	
	

}
