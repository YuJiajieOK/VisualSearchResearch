package ObjectComparison;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class readTxt {	
	public static float[][] Readtext(File m, int row, int col){
		float[][] F = new float [row][col];
		

		  FileReader fileReader=null;
		  BufferedReader bufferedReader=null;
		  String s;
		  if(!m.exists()){
		   System.out.println("Text file is not exist.");
		  }
		  else{
		   try {
		    fileReader=new FileReader(m);
		    bufferedReader=new BufferedReader(fileReader);
//		    F=new float [row][col];
		    int i = 0;
		    while((s=bufferedReader.readLine())!=null){		        
			     String s1[]=s.split(" ");//Separate the string by "," and save it in an array			     
			     int j = 0;
			     while(j<=col-1){			    	 
			    	 F[i][j]=Float.parseFloat(s1[j]);	
			    	 j++;
			     }
			     i++;
			}
		   }catch (Exception e) {
			    e.printStackTrace();
		   }		   
	     }
		  //Convert string to int

//		  for (int i = 0; i<row; i++){
//			  for (int j = 0; j<col; j++){
//				  D[i][j] =  Float.parseFloat(F[i][j]);
//			  }
//		  }
		  return F;
    }
	
	public static int [][] colorValue(InputStream image, int l ) {
		
		int col = 0;
		int row = 0;
		
		BufferedImage img = null;
		try {
			 img = ImageIO.read(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		col = img.getWidth();
	    row = img.getHeight();
		
		int color[][] = new int[row][col];
		
		for (int i = 0;i<row;i++){
	          for (int j = 0;j<col;j++){
	          int imageRGB = img.getRGB(j,i);
	          Color imageColor = new Color(imageRGB);
	          if (l==1){
	        	  color[i][j] = imageColor.getRed();  
	          }
	          else if(l==2){
	        	  color[i][j] = imageColor.getGreen(); 
	          }
	          else if(l==3){
	        	  color[i][j] = imageColor.getBlue();
			  }
	          else {
				System.out.println("No such color level(Parameter error)");
			}
	           
	           }
	       }
		
		return color;
		
	}
	
//public static float[][] ReadFeatureFile(File f, int row, int col ){
//	float [][]featureMatrix = new float [row][col];
//	
//	
//}
                       
public static float [][] floatcolorValue(InputStream image, int l ) {
		
		int col = 0;
		int row = 0;
		
		BufferedImage img = null;
		try {
			 img = ImageIO.read(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		col = img.getWidth();
	    row = img.getHeight();
		
		float color[][] = new float[row][col];
		
		for (int i = 0;i<row;i++){
	          for (int j = 0;j<col;j++){
	          int imageRGB = img.getRGB(j,i);
	          Color imageColor = new Color(imageRGB);
	          if (l==1){
	        	  color[i][j] = (float)imageColor.getRed();  
	          }
	          else if(l==2){
	        	  color[i][j] = (float)imageColor.getGreen(); 
	          }
	          else if(l==3){
	        	  color[i][j] = (float)imageColor.getBlue();
			  }
	          else {
				System.out.println("No such color level(Parameter error)");
			}
	           
	           }
	       }
		return color;
		
	}
	
	
	
}

