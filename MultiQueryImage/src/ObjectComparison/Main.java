package ObjectComparison;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.print.attribute.Size2DSyntax;
import javax.xml.crypto.Data;

import sun.misc.Sort;

public class Main {
	
	// Currently the program working using the 3rd layer features and down sample them to 5th layer in order to reduce the computation
	
	static float NB_epsilon = (float) 0.01;
	static float INS_epsilon = (float) 0.1;
	static int saliencyThreshold = 170;
	static float interSectionThreshold = (float) 0.3;
	static int numOfbins = 256;
	static int boxedge = 3;
    static int numbersOfpattern = 245;
	static int numFeatures = 18;
//	static int layerNum =3;
	static int objectNum = 0;
	
	
    static String imagePath = "C:\\research\\1000testing\\images\\";
    static String featurePath = "C:\\research\\1000testing\\f_18Processed\\";
    static String smPath = "C:\\research\\1000testing\\sm\\";
    static String output = "C:\\research\\1000testing\\results5\\";
    static String queryObjectPath = "C:\\research\\1000testing\\regionOfinterest\\";
    static String featureTitle = "objectDescriptions";
	
//    static String imagePath = "//home//jyu@acs.uwinnipeg.ca//1000testing//images//";
//    static String featurePath = "//home//jyu@acs.uwinnipeg.ca//1000testing//features//";
//    static String smPath = "//home//jyu@acs.uwinnipeg.ca//1000testing//sm//";
//    
//    static String output = "//home//jyu@acs.uwinnipeg.ca//1000testing//results//";
//    static String queryObjectPath = "//home//jyu@acs.uwinnipeg.ca//1000testing//regionOfinterest//";
	
	static int queryStart = 726;
	static int queryEnd = 999;
	
	/**
	 * @param args
	 */
	static int n;
	public static ArrayList<Integer> descriptiveNb(ArrayList<ArrayList<Integer>> patternGenerator, ArrayList<ArrayList<Integer>> SO, int row, int col, float epsilon, float FV[][]){
		
		ArrayList<Integer> dscpNb = new ArrayList<Integer>();
//		dscpNb.add(patternGenerator.get(row).get(col));		
		for (int i = 0;i<SO.get(row).size(); i++){
			float diff = 0;
			for (int k = 0; k<numFeatures; k++){
				diff = diff + Math.abs(FV[patternGenerator.get(row).get(col)][k]-FV[SO.get(row).get(i)][k]);
//				if ((Math.abs(FV[patternGenerator.get(row).get(col)][0]-FV[SO.get(row).get(i)][0])<=epsilon)&&(Math.abs(FV[patternGenerator.get(row).get(col)][1]-FV[SO.get(row).get(i)][1])<=epsilon)&&(Math.abs(FV[patternGenerator.get(row).get(col)][2]-FV[SO.get(row).get(i)][2])<=epsilon)&&(Math.abs(FV[patternGenerator.get(row).get(col)][3]-FV[SO.get(row).get(i)][3])<=epsilon)) {
//					dscpNb.add(SO.get(row).get(i));
//				}
		   }
			diff = (float) (diff/(float)numFeatures);
			if (diff<=epsilon) {
				dscpNb.add(SO.get(row).get(i));
			}
	    }
		return dscpNb;	
	}
	
	
	
	public static int descriptiveInterSection(ArrayList<Integer> dnb1,ArrayList<Integer> dnb2,float epsilon,float featureVector1[][], float featureVector2[][]){
		        int interSection = 0;
			
				for (int i1 = 0; i1<dnb1.size(); i1++){
					for (int i2 = 0; i2<dnb2.size(); i2++){
						float diff = 0;
						for(int k = 0; k<numFeatures; k++){
							diff = diff + Math.abs(featureVector1[dnb1.get(i1)][k]-featureVector2[dnb2.get(i2)][k]);
						}
						diff = (float) (diff/((float)numFeatures));
						if (diff<epsilon) {
							interSection++;
							break;
						}
//						if ((Math.abs(featureVector1[dnb1.get(i1)][0]-featureVector2[dnb2.get(i2)][0])<epsilon)&&(Math.abs(featureVector1[dnb1.get(i1)][1]-featureVector2[dnb2.get(i2)][1])<epsilon)&&(Math.abs(featureVector1[dnb1.get(i1)][2]-featureVector2[dnb2.get(i2)][2])<epsilon)&&(Math.abs(featureVector1[dnb1.get(i1)][3]-featureVector2[dnb2.get(i2)][3])<epsilon)) {
//							interSection++;
//							break;
//						}
					}
				}
                for (int i2 = 0; i2 < dnb2.size();i2++){
                	for (int i1 = 0; i1 < dnb1.size(); i1++){
                		float diff = 0;
                		for (int k = 0; k<numFeatures; k++){
                			diff = diff + Math.abs(featureVector1[dnb1.get(i1)][k]-featureVector2[dnb2.get(i2)][k]);         			
                		}
						diff = (float) (diff/((float)numFeatures));
                		if (diff<epsilon) {
							interSection++;
							break;
						}
//                		if ((Math.abs(featureVector1[dnb1.get(i1)][0]-featureVector2[dnb2.get(i2)][0])<epsilon)&&(Math.abs(featureVector1[dnb1.get(i1)][1]-featureVector2[dnb2.get(i2)][1])<epsilon)&&(Math.abs(featureVector1[dnb1.get(i1)][2]-featureVector2[dnb2.get(i2)][2])<epsilon)&&(Math.abs(featureVector1[dnb1.get(i1)][3]-featureVector2[dnb2.get(i2)][3])<epsilon)) {
//        					interSection++;
//        					break;
//        				}
                	}
                }
		return interSection;	
	}
	
	public static int[] ArrayDscNB(ArrayList<ArrayList<Integer>> patternGenerator, ArrayList<ArrayList<Integer>> SO, int row, int col, float epsilon, float FV[][]) {
		
		int[]DscNB = new int[SO.get(row).size()];
		n = 0;
		DscNB[n] = patternGenerator.get(row).get(col);		
		for (int i = 0; i<SO.get(row).size();i++){
			if ((Math.abs(FV[patternGenerator.get(row).get(col)][0]-FV[SO.get(row).get(i)][0])<=epsilon)&&(Math.abs(FV[patternGenerator.get(row).get(col)][1]-FV[SO.get(row).get(i)][1])<=epsilon)&&(Math.abs(FV[patternGenerator.get(row).get(col)][2]-FV[SO.get(row).get(i)][2])<=epsilon)&&(Math.abs(FV[patternGenerator.get(row).get(col)][3]-FV[SO.get(row).get(i)][3])<=epsilon)) {
				n++;
				DscNB[n] = SO.get(row).get(i);
			}
		}	
		return DscNB;		
	}
	
	public static int  ArrayDInSc(int []dnb1,int []dnb2, int sizefor1, int sizefor2, float epsilon,float featureVector1[][],float featureVector2[][]) {
		int intersection = 0;
		
		for (int i1 = 0; i1<=sizefor1; i1++){
			for (int i2 = 0; i2<=sizefor2; i2++){
				if ((Math.abs(featureVector1[dnb1[i1]][0]-featureVector2[dnb2[i2]][0])<epsilon)&&(Math.abs(featureVector1[dnb1[i1]][1]-featureVector2[dnb2[i2]][1])<epsilon)&&(Math.abs(featureVector1[dnb1[i1]][2]-featureVector2[dnb2[i2]][2])<epsilon)&&(Math.abs(featureVector1[dnb1[i1]][3]-featureVector2[dnb2[i2]][3])<epsilon)) {
					intersection++;
					break;
				}
			}
		}
		
		for (int i2 = 0; i2<=sizefor2; i2++){
			for (int i1 = 0; i1<=sizefor1; i1++){
				if ((Math.abs(featureVector1[dnb1[i1]][0]-featureVector2[dnb2[i2]][0])<epsilon)&&(Math.abs(featureVector1[dnb1[i1]][1]-featureVector2[dnb2[i2]][1])<epsilon)&&(Math.abs(featureVector1[dnb1[i1]][2]-featureVector2[dnb2[i2]][2])<epsilon)&&(Math.abs(featureVector1[dnb1[i1]][3]-featureVector2[dnb2[i2]][3])<epsilon)) {
					intersection++;
					break;
				}
			}
		}
		return intersection;	
	}
	
	
	public static int nonePatterInterSection(ArrayList<Integer> qObject, ArrayList<Integer> canObject, float [][]qFV, float [][]canFV, float epsilon) {
		int interSection = 0;
		
		for (int i1 = 0; i1<qObject.size(); i1++){
			for (int i2 = 0; i2<canObject.size(); i2++){
				if ((Math.abs(qFV[qObject.get(i1)][0]-canFV[canObject.get(i2)][0])<=epsilon)&&(Math.abs(qFV[qObject.get(i1)][1]-canFV[canObject.get(i2)][1])<=epsilon)&&(Math.abs(qFV[qObject.get(i1)][2]-canFV[canObject.get(i2)][2])<=epsilon)&&(Math.abs(qFV[qObject.get(i1)][3]-canFV[canObject.get(i2)][3])<=epsilon)) {
					interSection++;
					break;
				}
			}
		}
		for (int i2 = 0; i2<canObject.size(); i2++){
			for (int i1 = 0; i1<qObject.size(); i1++){
				if ((Math.abs(qFV[qObject.get(i1)][0]-canFV[canObject.get(i2)][0])<=epsilon)&&(Math.abs(qFV[qObject.get(i1)][1]-canFV[canObject.get(i2)][1])<=epsilon)&&(Math.abs(qFV[qObject.get(i1)][2]-canFV[canObject.get(i2)][2])<=epsilon)&&(Math.abs(qFV[qObject.get(i1)][3]-canFV[canObject.get(i2)][3])<=epsilon)) {
					interSection++;
					break;
				}
			}
		}
		return interSection;
	}
	
	public static void main(String[] args) throws IOException {
		
		
		
		//Calculate on query image		

//		File queryImgFolder = new File(imagePath);
//		File qf5Foler = new File(featurePath);
//		File qsmFolder = new File(smPath);	
//		File[] queryImgFilelist = queryImgFolder.listFiles();
//		File[] qf5Filelist = qf5Foler.listFiles();
//		File[] qsmFilelist = qsmFolder.listFiles();		
//		File candidateImageFiles = new File(imagePath);
//		File cf5Folder = new File(featurePath);
//		File csmFolder = new File(smPath);		
//		File[] candImageFilelist = candidateImageFiles.listFiles();
//		File[] cf5Filelist = cf5Folder.listFiles();
//		File[] csmFilelist = csmFolder.listFiles();
		
		float[][] similarityMatrix = new float[1000][1000];
		long start = 0;
		long end = 0;
	    long time = 0;
		start = System.currentTimeMillis();//timer
		
// This loop going to process the image on the rows (query)		
		for (int i = queryStart; i<=queryEnd; i++){
			File queryingImg = new File(imagePath + String.valueOf(i) + ".jpg");
			File queryingF5 = new File(featurePath + featureTitle+ String.valueOf(i) + ".txt");
			File queryingSm = new File(smPath + String.valueOf(i) + ".jpg");
//			File queryingImg = queryImgFilelist[i];
//			File queryingF5 = qf5Filelist[i];
//			File queryingSm = qsmFilelist[i];
			if (queryingImg.isFile()&&queryingF5.isFile()&&queryingSm.isFile()) {
				int [][]qsm = IOclass.inputImage(queryingSm, 1);
//				int[][] Downqsm= tools.downSampling(qsm);
//				for (int d = 0; d<layerNum-2; d++){
//					Downqsm = tools.downSampling(Downqsm);
//				}
								
				int qrow = qsm.length;
				int qcol = qsm[0].length;
//				int qrow1 = Downqsm.length;
//				int qcol1 = Downqsm[0].length;
				
				float[][] qfeatureVector = new float[qrow*qcol][numFeatures];
				qfeatureVector = readTxt.Readtext(queryingF5, qrow*qcol, numFeatures);
				qfeatureVector = tools.normalization(qfeatureVector, numFeatures);
				
				int [][]Downqsm = tools.downSamplingAft(qsm, qfeatureVector, numFeatures);
				qfeatureVector = tools.DSfeature;
				
				Downqsm = tools.downSamplingAft(Downqsm, qfeatureVector, numFeatures);
				qfeatureVector = tools.DSfeature;
				
				Downqsm = tools.downSamplingAft(Downqsm, qfeatureVector, numFeatures);
				qfeatureVector = tools.DSfeature;
				
//				Downqsm = tools.downSamplingAft(Downqsm, qfeatureVector, numFeatures);
//				qfeatureVector = tools.DSfeature;
				
				ArrayList<ArrayList<Integer>> qsalienceBlock = new ArrayList<ArrayList<Integer>>();
				ArrayList<ArrayList<Integer>> qDownsalienceBlock = new ArrayList<ArrayList<Integer>>();
				ArrayList<ArrayList<Integer>> qDownpatternGenerators = new ArrayList<ArrayList<Integer>>();
				
				qsalienceBlock = SaliencyDetection.newSalienceDetection(qsm, saliencyThreshold, qfeatureVector,numOfbins);
				qDownsalienceBlock = SaliencyDetection.newSalienceDetection(Downqsm, saliencyThreshold, qfeatureVector, numOfbins);				
				qDownpatternGenerators = Pattern.NewPatternGenerators(qDownsalienceBlock, numbersOfpattern);
			    
				BufferedImage queryBufferedImage = new BufferedImage(qcol,qrow,BufferedImage.TYPE_INT_RGB);
				ArrayList<ArrayList<Integer>> aimtoFind = new ArrayList<ArrayList<Integer>>();
			    aimtoFind.add(qsalienceBlock.get(objectNum));
			    queryBufferedImage = tools.addBox(queryingImg, qrow, qcol, aimtoFind,boxedge);
//output images with bounding box			    
			       String ext="jpg";
//			       String filepath = System.getProperty("java.io.tmpdir") +"sameObjects1" + "." + ext;
			       String  filepath = queryObjectPath + String.valueOf(i) + "." + ext;
			       try {
			           ImageIO.write(queryBufferedImage, ext,  new File(filepath));
			           System.out.println("File path is:" + filepath);
			       }
			       catch (IOException ex) {
			           ex.printStackTrace();
			       }
// This loop going to processing images on columns (candidats)	    
				for (int j = i; j<1000; j++){
					File cf5File = new File(featurePath + featureTitle + String.valueOf(j) + ".txt");
					File csmFile = new File(smPath + String.valueOf(j) + ".jpg");
//				File cf5File = cf5Filelist[j];
//				File csmFile = csmFilelist[j];
				     if (cf5File.isFile()&&csmFile.isFile()) {

				    	 int [][]csm = IOclass.inputImage(csmFile, 1);
//						 int [][]Downcsm= tools.downSampling(csm);
//							for (int d = 0; d<layerNum-2; d++){
//								Downcsm = tools.downSampling(Downcsm);
//							}
					 int crow = csm.length;
					 int ccol = csm[0].length;
//					 int crow1 = Downcsm.length;
//					 int ccol1 = Downcsm[0].length;
					 float[][] cfeatureVector = new float[crow*ccol][numFeatures];
					 cfeatureVector = readTxt.Readtext(cf5File, crow*ccol, numFeatures);
					 cfeatureVector = tools.normalization(cfeatureVector, numFeatures);
					 
					 int [][]Downcsm = tools.downSamplingAft(csm, cfeatureVector, numFeatures);
					 cfeatureVector = tools.DSfeature;
					 
					 Downcsm = tools.downSamplingAft(Downcsm, cfeatureVector, numFeatures);
					 cfeatureVector = tools.DSfeature;
					 
					 Downcsm = tools.downSamplingAft(Downcsm, cfeatureVector, numFeatures);
					 cfeatureVector = tools.DSfeature;
					 
//					 Downcsm = tools.downSamplingAft(Downcsm, cfeatureVector, numFeatures);
//					 cfeatureVector = tools.DSfeature;
					 
					 ArrayList<ArrayList<Integer>> CandsalienceBlock = new ArrayList<ArrayList<Integer>>();
					 ArrayList<ArrayList<Integer>> DownCandsalienceBlock = new ArrayList<ArrayList<Integer>>();
					 ArrayList<ArrayList<Integer>> DownpatternGeneratorsCand = new ArrayList<ArrayList<Integer>>();

					 CandsalienceBlock = SaliencyDetection.newSalienceDetection(csm, saliencyThreshold,cfeatureVector,numOfbins);
					 DownCandsalienceBlock = SaliencyDetection.newSalienceDetection(Downcsm, saliencyThreshold, cfeatureVector, numOfbins);
					 DownpatternGeneratorsCand = Pattern.NewPatternGenerators(DownCandsalienceBlock, numbersOfpattern);
					 
					 ArrayList<Integer> dnb1 = null;
					 ArrayList<Integer> dnb2 = null;
					 
					 float Pattern_similarity;
					 float MaxSimilarity = 0;
					 int u1 = 0;
					 int u2 = 0;
			    	 System.out.println(j+"start");
					 for (int n = 0; n<DownpatternGeneratorsCand.size(); n++){
							Pattern_similarity = 0;
							int counting = 0;
						for (int m = 0; m<qDownpatternGenerators.get(objectNum).size();m++){
							dnb1 =new ArrayList<Integer>();
							dnb1 = descriptiveNb(qDownpatternGenerators, qDownsalienceBlock, objectNum, m, NB_epsilon, qfeatureVector);
							u1 = dnb1.size();
							float similarity = 0;
							float s = 0;
							for (int k = 0; k<DownpatternGeneratorsCand.get(n).size();k++){
								dnb2 =new ArrayList<Integer>();
								dnb2 = descriptiveNb(DownpatternGeneratorsCand, DownCandsalienceBlock, n, k, NB_epsilon, cfeatureVector);
								u2 = dnb2.size();
								int y = 0;
								//Descriptive intersection by arraylist
								y = descriptiveInterSection(dnb1, dnb2, INS_epsilon, qfeatureVector, cfeatureVector);
								s = (float)y/(float)(u1+u2);
								if (s==1) {
									similarity = s;
									break;
								}
								if (s>similarity) {
									similarity = s;
								}		
							}
							Pattern_similarity = Pattern_similarity+similarity;
						}
						Pattern_similarity = (float)Pattern_similarity/(qDownpatternGenerators.get(objectNum).size());
						if (Pattern_similarity>MaxSimilarity) {
							MaxSimilarity = Pattern_similarity;
						}
						System.out.println(Pattern_similarity);
						similarityMatrix[i][j] = MaxSimilarity;
						counting++;
					 }
//					 oneItem.add(fileName);
//					 oneItem.add(MaxSimilarity);	
				     }				
			    }			
			}
		}
		end = System.currentTimeMillis();
		time = end-start;
		System.out.println(time);
		
		String name1 = String.valueOf(queryStart);
		String name2 = String.valueOf(queryEnd);
		
		String name = name1+name2;
		
		File outputData = new File(output + name + ".txt");
		BufferedWriter bf =new BufferedWriter(new FileWriter(outputData));
		for (int x = 0; x<similarityMatrix.length; x++){
			for (int y = 0; y<similarityMatrix[x].length; y++){
				String fl = String.valueOf(similarityMatrix[x][y]);
				bf.write(fl);

				bf.write(" ");
			}
			bf.newLine();
		}
		bf.close();
		System.out.println("Done");
	}
}
