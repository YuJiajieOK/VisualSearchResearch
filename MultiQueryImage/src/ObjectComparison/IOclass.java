package ObjectComparison;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class IOclass {
	
	public static int [][]inputImage(File f, int layer){
		InputStream imageStream = null;
		try {
//			imageR = new FileInputStream("c:\\research\\IP_java\\IP\\src\\test.jpg");
//This is the path of the image
			imageStream = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch blockS
			e.printStackTrace();
		}
		int[][] image = null;		
		image = readTxt.colorValue(imageStream, layer);
		return image;
	}
	
	public static float [][]loadFeatures(File Featurefile, int row, int col) throws IOException {
		
		float [][]f = new float[row][col];
		
		 BufferedReader reader = new BufferedReader(new FileReader(Featurefile));
		 List<String> list = new ArrayList<String>();
		 Object[] array = null;
		 String tmp;
		 while ((tmp = reader.readLine() ) != null) {
			   list.add(tmp);
			}
		 float []t = new float [list.size()];
		 int []locationPair = null;
		 array = new String[list.size()];
		 array = list.toArray();
		  for (int i = 0; i<array.length;i++){
	    	   t[i]=Float.parseFloat((String) array[i]);
	    	   locationPair = tools.address2indexbycol(col, i);
	    	   f[locationPair[0]][locationPair[1]] = t[i];
	       }
		return f;
		
	}
	
	
	public static float [][]inputFeature(File f, int layer){
		InputStream imageStream = null;
		try {
//			imageR = new FileInputStream("c:\\research\\IP_java\\IP\\src\\test.jpg");
//This is the path of the image
			imageStream = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch blockS
			e.printStackTrace();
		}
		float [][] image = null;		
		image = readTxt.floatcolorValue(imageStream, layer);
		return image;
	}
	
}
