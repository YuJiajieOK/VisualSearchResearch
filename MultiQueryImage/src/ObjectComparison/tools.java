package ObjectComparison;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class tools {
	
	static float featureVector[][] = null;
	
	static float maxf1 = 0;
	static float maxf2 = 0;
	static float maxf3 = 0;
	static float maxf4 = 0;
	
	static float minf1 = 0;	
	static float minf2 = 0;
	static float minf3 = 0;
	static float minf4 = 0;
	
	static float DSformerlayer [][] = null;
	
	static float DSfeature [][] = null;
	
//	static int numFeatures = 128;
//	
//	static float [][]maxmin = new float [numFeatures][2];// first col of the maxmin is max second col is min
	
//	static ArrayList<ArrayList<Integer>> bins4f1 = null;
//	static ArrayList<ArrayList<Integer>> bins4f2 = null;
//	static ArrayList<ArrayList<Integer>> bins4f3 = null;
//	static ArrayList<ArrayList<Integer>> bins4f4 = null;
	
	static int [][]RGBMatrix = null;
	
	public static int [][] downSamplingAft( int [][]img, float [][]currentF, int numFeatures) {
		
		int r = img.length;
		int c = img[0].length;		
		int [][] downsampled = new int[(int) Math.floor((float)r/2)][(int) Math.floor((float)c/2)];
		DSfeature = new float[((int) Math.floor((float)r/2))*((int) Math.floor((float)c/2))][numFeatures];
//		DSformerlayer = new float [(downsampled.length)*(downsampled[0].length)][2];
		int [][] t = img;		
	    int address = -1;
			int Trowindex = 1;			
			for (int m = 0; m<downsampled.length; m++){
				int Tcolindex = 1;
				for (int n =0; n<downsampled[0].length; n++){
					downsampled[m][n] = t[Trowindex][Tcolindex];
					address++;
					DSfeature[address] = currentF[Trowindex*c+Tcolindex];
					Tcolindex = Tcolindex+2;					
				}
				Trowindex = Trowindex+2;
			}					
		return downsampled;
		
	}
	
	public static int [][] downSampling( int [][]img) {
		
		int r = img.length;
		int c = img[0].length;		
		int [][] downsampled = new int[(int) Math.floor((float)r/2)][(int) Math.floor((float)c/2)];
//		DSformerlayer = new float [(downsampled.length)*(downsampled[0].length)][2];
		int [][] t = img;		
			int Trowindex = 1;			
			for (int m = 0; m<downsampled.length; m++){
				int Tcolindex = 1;
				for (int n =0; n<downsampled[0].length; n++){
					downsampled[m][n] = t[Trowindex][Tcolindex];
					Tcolindex = Tcolindex+2;					
				}
				Trowindex = Trowindex+2;
			}					
		return downsampled;
		
	}
	
	public static int [][] lastDownSampleing(int [][] img, float [][]formerFeature) {
		int r = img.length;
		int c = img[0].length;		
		int [][] downsampled = new int[(int) Math.floor((float)r/2)][(int) Math.floor((float)c/2)];
		DSformerlayer = new float [(downsampled.length)*(downsampled[0].length)][512];
		int [][] t = img;
		int Trowindex = 1;
		int featureIndex = 0;
		for (int m = 0; m<downsampled.length; m++){
			int Tcolindex = 1;
			for (int n =0; n<downsampled[0].length; n++){
				downsampled[m][n] = t[Trowindex][Tcolindex];
				DSformerlayer[featureIndex] = formerFeature[Trowindex*Tcolindex];
				featureIndex++;
				Tcolindex = Tcolindex+2;					
			}
			Trowindex = Trowindex+2;
		}			
		return downsampled;		
	}
	
	
	
	public static BufferedImage addBox(File file, int row, int col, ArrayList<ArrayList<Integer>> selectedObject, int width) {
        
		InputStream Inputimage = null;		
		try {
			Inputimage = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch blockS
			e.printStackTrace();
		}
		//Creat mask
		int [][] mask = new int[row][col];
		int maxrow = 0;
		int maxcol = 0;
		int minrow = row;
		int mincol = col;
		int []rowcol = new int[2];
		for (int r = 0; r<selectedObject.size();r++){
		for (int i = 0; i<selectedObject.get(r).size(); i++){
			rowcol = address2indexbycol(col, selectedObject.get(r).get(i));
			if (maxrow<rowcol[0]) {
				maxrow = rowcol[0];
			}
			if (maxcol<rowcol[1]) {
				maxcol = rowcol[1];
			}
			if (minrow>rowcol[0]) {
				minrow = rowcol[0];
			}
			if (mincol>rowcol[1]) {
				mincol = rowcol[1];
			}
		}
		// in case the bound of the box reach the edge of the image
		if (minrow-width<0) {
			minrow = width;
		}
		if (mincol-width<0){
			mincol = width;
		}
		if (maxrow+width>row) {
			maxrow = row-width;
		}
		if (maxcol+width>col) {
			maxcol = col-width;
		}
		
		for (int i = 0; i<row; i++){
			for (int j = 0; j<col; j++){		
				//bound the box edge
				if ((i>=minrow-width&&i<=minrow&&j>=mincol-width&&j<=maxcol+width)||(i>=minrow-width&&i<=maxrow+width&&j>=mincol-width&&j<=mincol)||(i>=minrow-width&&i<=maxrow+width&&j>=maxcol&&j<maxcol+width)||(j>=mincol-width&&j<=maxcol+width&&i<=maxrow+width&&i>=maxrow) ){
					mask[i][j] = 0;
				}
				else {
					mask[i][j] = 1;
				}
			}
		}
		}
		//Use mask creating output image
		BufferedImage Image = null;
		
		try {
			Image = ImageIO.read(Inputimage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		int [][]finalMatrix = new int [row][col];
		BufferedImage outputImage  = new BufferedImage(col, row, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i<row; i++){
			for (int j = 0; j<col; j++){
				finalMatrix[i][j] = Image.getRGB(j, i);
				finalMatrix[i][j] = finalMatrix[i][j]*mask[i][j];
//				if (finalMatrix[i][j]<0) {
//					finalMatrix[i][j]=0;
//				}
				outputImage.setRGB(j,i,finalMatrix[i][j]);
			}
		}
		return outputImage;
	}
	
	
	public static float normalize(float protoValue, float min, float max){
		float ZerotoOne = 0;
		
		if (max==min) {
			ZerotoOne = 0;
		}		
		else if (min<0) {
			ZerotoOne = (protoValue-min)/(max-min);
		}
	    else {
			ZerotoOne = protoValue/max;
		}
		
		return ZerotoOne;
	}
	
	public static float [][] normalization(float [][]fv, int numFeatures) {
		
		int row = fv.length;

		float [][]maxmin = new float [numFeatures][2];		
		for (int i = 0; i<numFeatures; i++){
			maxmin[i][0] = fv[0][i];
			maxmin[i][1] = fv[0][i];
		}
		for (int i = 0; i<fv.length; i++){
			  for (int k = 0; k<numFeatures; k++){
					if (fv[i][k]>maxmin[k][0]) {
						maxmin[k][0] = fv[i][k];
					}
					if(fv[i][k]<maxmin[k][1]){
						maxmin[k][1] = fv[i][k];
					}				
				}
		}

			for (int n = 0; n<row; n++){		
				for (int k = 0; k<numFeatures; k++){
					fv[n][k] = normalize(fv[n][k], maxmin[k][1], maxmin[k][0]);
				}
		}
		return fv;		
	}
	
	public static int index2address(int m[][], int i, int j) {
		
		int address = 0;
		int col = m[0].length;		
		address = i*col+j;	
		return address;		
	}
	
	
	public static int []Maximum(int [][]m) {
		
		int row = m.length;
		int col = m[0].length;
		int t = m[0][0];
		//t will be the larger value
		int ir = 0;
		//ir will be the larger value's row index
		int ic = 0;		
		int []retValue = new int[4];
		for (int i = 0;i<row; i++){
			for (int j = 0; j<col; j++){
//				bins.get(m[i][j]).add(index2address(m, i, j));
				if(m[i][j]>t){
					t = m[i][j];
					ir = i;
					ic = j;
				}
			}
		}
		retValue[0] = t;//max value
		retValue[1] = index2address(m, ir, ic);//address
		retValue[2] = ir; //row index
		retValue[3] = ic; //col index
		return retValue;
	}
	
	public static int []Maximum1( int [][]m, float [][]fv, int ttbins) {

		//m[][] is the saliency map
		int row = m.length;
		int col = m[0].length;
		int t = m[0][0];
		
		//initialize the maxmin of feature values		
		int count = 0;
		int count1 = 0;
		//t will be the larger value
		int ir = 0;
		//ir will be the larger value's row index
		int ic = 0;
		//ic will be the larger value's col index
		
		int []retValue = new int[4];
		for (int i = 0;i<row; i++){
			for (int j = 0; j<col; j++){
				//Read the color value into a matrix by the way		
				if(m[i][j]>t){
					t = m[i][j];
					ir = i;
					ic = j;
				}
			}
		}
		retValue[0] = t;//max value
		retValue[1] = index2address(m, ir, ic);//address
		retValue[2] = ir; //row index
		retValue[3] = ic; //col index
		
		return retValue;
	}
	

	
	public static int[] address2index(int m[][], int address) {
		
		int index[] = new int[2];//index[0] = i index[1] = j;
		int col = m[0].length;
		index[0] = address/col;//row
		index[1] = address%col;//col
		
		return index;
	}
	
	public static int []address2indexbycol(int col, int address){
		int index[] = new int[2];
		index[0] = address/col;//row
		index[1] = address%col;//col
		
		return index;		
	}

	
	public static int[][] nbs(int m[][], int r,int c) {
		//case1 r = 0 c = 0 left-up corner
		int [][]neighbours = null;
		int row = m.length;
		int col = m[0].length;
		if (r==0&&c==0){
			neighbours = new int[3][2];
			neighbours[0][0] = r;
			neighbours[0][1] = c+1;
			neighbours[1][0] = r+1;
			neighbours[1][1] = c;
			neighbours[2][0] = r+1;
			neighbours[2][1] = c+1;
		}
		//case2 r = 0 c = col-1 right-up corner
		else if (r==0&&c==col-1) {
			neighbours = new int[3][2];
			neighbours[0][0] = r;
			neighbours[0][1] = c-1;
			neighbours[1][0] = r+1;
			neighbours[1][1] = c-1;
			neighbours[2][0] = r+1;
			neighbours[2][1] = c;
		}
		//case3 r = row-1 c = 0 left-down corner
		else if (r==row-1&&c==0) {
			neighbours = new int[3][2];
			neighbours[0][0] = r-1;
			neighbours[0][1] = c;
			neighbours[1][0] = r-1;
			neighbours[1][1] = c+1;
			neighbours[2][0] = r;
			neighbours[2][1] = c+1;
		}
		//case4 r = row-1 c = col-1 right-down corner
		else if (r==row-1&&c==col-1) {
			neighbours = new int[3][2];
			neighbours[0][0] = r-1;
			neighbours[0][1] = c-1;
			neighbours[1][0] = r-1;
			neighbours[1][1] = c;
			neighbours[2][0] = r;
			neighbours[2][1] = c-1;
		}
		//case5 r = 0 0<c<col-1 top line
		else if (r==0&&c>0&&c<col-1) {
			neighbours = new int[5][2];
			neighbours[0][0] = r;
			neighbours[0][1] = c-1;
			neighbours[1][0] = r;
			neighbours[1][1] = c+1;
			neighbours[2][0] = r+1;
			neighbours[2][1] = c-1;
			neighbours[3][0] = r+1;
			neighbours[3][1] = c;
			neighbours[4][0] = r+1;
			neighbours[4][1] = c+1;
		}
		//case6 0<r<row-1 c = 0 left line
		else if (r>0&&r<row-1&&c==0) {
			neighbours = new int[5][2];
			neighbours[0][0] = r-1;
			neighbours[0][1] = c;
			neighbours[1][0] = r-1;
			neighbours[1][1] = c+1;
			neighbours[2][0] = r;
			neighbours[2][1] = c+1;
			neighbours[3][0] = r+1;
			neighbours[3][1] = c;
			neighbours[4][0] = r+1;
			neighbours[4][1] = c+1;
		}
		//case7 0<r<row-1 c = col-1 right line
		else if (r>0&&r<row-1&&c==col-1) {
			neighbours = new int[5][2];
			neighbours[0][0] = r-1;
			neighbours[0][1] = c-1;
			neighbours[1][0] = r-1;
			neighbours[1][1] = c;
			neighbours[2][0] = r;
			neighbours[2][1] = c-1;
			neighbours[3][0] = r+1;
			neighbours[3][1] = c-1;
			neighbours[4][0] = r+1;
			neighbours[4][1] = c;
		}
		//case8 r = row-1 0<c<col-1 bottom line
		else if (r==row-1&&c>0&&c<col-1) {
			neighbours = new int[5][2];
			neighbours[0][0] = r-1;
			neighbours[0][1] = c-1;
			neighbours[1][0] = r-1;
			neighbours[1][1] = c;
			neighbours[2][0] = r-1;
			neighbours[2][1] = c+1;
			neighbours[3][0] = r;
			neighbours[3][1] = c-1;
			neighbours[4][0] = r;
			neighbours[4][1] = c+1;
		}
		//case9 inside
		else {
			neighbours = new int [8][2];
			neighbours[0][0] = r-1;
			neighbours[0][1] = c-1;
			neighbours[1][0] = r-1;
			neighbours[1][1] = c;
			neighbours[2][0] = r-1;
			neighbours[2][1] = c+1;
			neighbours[3][0] = r;
			neighbours[3][1] = c-1;
			neighbours[4][0] = r;
			neighbours[4][1] = c+1;
			neighbours[5][0] = r+1;
			neighbours[5][1] = c-1;
			neighbours[6][0] = r+1;
			neighbours[6][1] = c;
			neighbours[7][0] = r+1;
			neighbours[7][1] = c+1;
		}		
		return neighbours;	
	}
	
}
